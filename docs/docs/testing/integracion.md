---
sidebar_position: 3
---

# Test de Integración

A diferencia de los _test unitarios_, los test de integración buscan evaluar los flujos de trabajo completos, donde se ejecutan las interacciones entre los diferentes componentes de software. Estas interacciones pueden ser entre clases dentro de un mismo microservicio o APIs de _"servicios externos"_.

Para el caso de un microservicio de Spring Boot, sólo debemos implementar el bean `@SpringBootTest`. Con él, el test cargará todos los beans pre-configurados; emulando el comportamiento normal de la aplicación. 

## 1. Configuración de diferentes ambientes de trabajo

Dentro del `application.yaml` es sencillo establecer configuraciones para distintos ambientes de trabajo (`spring.profiles`). También es posible establecer configuraciones generales que sean aplicables a todos los ambientes. En el siguiente ejemplo, se tienen dos ambientes: `test` y `dev`, expuestos en el puerto `8080`. La primera con base de datos en memoria, tipo H2, y la segunda con base de datos MySQL.

```yaml
server:
    port: 8080

---
spring:
  profiles: test
  datasource:
    url: jdbc:h2:mem:test-db
    username: test-user
    password: test-pass

---
spring:
  profiles: dev
  datasource:
    url: jdbc:mysql://localhost:3306/dev-db
    username: dev-user
    password: dev-pass

```

## 2. Docker Compose y Testcontainers

Docker Compose es una herramienta de orquestación de contenedores básica que se puede acoplar fácilmente con el entorno de desarrollo de Spring Boot, a través de la dependencia de _Docker Compose Support_, como se puede apreciar en la Figura 1.

![](../../static/img/testing/integration/dependencias.png)

Figura 1. Dependencias de _Docker Compose_ y _Testcontainers_ en Spring Initialzr.

Al utilizar Docker Compose, la estructura del proyecto habilita la carpeta `compose.yaml`, en donde se pueden especificar los diferentes contenedores con los que se desea trabajar, como se muestra a continuación.

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<Tabs>
    <TabItem value="folder" label="Estructura del proyecto" default>
    ```bash
    /my_microservice
    ├── /src
    │   ├── /main
    |   |   ├── /java
    |   |   |   ├── /Group-name/Artifact/Name
    |   |   |   ├── {{Name}}Application.java
    |   |   ├── /resources
    |   |   |   ├── /static
    |   |   |   ├── /templates
    |   |   |   ├── application.properties
    │   ├── /test
    ├── build.gradle
    ├── settings.gradle
    ├── compose.yaml
    ```
    </TabItem>
    <TabItem value="yaml" label="Docker Compose YAML">
    En el archivo YAML es fácil e intuitiva la orquestación de contenedores. Simplemente se debe darle un nombre al servicio, al contenedor (`container_name`), especificar la imagen (`image`) y variables de entorno (`environment`), entre otros. Por ejemplo, a continuación, se puede ver la configuración base para crear una base de datos en PostgreSQL.
    ```yaml
    services:
        postgres:
            container_name: blog_postgres_db
            image: 'postgres:16.0'
            environment:
            - 'POSTGRES_DB=blog'
            - 'POSTGRES_PASSWORD=secret_password'
            - 'POSTGRES_USER=blog'
            ports:
            - '5432'
    ```
    </TabItem>
</Tabs>

Por otro lado, la dependencia de `Testcontainers` se trata de una librería base de Spring Framework que facilita la implementación de contenedores dentro de los test de integración en Spring Boot. Es especialmente útil para realizar pruebas de integración automática con bases de datos contenerizados. 

### 2.1. ¿Cuándo usar uno u otro?


Depende del caso de uso. Para pruebas de flujo interno, lo más probable es que se requieran desarrollar pruebas aisladas y controladas con bases de datos, por lo que el uso de `Testcontainers` podría ser suficiente. En otros casos, se podría requerir el uso de pruebas con otros microservicios para comprobar la funcionalidad de flujos completos; en cuyo caso sería recomendable utilizar esta herramienta en conjunto con `Docker Compose`. 

En la Tabla 1 se encuentran algunas consideraciones que podrían ayudar a seleccionar una u otra. __Recuerda que pueden existir casos donde sea viable usar ambas__.

| __Criterio__ | __Docker Compose__ | __Testcontainers__ |
| ------------ | ------------------ | ------------------ |
| Ambientes con múltiples servicios (contenedores) | Comportamiento predefinido. Especial para este tipo de situaciones. | Requiere múltiples configuraciones en Java puro. Opción menos recomendada. |
| Aislamiento y repetitividad del test | Contenedores no aislados. Los servicios persisten en cada test. | Contenedores completamente aislados y creados por cada test. |
| Configuración dinámica | Limitada. Sólo se define a través del `compose.yaml` | Completamente dinámica. Definida mediante programación. |
| Portabilidad | Requiere que esté instalado Docker Compose. | Trabaja siempre que Docker esté instalado. |
| CI/CD | Ideal para ambientes compartidos que requieren persistencia de datos. | Ideal para ambientes de pruebas aisladas. |


## 3. Pruebas de flujo interno



## 4. Integración con otros microservicios

