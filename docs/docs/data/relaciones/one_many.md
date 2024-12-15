Las relaciones _One-To-Many_ y _Many-To-One_ especifican que el objeto de una tabla se relaciona con uno o varios de otra. Por ejemplo, una persona a lo largo de su vida, puede cambiar la documentación legal en el tiempo, como cuando se alcanza la mayoría de edad, ocurre cambio de nombre o nacionalidad. Con base en ello, podemos plantear la relación entre una `Persona` y su `Documento` de identidad, como se muestra en la Figura 2.

![](../../../static/img/spring%20data/one-many.png)

Figura 2. Relación _One-To_Many_.

Como se aprecia en la Figura 2, este tipo de relaciones se establecen a partir de la columna `personaId` en la tabla del `Documento`. En los modelos de Spring Boot, esto se define de la siguiente forma:

```java
@Entity
@Table(name="Documento")
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long documentoId;
    private String numero;
    private String nombreCompleto;
    private Date fechaExpedicion;
    private String nacionalidad;

    @ManyToOne
    @JoinColumn(name = "personaId")
    private Persona persona;
}
```

```java
@Entity
@Table(name="Persona")
public class Persona {
    
    // Atributos descritos anteriormente

    @OneToMany(mappedBy="persona", cascade = CascadeType.ALL)
    private List<Documento> documento;
}
```