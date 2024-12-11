---
sidebar_position: 2
---

# Conceptos básicos

Para entender adecuadamente la aplicabilidad de los conceptos básicos de Spring Data, es importante comprender primero las [relaciones entre tablas](./intro) descritas en la sección anterior. 

Spring Data, como tal, se trata de una dependencia de Spring Framework que se encarga de las operaciones de persistencia de datos y, además, abstrae la lógica a través de interfaces que simplifican las operaciones CRUD dentro de un microservicio. Existen diferentes dependencias basadas en Spring Data, dependiendo del caso de uso y del tipo de motor de bases de datos.

* __Spring Data JPA:__ se utiliza como capa base para la comunicación con bases de datos relacionales (SQL).
* __Spring Data R2DBC:__ basado en Spring Data JPA. Se emplea en aplicaciones reactivas que utilizan bases de datos relacionales.
* __Spring Data JDBC:__ persistencia de datos SQL con JDBC plano.
* __Spring Data Reactive Redis:__ acceso a valores en Redis de tipo _key-value_.

Como en nuestro caso nos centraremos en persistencia con bases de datos SQL, utilizaremos _Spring Data JPA_. A continuación, se exponen los conceptos clave detrás de esta herramienta.

## 1. Configuración: conexión a la base de datos



## 2. Modelo

Spring Data relaciona los modelos base (POJOs) con las tablas SQL. Cada atributo de la clase corresponde a una columna de la tabla en la base de datos. Por ejemplo:

```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;

    // Getters, Setters, Constructors (Lombok can be used)
}
```

Como se puede apreciar, existen múltiples decoradores que se relacionan en la clase, entre ellos:

* __`@Entity`__: identifica una clase como una tabla en SQL. Por default, toma el nombre de la clase para relacionarlo con una tabla. Si la tabla con dicho nombre no existe, creará la tabla en la base de datos.
* __`@Id`__: 
* __`@GeneratedValue`__:

## 3. Repository

El concepto de _repository_ se trata de una abstracción para el __acceso de datos__. En lugar de escribir un DAO (_Data Access Object_), Spring Data lo crea para nosotros