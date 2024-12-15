---
sidebar_position: 1
---

# Taller 2

Para este taller, extenderemos las funcionalidades básicas construidas en el [Taller 1](/docs/introduccion/primeros-pasos/taller1.md). Es decir, reconstruiremos las funcionaldiades de nuestro microservicio de productos de un e-commerce para que se conecte con una base de datos en ambiente local.

## 1. Generación del proyecto

Para este nuevo ejercicio, comenzamos con la generación del proyecto con las siguientes dependencias en [Spring Initializr](https://start.spring.io).

![](../../../static/img/spring%20data/Taller2/Initializr.png)

Figura 1. Metadata en Spring Initializr.

La estructura del proyecto se puede apreciar a continuación.

```bash
src/
  main/
    java/
      com/
        ecommerce/
          ProductoApiApplication.java   <-- Ejecución del proyecto
          controllers/
            ProductoController.java      <- @RestController
          repositories/
            ProductoRepository.jaba     <- interface repository
          services/
            ProductoService.java         <-- @Service (lógica de negocio)
          models/
            Producto.java                <-- POJO (data model)
    resources/
      application.yaml            <-- Archivo de configuración (debe crearse)

```

## 2. Creación y configuración de la base de datos

El primer paso es seleccionar la base de datos. Puedes escoger trabajar con una base de datos en memoria, o con una base de datos PostgreSQL en local. Una vez escojamos la base de datos, se recomienda cambiar el archivo `application.properties` por __`application.yaml`__.

### 2.1. Base de datos H2

La base de datos en memoria no requiere la instalación de ningún paquete de software. Sólo debes configurarla en el archivo `application.yaml` de la siguiente forma:

```yaml
spring:
    datasource: 
        url: jdbc:h2:mem:testdb
        driverClassName: org.h2.Driver
        username: sa
        password: password

    jpa:
        hibernate:
            ddl-auto: update
        show-sql: true

    h2:
        console:
            enabled: true
            path: /h2-console
```

Con esto, es suficiente para conectarnos a la base de datos.

### 2.2. Base de datos PostgreSQL

Para la base de datos PostgreSQL, el procedimiento es un poco más complejo. La forma sugerida es crearla a través del siguiente comando:

```bash
docker run -d --name taller2-db \
    -e POSTGRESQL_DATABASE=ecommerce \
    -e POSTGRESQL_USERNAME=ejemplo \
    -e POSTGRESQL_PASSWORD=password123 \
    -p 5432:5432 \
    bitnami/postgresql:latest
```


Una vez configurado el contendor con la base de datos, podremos conectarnos a ella ajustando el archivo `application.yaml` de la siguiente forma:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce
    username: ejemplo
    password: password123
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 8080
```
Para visualizar los cambios en la base de datos, se puede usar el UI propio de PostgreSQL o DBeaver.

## 3. Definición de modelos

A diferencia del Taller 1, el modelo de `Producto` debe ajustarse para poder utilizar Spring Data JPA. La versión ajustada se puede apreciar a continuación.

```java
package com.ecommerce.productosAPI.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="productoGen")
    @SequenceGenerator(name = "productoGen", sequenceName = "producto_sequence", allocationSize = 1)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidad;
}
```

## 4. Repository

Antes de definir la capa de _servicios_, es necesario especificar el repositorio que abstraerá la lógica de conexión con la base de datos.

```java
package com.ecommerce.productosAPI.repositories;

import com.ecommerce.productosAPI.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Produco, Long> {
}
```

## 5. Service

El servicio utiliza el repositorio definido en la sección 4 para realizar las operaciones con la base de datos. 

```java
package com.ecommerce.productosAPI.services;

import com.ecommerce.productosAPI.models.Producto;
import com.example.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
```

## 6. Controlador

El controlador se maneja exactamente de la misma forma que la expuesta en el [Taller 1](../../introduccion/primeros-pasos/taller1.md).
