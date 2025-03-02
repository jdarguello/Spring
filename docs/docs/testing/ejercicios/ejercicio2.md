---
sidebar_position: 1
---

# Ejercicio 2

El presente ejercicio propone la creación de un microservicio de cuentas bancarias. El microservicio debe permitir la ejecución de dos tipos de transacciones:
* __Transacción en efectivo__:
    * _Consignación:_ emula el comportamiento de un cliente de acercarse a una sucursal física y entregarle su dinero en efectivo al cajero para incrementar el saldo de su cuenta bancaria.
    * _Retiro:_ emula la acción de un cliente de acercarse a un cajero electrónico y solicitar el retiro de dinero en efectivo a través de su tarjeta débito.
* __Movimiento bancario:__ se trata de un movimiento de dinero por medios electrónicos. El cliente especifica el monto de la transacción y la información de la cuenta de otro cliente a donde desea depositar su dinero.
    * _Cuenta de Origen:_ trae consigo la información de la cuenta del cliente desde donde se realiza la transacción. Al procesar la transacción, se debitará el monto de su cuenta bancaria.
    * _Cuenta de Destino:_ se trata de la cuenta hacia donde se desea enviar el dinero.

En la Figura 1 se puede apreciar la propuesta de arquitectura de la aplicación del presente ejercicio.

![](../../../static/img/testing/Ejercicio%202/saldos-micro.png)

Figura 1. Arquitectura de la aplicación.

Como se aprecia en la Figura 1, la arquitectura consiste en tres capas: _Modelos_, _Servicios_ y _Controladores_. Dentro de la capa de modelos, la clase `Cliente`, contenida en el módulo _"ext"_, se trata de un simple POJO (_"Plain Old Java Object"_) que se utiliza para representar la información de un cliente. Este microservicio no debería persistir la información de los clientes, pero sí debería recibirla en algunas APIs para el desarrollo de validaciones y el procesamiento de las transacciones. Las otras enitdades propuestas sí deberían ser persistidas. 

## 1. Solución

La construcción de este microservicio es retador y se recomienda intentar hacerlo por cuenta propia para obtener la mejor experiencia de aprendizaje. Sin embargo, es útil saber que la solución propuesta se encuentra en el siguiente repositorio [https://github.com/jdarguello/Spring-Taller6-CuentaBancaria](https://github.com/jdarguello/Spring-Taller6-CuentaBancaria). 

Esta solución fue construida usando TDD (_Test-Driven Development_) y fue evaluada mediante SonarQube Cloud, cuyos resultados se pueden apreciar en la Figura 2.

![](../../../static/img/testing/Ejercicio%202/sonar.png)

Figura 2. Resultados de Sonar.

Como se puede apreciar, se trata de una solución de alta calidad, con un margen de cobertura de casi el __96%__ y sin líneas duplicadas en el código (__0.0%__).

La solución también está contenerizada y liberada en DockerHub para ser usada en ejercicios y talleres posteriores. Se puede acceder a través de la siguiente URL: [https://hub.docker.com/repository/docker/jdarguello10/spring-taller6-cuenta/general](https://hub.docker.com/repository/docker/jdarguello10/spring-taller6-cuenta/general).
