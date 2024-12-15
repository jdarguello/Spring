Las relaciones _One-To-One_ se emplean para relacionar una tabla con otra. Por ejemplo, la relación entre una `Persona` y su `Residencia`, como se aprecia en la Figura 1.

![](../../../static/img/spring%20data/one-one.png)

Figura 1. Relación Uno a Uno entre una _persona_ y su _residencia_.

Conforme a la relación descrita en la Figura 1, el modelo de `Residencia` podría ser algo como:

```java
@Entity
@Table(name="Residencia")
public class Residencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long residenciaId;
    private String ciudad;
    private String barrio;
    private Integer direccion;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Persona persona;
}
```
Además, la clase `Persona` se reajustaría de la siguiente forma:

```java
@Entity
@Table(name="Persona")
public class Persona {
    
    // Atributos descritos anteriormente

    @OneToOne
    @JoinColumn(name = "personaId", referencedColumnName = "personaId")
    private Residencia residencia;
}
```