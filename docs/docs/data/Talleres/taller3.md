---
sidebar_position: 2
---

# Taller 3

En el presente taller, ampliaremos el alcance del [Taller 2](./taller2.md) al definir relaciones entre objetos de mayor complejidad para que nuestro microservicio cumpla con objetivos del mundo real. Nuestro entorno de trabajo sigue siendo sobre un microservicio de productos, pero ahora, además de cumplir con el objetivo de registrar y retornar productos, permitiremos que nuestro microservicio pueda generar carritos de compra y pedidos. Para ello, nos basaremos en el diseño de arquitectura expuestos en las Figuras 1 y 2.

![](../../../static/img/spring%20data/Taller3/carrito.png)

Figura 1. Modelos.

En la Figura 1, se pueden observar tres clases: `Producto`, `CarritoCompras`y `OrdenCompra`. El primero tiene una relación de agregación con el segundo, mientras que el tercero presenta una relación de asociación con el segundo.

![](../../../static/img/spring%20data/Taller3/relaciones_tablas.png)

Figura 2. Relaciones entre tablas.

Como se aprecia en la Figura 2, en el presente taller, usaremos dos tipos de relaciones entre tablas: __One-To-One__ (`CarritoCompras` y `OrdenCompra`) y __One-To-Many__ (`Producto` y `CarritoCompras`).

## 1. Generación del proyecto y Conexión a la base de datos

Tanto la generación del proyecto como la conexión a la base de datos se realiza de la misma forma en como se expuso en las secciones 1 y 2 del [Taller 2](./taller2.md).

## 2. Modelos

De los modelos expuestos en la Figura 1, empezaremos con la clase `Producto`.

```java
@Entity
@Table (name = "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="productoGen")
    @SequenceGenerator(name = "productoGen", sequenceName = "producto_sequence", allocationSize = 1)
    private Long productoId;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer inventario;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.DETACH)
    private List<Articulo> articulos;
}
```

Como se puede observar, el producto se vincula con una lista de objetos tipo `Articulo` a través de una relación `@OneToMany`. Debido a que los precios del producto pueden cambiar en el tiempo, se configuró la relación como `DETACH` para garantizar que el artículo tenga un sólo valor en el tiempo.

```java
@Entity
@Table (name = "Articulo")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="articuloGen")
    @SequenceGenerator(name = "articuloGen", sequenceName = "articulo_sequence", allocationSize = 1)
    private Long articuloId;

    private Double descuento = 0.0;
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "productoId")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "carritoId")
    private CarritoCompra carrito;

    public Double getSubtotal () {
        return this.cantidad*this.producto.getPrecio()*(1-this.descuento);
    }
}
```

La clase `Articulo` se vincula con un único `Producto` a través de una relación `@ManyToOne`. Adicional, se adicionó un método de `getSubtotal()` para ayudar al carrito de compras en el cálculo del valor total.

Ahora, hablaremos de la relación entre los `Articulo` con el `CarritoCompra`.

```java
@Entity
@Table (name = "CarritoCompra")
public class CarritoCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="carritoGen")
    @SequenceGenerator(name = "carritoGen", sequenceName = "carrito_sequence", allocationSize = 1)
    private Long carritoId;

    private String nombreCliente;
    private Integer cantidadArticulos = 0;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Articulo> articulos;

    @OneToOne
    @JoinColumn(name = "ordenId", referencedColumnName = "ordenId")
    private OrdenCompra orden;

    public Integer getCantidadArticulos () {
        return 0 //Lógica para contar la cantidad de artículos
    }

    public Double getTotal() {
        return 0.0 //Lógica para calcular el valor total
    }
}
```

Como se puede apreciar, un objeto de `CarritoCompra` tiene vinculado a muchos objetos de tipo `Articulo` en una relación tipo `@OneToMany`. Emplea un `CascadeType.ALL` debido a que se busca garantizar la relación de composición entre los artículos con el carrito de compras. Adicional, empleamos `FetchType.EAGER` porque queremos que cada vez que se importe el carrito de compras, traiga consigo la referencia de __todos__ los artículos.

Finalmente, hablaremos de la `OrdenCompra`.

```java
@Entity
@Table (name = "OrdenCompra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ordenGen")
    @SequenceGenerator(name = "ordenGen", sequenceName = "orden_sequence", allocationSize = 1)
    private Long ordenId;

    private String metodoPago;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CarritoCompras carrito;

    public Integer getCantidadArticulos () {
        return this.carrito.getCantidadArticulos();
    }

    public Double getTotal() {
        return this.carrito.getTotal();
    }
}
```
## 3. Repositories

Se plantean interfaces tipo `CrudRepository` para cada uno de los modelos gestionados, similar a como se trabajó en el [Taller 2](./taller2.md). 

## 4. Services y controladores

Se gestionan de una forma similar a la expuesta en el [Taller 2](./taller2.md). 