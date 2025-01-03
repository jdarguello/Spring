---
sidebar_position: 2
---

# Taller 5

El presente Taller se trata de una extensión del [Taller 4](taller4.md). En este punto, aplicaremos todos los conceptos que hemos visto hasta la fecha (Spring Web, Spring Data JPA y Spring Testing). 

## 1. Adecuación del proyecto

Para el presente proyecto, usaremos las dependencias mostradas en la Figura 1.

![](../../../static/img/testing/Taller%205/initializr.png)

Figura 1. Gestión del proyecto.

## 2. Ambientes de trabajo

Definiremos dos ambientes de trabajo en el `application.yaml`, uno para los _test unitarios_ (base de datos H2) y otro para _pruebas funcionales_ (base de datos PostgreSQL), como se muestra a continuación.

```yaml
server:
    port: 8080

---
spring:
  config:
    activate:
      on-profile: test_unitarios
  datasource:
    url: jdbc:h2:mem:test-db
    username: test-user
    password: test-pass

---
spring:
  config:
    activate:
      on-profile: funcional
  datasource:
    url: jdbc:postgresql://localhost:5432/inv-virtual
    username: usuario-taller5
    password: pass-taller5
```

Emplearemos Docker Compose para configurar el contenedor con la base de datos de pruebas, editando el archivo `compose.yaml` de la siguiente forma:

```yaml
services:
    db-taller5:
        container_name: taller5
        image: 'postgres:latest'
        environment:
        - 'POSTGRES_DB=inv-virtual'
        - 'POSTGRES_PASSWORD=pass-taller5'
        - 'POSTGRES_USER=usuario-taller5'
        ports:
        - '5432'
```

## 3. Modelos y Repositorios

A diferencia del Taller 4, las relaciones entre clases de los modelos descritos presenta ciertas complejidades que se recomienda probar mediante test unitarios. Adicional, se debería también corroborar la capa de persistencia de datos. 

Dada que la mayoría de las integraciones requieren la existencia de la clase `Cliente`, iniciaremos creándola de la siguiente forma:

```java
@Table(name = "Cliente")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clienteId;
    private String nombre;
    private Date fechaVinculacion;

    //@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    //private List<Documento> documentos;   

    //@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    //private List<CuentaBancaria> cuentas;

    //@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    //private List<InversionVirtual> inversiones;
    
}
```

### 3.1. Documento

Para la clase `Documento`:

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<Tabs>
    <TabItem value="doc-model" label="Modelo" default>
    ```java
    @Table(name = "Documento")
    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Documento {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long documentoId;
        private String numeroDocumento;
        private String tipo;

        @ManyToOne
        @JoinColumn(name = "clienteId")
        private Cliente cliente;
    }
    ```
    </TabItem>
    <TabItem value="doc-repository" label="Repository">
    ```java
    public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    
    }
    ```
    </TabItem>
    <TabItem value="doc-test" label="Test unitarios">
    ```java
    @DataJpaTest
    @ActiveProfiles("test_unitarios")
    public class DocumentoTest {
        
        @Autowired
        private DocumentoRepository repository;

        //Clientes
        private Cliente Fernanda;

        //Documentos
        private Documento documentoFernanda;

        @BeforeEach
        void setUp () {
            //Fecha de vinculación
            Calendar calendar = Calendar.getInstance();
            calendar.set(2001, Calendar.MAY, 16);

            //Clientes
            Fernanda = Cliente.builder()
            .nombre("Fernanda Aristizabal")
            .fechaVinculacion(calendar.getTime())
            .build();

            documentoFernanda = Documento.builder()
                .cliente(Fernanda)
                .numeroDocumento("AE392183")
                .tipo("PP")
                .build();
        }

        @Test
        public void saveAndFind() {
            documentoFernanda = repository.save(documentoFernanda);

            Documento documentoObtenido = repository.findById(documentoFernanda.getDocumentoId()).get();

            assertNotNull(documentoObtenido);
            assertEquals(documentoObtenido.getNumeroDocumento(), "AE392183");
            assertEquals(documentoObtenido.getTipo(), "PP");
        }

        @Test
        public void delete() {
            documentoFernanda = repository.save(documentoFernanda);

            repository.delete(documentoFernanda);

            assertThrows(
                Exception.class,
                () -> repository.findById(documentoFernanda.getDocumentoId()).get()
            );
        }
    }
    ```
    </TabItem>
</Tabs>

### 3.2. Cuenta Bancaria

Para la `CuentaBancaria`, se tiene lo siguiente:




## 4. Servicios

## 5. Controladores

