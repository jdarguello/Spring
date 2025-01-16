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

Dada que la mayoría de las integraciones requieren la existencia de la clase `Cliente`, iniciaremos creándola de la siguiente forma. Además, también estableceremos una clase abstracta con la configuración general de nuestros sets de test unitarios para la capa de modelos.

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<Tabs>
    <TabItem value="modelo-cliente" label="Cliente" default>

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
    </TabItem>
    <TabItem value="modelo-test" label="ModelosTest" default>
```java
public abstract class ModelosTest {

    //Clientes
    protected Cliente Fernanda;

    //Documentos
    protected Documento documentoFernanda;

    @BeforeEach
    public void setUp () {
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
}
```
    </TabItem>
</Tabs>

### 3.1. Documento

Para la clase `Documento`:

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

        //@OneToOne
        //private CuentaBancaria cuenta;
    }
    ```
    </TabItem>
    <TabItem value="doc-repository" label="Repository">
    ```java
    public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    
    }
    ```
    </TabItem>
    <TabItem value="doc-modelo-test" label="Test unitarios">
```java
@DataJpaTest
@ActiveProfiles("test_unitarios")
public class DocumentoTest extends ModelosTest {
    
    @Autowired
    private DocumentoRepository repository;

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

<Tabs>
    <TabItem value="cuenta-modelo" label="Modelo" default>

```java
@Table(name = "Cuenta_Bancaria")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuentaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cuentaId;
    private Long numeroCuenta;
    private Double saldo = 0.0;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @OneToOne(mappedBy = "cuenta", cascade = CascadeType.ALL)
    @JoinColumn(name = "documentoId")
    private Documento documento;
}
```
    </TabItem>
    <TabItem value="cuenta-repo" label="Repository">

```java
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long> {
    
}
```
    </TabItem>
    <TabItem value="cuenta-modelo-test" label="Test unitarios">

```java
@DataJpaTest
@ActiveProfiles("test_unitarios")
public class CuentaBancariaTest extends ModelosTest {
    
    @Autowired
    private CuentaBancariaRepository repository;

    @Test
    public void saveAndFind() {
        cuentaFernanda = repository.save(cuentaFernanda);

        CuentaBancaria cuentaObtenida = repository.findById(cuentaFernanda.getCuentaId()).get();

        assertNotNull(cuentaObtenida);
        assertEquals("Fernanda Aristizabal", cuentaObtenida.getCliente().getNombre());
        assertEquals(31398734562L, cuentaObtenida.getNumeroCuenta());
    }

    @Test
    public void delete() {
        cuentaFernanda = repository.save(cuentaFernanda);

        repository.delete(cuentaFernanda);

        assertThrows(
            Exception.class,
            () -> repository.findById(cuentaFernanda.getCuentaId()).get()
        );
    }
}
```
    </TabItem>
</Tabs>

### 3.3. Inversión Virtual

Para la `InversionVirtual`:

<Tabs>
    <TabItem value="inversion-modelo" label="Modelo" default>

```java
@Table(name = "Inversion_Virtual")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InversionVirtual {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inversionId;

    @Column(nullable = false)
    private Double valor;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Temporal(TemporalType.DATE)
    private LocalDate tiempoDuracion;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "cuentaOrigenId", referencedColumnName = "cuentaId", nullable = false)
    private CuentaBancaria cuentaOrigen;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "cuentaDestinoId", referencedColumnName = "cuentaId", nullable = false)
    private CuentaBancaria cuentaDestino;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "clienteId")
    private Cliente cliente;
}
```
    </TabItem>
    <TabItem value="inverson-repo" label="Repository">
```java
public interface InversionVirtualRepository extends JpaRepository<InversionVirtual, Long> {
    
}
```
    </TabItem>
    <TabItem value="inversion-modelo-test" label="Test unitarios">
```java
@DataJpaTest
@ActiveProfiles("test_unitarios")
public class InversionVirtualTest extends ModelosTest {
    
    @Autowired
    private InversionVirtualRepository inversionRepository;

    @Autowired
    private CuentaBancariaRepository cuentaRepository;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();  //Ejecuta la lógica del padre

        //Crear la cuenta bancaria de Fernanda, para que sirva 
        //como cuenta de Origen y Destino
        cuentaRepository.save(cuentaFernanda);
    }

    @AfterEach
    public void tearDown() {
        cuentaRepository.deleteAll();
    }

    @Test
    public void saveAndFind() {
        inversionFernanda = inversionRepository.save(inversionFernanda);

        InversionVirtual inversionObtenida = inversionRepository.findById(inversionFernanda.getInversionId()).get();

        assertNotNull(inversionObtenida);
        assertEquals("Fernanda Aristizabal", inversionObtenida.getCliente().getNombre());
        assertEquals(31398734562L, inversionObtenida.getCuentaOrigen().getNumeroCuenta());
        assertEquals(31398734562L, inversionObtenida.getCuentaDestino().getNumeroCuenta());
    }
}
```
    </TabItem>
</Tabs>

## 4. Servicios

La lógica de la capa de los servicios y los test unitarios construídos son los mismos desarrollados en el [Taller 4](taller4.md).

## 5. Controladores

En esta capa, expondremos las APIs requeridas por el frontend. Si queremos una experiencia centrada en el usuario, las APIs deberían manejarse a través de nuestra `ClienteService`, que comunica las necesidades del cliente de forma transparente para cada servicio. Cada controlador tendrá tanto test unitarios como de integración.

### 5.1. Documentos

Si nuestro cliente desea registrar un nuevo documento, leer su información o eliminarlo, debería poder hacerlo. Por cuestiones de seguridad, no debería poder modificarlo. 

<Tabs>
    <TabItem value="doc-controlador" label="Controlador" default>

```java
@RestController
@RequestMapping("/api/docs")
public class DocumentoControlador {
    
    private ClienteOperaciones operaciones;

    public DocumentoControlador(ClienteOperaciones operaciones) {
        this.operaciones = operaciones;
    }

    @PostMapping
    public ResponseEntity<?> nuevoDocumento(@RequestParam(name = "clienteId") Long clienteId, 
                                            @RequestBody Documento documento) throws Exception {

        Documento documentoRegistrado = new Documento();
        try {
            documentoRegistrado = this.operaciones.registrarNuevoDocumento(clienteId, documento);
        } catch (SQLException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                errorMessage(400, "Bad Request", 
                    exception.getMessage(), "/api/docs")
            );
        } catch (InstantiationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                errorMessage(400, "Bad Request", 
                    exception.getMessage(), "/api/docs")
            );
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
            documentoRegistrado
        );
    }

    @GetMapping
    public Documento getDocumento(@RequestParam(name = "clienteId") Long clienteId, 
                                  @RequestParam(name = "documentoId") Long documentoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDocumento'");
    }

    @DeleteMapping
    public Boolean eliminarDocumento(@RequestParam(name = "clienteId") Long clienteId, 
                                    @RequestParam(name = "documentoId") Long documentoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarDocumento'");
    }

    private Map errorMessage(Integer status, String error, String message, String path) {
        return Map.of(
            "timestamp", LocalDateTime.now(),
            "status", status,
            "error", error,
            "message", message,
            "path", path
        );
    }
}
```
    </TabItem>
    <TabItem value="doc-controlador-tests-unitarios" label="Test unitarios">
```java
@WebMvcTest(DocumentoControlador.class)
public class DocumentoControladorTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;  //Serializador JSON

    @Autowired
    private ClienteOperaciones clienteOperaciones;

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public ClienteOperaciones mockeOperaciones() {
            return mock(ClienteOperaciones.class);
        }
    }

    private Documento cedulaFernanda;

    private String requestBody;

    @BeforeEach
    public void setUp() throws Exception {
        cedulaFernanda = Documento.builder()
            .documentoId(2L)
            .tipo("CC")
            .numeroDocumento("10983929238")
            .build();

        when(clienteOperaciones.registrarNuevoDocumento(1L, cedulaFernanda))
            .thenReturn(cedulaFernanda);

        requestBody = objectMapper.writeValueAsString(cedulaFernanda);   //Serialización JSON de la cédula
    }

    @Test
    public void nuevoDocumento201 () throws Exception {
        MvcResult response = postNuevoDocumento(status().isCreated(), requestBody);

        Documento documentoObtenido = objectMapper.readValue(response.getResponse().getContentAsString(), Documento.class);

        assertEquals(documentoObtenido.getDocumentoId(), 2L);
        assertEquals(documentoObtenido.getTipo(), "CC");
        assertEquals(documentoObtenido.getNumeroDocumento(), "10983929238");
    }

    @Test
    public void nuevoDocumento400YaRegistrado() throws Exception {
         when(clienteOperaciones.registrarNuevoDocumento(1L, cedulaFernanda))
            .thenThrow(new SQLException("400: el documento ya está registrado en BD"));

        
        MvcResult result = postNuevoDocumento(status().isBadRequest(), requestBody);

        ErrorModel error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorModel.class);


        assertEquals(error.status(), 400);
        assertEquals(error.message(), "400: el documento ya está registrado en BD");
        assertEquals(error.path(), "/api/docs");
    }

    @Test
    public void nuevoDocumento400InfoIncompleta() throws Exception {
        //Error de documento incompleto
        Documento pasaporteIncompleto = Documento.builder()
            .tipo("PP")
            .build();
        
        when(clienteOperaciones.registrarNuevoDocumento(1L, pasaporteIncompleto))
            .thenThrow(new InstantiationException("400: la información del documento está incompleta"));
        
        MvcResult result = postNuevoDocumento(status().isBadRequest(), objectMapper.writeValueAsString(pasaporteIncompleto));

        ErrorModel error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorModel.class);

        assertEquals(error.status(), 400);
        assertEquals(error.message(), "400: la información del documento está incompleta");
    }

    private MvcResult postNuevoDocumento(ResultMatcher status, String requestBody) throws Exception {
        return mvc.perform(post("/api/docs")
            .param("clienteId", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status)
        .andReturn();
    }
}
```
    </TabItem>
</Tabs>

