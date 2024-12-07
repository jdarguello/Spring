---
sidebar_position: 1
---

# Spring Boot: Conceptos base

Lo primero es entender cómo crear un proyecto de Spring Boot. La forma más sencilla de hacerlo es a través del proyecto gratuito conocido como __Spring Initializr__, que encontrarás en el [siguiente enlace](https://start.spring.io). 

![](../../../static/img/Intro-spring/initializr.png)

Figura 8. Entorno de Spring Initializr. __Fuente:__ Spring Boot.

En la Figura 8 se puede aprecir el ecosistema de generación de proyectos de Spring Boot. Una interfaz web genérica que, a partir de cierta metadata de entrada, estructura el proyecto de Spring Boot con todas las dependencias y librerías que se requieran.

Una aplicación de Spring Boot convencional, creada a partir de la metadata definida en Spring Initializr (ver Figura 8), presenta la siguiente estructura, en términos de carpetas y archivos base que componen el proyecto.

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
```

Dentro de la carpeta `src` se encuentran todos los archivos que componen la aplicación. En la carpeta `java` se aloja el código fuente de nuestra aplicación; en `resources`, estarán los archivos de configuración; y en `test` estarán los test unitarios y de integración. En los archivos `build.gradle` y `settings.gradle` se encontrarán las dependencias y configuraciones base del proyecto.

## 1. Runner

