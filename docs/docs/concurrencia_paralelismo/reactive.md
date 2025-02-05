---
sidebar_position: 2
---

# Programación Reactiva

El paradigma de programación reactiva disminuye el consumo de los recursos computacionales durante operaciones I/O, mejora la escalabilidad e incrementa la relación costo-eficiencia durante la comunicación con bases de datos o servicios externos en los flujos de trabajo de una aplicación. Los microservicios que hemos construido hasta el momento han sido con flujos de trabajo síncrono, también conocidos como _"one thread-per-request model"_. Es decir, aplicaciones que sólo procesan una petición a la vez. Este enfoque es contrario a las aplicaciones reactivas, que operan de forma _asíncrona_ y de forma _no bloqueante_, disminuyendo el consumo de los recursos computacionales; lo que representa una gran ventaja en aplicaciones de tipo _cloud-native_, en donde se paga por el uso de estos recursos. 

En este paradigma, cuando un _thread_ envía una petición a un servicio backend, este no esperará a recibir una respuesta, sino que continuará con la ejecución de otras operaciones de forma _paralela_; eliminando así la dependencia lineal entre el número de _threads_ y el número de peticiones concurrentes, incrementando la escalabilidad de las aplicaciones. 

## 1. Arquitecturas asíncronas y _no bloqueantes_

La programación reactiva se basa en el paradigma conocido como _"event loop"_, que permite incrementar la concurrencia de peticiones dentro de un microservicio, sin depender estrictamente del número de _threads_. De hecho, una configuración por default de proyectos reactivos en Spring es el uso de un _thread_ por core de CPU. La base de este paradigma se puede evidenciar en la Figura 1.

![](../../static/img/concurrencia_paralelismo/reactive/event-loop.png)

Figura 1. Modelo _event-loop_. __Fuente:__ Vitale, T. _"Cloud Native Spring in Action"_. Manning.

Como se aprecia en la Figura 1, este modelo maneja los requests por threads que no se bloquean mientras esperan por una operación intensiva, lo que les permite procesar otros requests mientras tanto.

Una de las características esenciales de este tipo de aplicaciones es que proveen un _control de flujo_ que permite a los consumidores controlar la cantidad de datos que reciben. Lo que disminuye el riesgo de que los productores envíen más datos de lo que los consumidores podrían manejar. Evitando posibles ataques por DoS, que relantece las aplicaciones, genera fallas en cascada o que incluso repercute en una falla total del sistema.

### 1.1. Proyecto Reactor: streams reactivos con Mono y Flux


