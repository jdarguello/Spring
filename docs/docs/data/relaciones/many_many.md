La relación _Many-To-Many describe las relaciones entre múltiples objetos de una tabla con múltiples objetos de otra. Un ejemplo de ello puede ser la relación entre personas con cuentas bancarias compartidas. 

![](../../../static/img/spring%20data/many-many.png)

Figura 3. Relación _Many-To-Many.

Como se aprecia en al Figura 3, para establecer este tipo de relaciones, se requiere crear una tabla intermedia que relacione los IDs de las mismas. En términos de código, esto se establece de la siguiente forma:

```java
@Entity
@Table(name="Persona")
public class Persona {
    
    // Atributos descritos anteriormente

    @ManyToMany(mappedBy="personas")
    private List<CuentaBancaria> cuentasBancarias;
}
```

```java
@Entity
@Table(name="CuentaBancaria")
public class CuentaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cuentaId;
    private String numero;
    private String tipo;
    private Double saldo;

    @ManyToMany
    @JoinTable(
        name = "personas_cuentas",
        joinColumns = @JoinColumn(name = "cuentaId"),
        inverseJoinColumns = @JoinColumn(name = "personaId")
    )
    private List<Persona> personas = new ArrayList<>();
}
```