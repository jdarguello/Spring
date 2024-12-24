---
sidebar_position: 3
---

# Test de Integración

A diferencia de los _test unitarios_, los test de integración buscan evaluar un flujo de trabajo completo que involucra la interacción entre diferentes componentes de software. Estas interacciones pueden ser entre clases dentro de un mismo microservicio o APIs de _"servicios externos"_.

Dentro de Spring Framework, existen beans que facilitan la construcción de test de integración dentro de un microservicio. Estos beans son preinstalados al momento de iniciar un proyecto de Spring Boot y se conocen como _Spring Testing_. A continuación, se pueden apreciar algunos de los beans más significativos en test de integración.

| __Bean__ | __Tipo__ | __Requisito__ | __Descripción__ |
| -------- | -------- | ------------- | --------------- |
| `@SpringBootTest` | Configuración | Obligatorio | Carga la configuración base en el set de testing. Se emplea a nivel de clase. |
| `@ContextConfiguration` | Configuración | Opcional | Permite especificar el tipo de configuración de testing. Por ejemplo, si se usan distintas bases de datos, se puede especificar a cuál conectarse. |
| `@Import` | Configuración | Opcional | Importa configuraciones adicionales. Especialmente útil para test de integración con configuraciones de seguridad. |
| `@MockBean` | Mocking | Opcional | Crea e inyecta un mock manejado por Spring Boot. Se puede usar en relaciones complejas entre clases. |
| `@SpyBean` | Mocking | Opcional | Envuelve un bean existente y simula parte de su comportamiento. |
| `@WebMvcTest` | Capa | Obligatorio | Se utiliza en tests de integración. Permite la exposición de APIs en la capa de infraestructura haciendo llamados mediante métodos HTTP (GET, POST, etc). Se puede configurar para que utilice todos los controladores o una sola clase en particular. |
| `@DataJpaTest` | Capa | Opcional | Configura, únicamente, los componented de Spring Data JPA para corroborar la lógica de serialización en la capa de persistencia de datos. |

Tabla 1. Beans de Spring Testing.

## 1. Test de controladores

En este tipo de test, se busca corroborar los flujos de trabajo en un microservicio emulando el llamado al microservicio a través de las APIs expuestas. Existen dos formas de testear

```java
@WebMvcTest(AuthorController.class)
class ControladorTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private ExampleService service;
    private final ObjectMapper objectMapper = new ObjectMapper();
}
```

