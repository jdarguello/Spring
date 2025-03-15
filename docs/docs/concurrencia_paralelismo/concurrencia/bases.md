---
sidebar_position: 1
---

# Conceptos base

_"Concurrency is about dealing with lots of things at once. Parallelism is about doing lots of things at once."_ - Robert Pike

Java es un lenguaje que fue diseñado para ser concurrente desde un principio. Nació con la capacidad inhata de configurar el ciclo de vida de __threads__, lo que removió la necesidad de los programadores de realizar configuraciones, a nivel de sistema operativo, para alcanzar la concurrencia.

En Java, un _thread_ es la unidad más pequeña de ejecución. Es el camino independiente que transcurre un algoritmo durante la ejecución de un programa. Los threads se ejecutan en el mismo _environment_, lo que significa que cada uno de ellos tiene acceso a las mismas variables y estructuras de datos. Sin embargo, cada thread mantiene su propio _counter_, permitiéndole manejar sus propias variables locales y su habilidad de operar de forma independiente. Este diseño facilita la interacción entre múltiples threads cada vez que sea necesario. 

El sistema operativo distribuye el tiempo de CPU para cada thread activo; manejando la transición entre threads para asegurar una ejecución eficiente. Al distribuir los threads sobre los múltiples CPUs, el sistema puede alcanzar el verdadero paralelismo, incrementando el performance de las aplicaciones concurrentes.

<img src="../../../img/concurrencia_paralelismo/concurrencia/bases/concurrency.png" width="600px" />

Figura 1. Concepto de concurrencia. __Fuente:__ https://jenkov.com/images/java-concurrency/concurrency-vs-parallelism-4.png.

Entre más threads tengamos en un programa de Java, mayor cantidad de ambientes de ejecución podremos crear. Esto nos da la habilidad de ejecutar diferentes operaciones de forma simultánea; lo que es particularmente benéfico en aplicaciones que requieren altos niveles de concurrencia y paralelismo. 

## 1. _Threads:_ la columna vertebral de Java

Los threads en Java son una parte integral a todas las capas de la plataforma de Java, jugando un papel crucial en varios aspectos más alla de sólo ejecutar código. Por ejemplo, son la base del manejo de excepciones, debugging y perfilamiento de las aplicaciones en Java.

### 1.1. El génesis de Java 1.0

Lanzado en 1996, Java nació con funcionalidades incluidas para el manejo de Threads. Desde entonces, es posible crear threads ya sea extendiendo la clase `Thread` o implementando la interfaz `Runnable`. Por ejemplo:

```java
//Usando la clase 'Thread'
class ThreadEjemplo1 extends Thread {
    public void run() {
        System.out.println("Thread ejecutado por la clase 'Thread'");
    }
}

//Usando la interfaz 'Runnable'
class ThreadEjemplo2 implements Runnable {
    public void run() {
        System.out.println("Thread ejecutado por la interfaz 'Runnable'");
    }
}
```

Para iniciar el _thread_, sólo debemos ejecutar lo siguiente:

```java
Thread tarea = new ThreadEjemplo1();
tarea.start();
```

O

```java
Thread tarea = new Thread(new ThreadEjemplo2());
tarea.start();
```

### 1.2. Ejecución de Threads

Cualsea el método que escojamos para invocar un thread, todo empieza con el método `start()`. Esto es esencial porque más que ejecutar el método `run()` definido en las clases de ejemplo, también ejecuta tareas para distribuir los recursos del sistema. 

---
__Nota:__ es crucial no invocar el método `run()` directamente. Hacerlo ejecutaría el método en el thread existente, no en un nuevo thread.

---

Sin embargo, en las aplicaciones Java de la actualidad, usar el _Executors Framework_ es la forma más eficiente que crear threads mediante constructores. `Executors` abstrae el proceso del manejo de threads al mantener un pool de threads (grupo de threads pre-existentes listos para ser ejecutados). Este método disminuye la carga de crear threads durante el tiempo de ejecución de la aplicación.

Cuando una tarea se envía al `Executor`, esta se almacena en una cola de mensajería interna. Luego, un thread del pool toma la tarea de la cola de mensajería y la ejecuta. Esta configuración simplifica la programación concurrente y optimiza el uso de recursos destinado a cada thread.

El siguiente ejemplo muestra la forma de utilizar este framework:

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ExecutorExample {
    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newFixedThreadPool(5)) {
            for (int i = 0; i < 10; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    System.out.println("Ejecutando task " + taskId 
                            + " en thread " + Thread.currentThread().getName());
                });
            }
        }
    }
}
```

### 1.3. El costo oculto de los threads

Muchas de las aplicaciones web de la actualidad corren en un modelo de tipo _thread-per-request_, donde un thread es asignado por cada request. De esta forma, el _thread_ maneja todo el ciclo de vida del _request/response_. Por ejemplo, 