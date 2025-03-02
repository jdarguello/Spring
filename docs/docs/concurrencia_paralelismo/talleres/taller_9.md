---
sidebar_position: 1
---

# Taller 9

Hasta este momento, hemos trabajado de forma imperativa con Spring MVC y Spring Data JPA. En el presente taller, aprenderemos sobre las generalidades de trabajar con Spring WebFlux y Spring Data R2DBC. Veremos que los patrones y principios que hemos aplicado hasta el momento los seguiremos usando en aplicaciones reactivas. La principal diferencia será que cambiaremos la implementación de la lógica de negocio de forma imperativa al uso de streams reactivos procesados de forma asíncrona. 

Para ello, usaremos como ejemplo lo construido en el [Taller 3](../../data/Talleres/taller3.md), donde definimos la lógica básica de un carrito de compras. 

## 1. Configuración del proyecto

En primer lugar, empezaremos por generar el proyecto base. Usaremos [Spring Initialzr](https://start.spring.io) con la configuración mostrada en la Figura 1.

![](../../../static/img/concurrencia_paralelismo/taller6/initializr.png)

Figura 1. Generación del proyecto con Spring Initialzr.

A continuación, se explican brevemente las dependencias principales:

* _Spring Reactive Web:_ provee las librerias necesarias para la construcción de aplicaciones web reactivas con Spring WebFlux y Netty como servidor embebido por default.
* _Spring Data R2DBC:_ importa las libererias base necesarias para la persistencia de datos relacionales de aplicaciones reactivas.
* _Validation:_ se emplea en procesos de validación. Basado en el Java Bean Validation API.

A continuación, configuraremos el servidor de aplicación Netty en el `application.yaml` de la siguiente forma:

```yaml
server:
  port: 8080                        1️⃣              
  shutdown: graceful                2️⃣          
  netty:
    connection-timeout: 2s          3️⃣            
    idle-timeout: 15s               4️⃣     
 
spring:
  application:
    name: carrito-compras
  lifecycle:
    timeout-per-shutdown-phase: 15s 5️⃣
```

1. Define el puerto en el que es posible acceder a la aplicación.
2. Habilita el _graceful shutdown_. Al recibir una solicitud de apagado, el sistema dejará de recibir nuevas peticiones, terminará de completar las peticiones que se encuentren en curso y liberará los recursos antes de proceder con el apagado.
3. Cantidad de tiempo que esperará el sistema para una conexión TCP que se establezca con el servidor.
4. Cantidad de tiempo a esperar antes de cerrar la conexión TCP en caso de que no se produzca la transferencia de datos.
5. Define un periodo de gracia de 15 segundos para cada periodo de apagado. Si demora más de este tiempo, Spring forzará el apagado de la aplicación.

## 2. Modelos

Antes de empezar a redefinir los modelos vistos en el [Taller 3](../../data/Talleres/taller3.md), iniciaremos por configurar la conexión a la base de datos. En primer lugar, iniciaremos por definir el `compose.yaml` para crear el contenedor con una base de datos PostgreSQL con Docker Compose:

```yaml
services:
  carrito-db:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=taller9'
      - 'POSTGRES_PASSWORD=ejemplo123'
      - 'POSTGRES_USER=usuario'
    ports:
      - '5432'
```

Luego, ajustaremos el `application.yaml` para definir los parámetros de conexión a la base de datos.

```yaml
spring:
    r2dbc: 
        username: usuario                                    1️⃣
        password: ejemplo123                                 2️⃣
        url: r2dbc:postgresql://localhost:5432/taller9       3️⃣
        pool: 
            max-create-connection-time: 2s                   4️⃣
            initial-size: 5                                  5️⃣
            max-size: 10                                     6️⃣
```

1. Define el nombre de usuario definido.
2. Especifica la contraseña de conexión.
3. Protocolo y url de conexión a la base de datos de PostgreSQL.
4. Tiempo máximo de espera para establecer una nueva conexión con la base de datos.
5. Tamaño inicial del _pool_ de conexión. Garantiza que hayan, al menos, 5 conectores establecidos con la base de datos.
6. Cantidad máxima de conexiones mantenidas en le pool de conexión. Limita la cantidad máxima de conexiones concurrentes con la base de datos para evitar una sobrecarga.

Como se aprecia, estas configuraciones ayudan a incrementar el performance de una aplicación. 

### 2.1. Producto

