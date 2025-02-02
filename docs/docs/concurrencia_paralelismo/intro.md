---
sidebar_position: 1
---

# Introducción

El enfoque de desarrollo backend tradicional es el de construir un proyecto de software (por ejemplo, microservicios) desde una base funcional, y está bien. No todos los prototipos de software necesitan proyectarse a ser consumidos por millones de personas en el mundo. Incluso, muchas startups inician con el lanzamiento de un MVP (_"Mininum Viable Product"_) y, cuando alcanzan cierto grado de éxito, deciden reinventar su aplicación para abarcar a todo su nuevo ecosistema de consumidores. Pero, ¿es posible diseñar la arquitectura de una aplicación pensando en que sea consumida por la mayor cantidad de personas posible y al menor costo? La respuesta corta es sí.

Si queremos abarcar a múltiples personas, en diferentes regiones del mundo, significa que tendremos que tener servidores en distintos puntos geográficos. Bajo esa premisa, existe la probabilidad de que se den escenarios donde un servidor en una región deba comunicarse con el de otra región, lo que tomará un tiempo de llegada de la petición y otro adicional para recibir la respuesta de dicha comunicación. Ese tiempo transcurrido se conoce como _latencia_.

<img src="../../img/concurrencia_paralelismo/intro/latencia.png" width="700px" />

Figura 1. Concepto de latencia. __Fuente:__ [kinsta.com](https://kinsta.com/blog/network-latency/)

Si bien dentro de la arquitectura de la aplicación no contemplamos la definición de los servidores que usaremos (esto se define dentro de la _arquitectura de solución_), sí es importante que nuestra aplicación esté preparada por si se da este tipo de escenarios. Debido a ello, hablaremos sobre los conceptos de _programación funcional_ y _programación reactiva_, que habilitarán el procesamiento de solicitudes entre servidores de forma asíncrona.

Ahora, cuando hablamos de eficiencia en desarrollo de software, nos referimos a la optimización de los recursos computacionales de los servidores en donde se alojan nuestras aplicaciones. Esto se logra distribuyendo de forma efectiva la carga de trabajo del servidor en los CPUs disponibles, de forma paralela, a través de _threads_, como se muestra en la Figura 2.

<img src="../../img/concurrencia_paralelismo/intro/threads.png" width="600px" />

Figura 2. Concepto de concurrencia. __Fuente:__ Bazlur Rahman, A. N. _"Modern Concurrency in Java"_. O'Reilly.

