---
sidebar_position: 1
---

# Introducción

En el capítulo anterior, entendimos cómo crear un microservicio básico con APIs REST. En muchos patrones de arquitectura de microservicios, se tiene la necesidad de almacenar datos de forma persistente. La mayoría de las situaciones requieren el uso de bases de datos relacionales (SQL). El uso de bases de datos NoSQL está fuera del alcance de este material.

En Spring Boot, y las aplicaciones Java en general, se utiliza el DAO (_Data Access Object_) como patrón de diseño para desacoplar la lógica de negocio de la lógica de persistencia de datos. __Spring Data__ abstrae este concepto y se complementa con Hibernate como ORM (_Object-Relational Mapping_) para simplificar la persistencia de datos en aplicaciones de Spring Boot. 

## 1. Bases de datos SQL y _relaciones entre tablas_

Las bases de datos relacionales, basadas en el lenguaje SQL, funcionan bajo el concepto de _filas_ y _columnas_. La primera hace referencia a la información almacenada por diferentes objetos, mientras que la segunda define los atributos que caracterizan los objetos. Como ejemplo de lo anterior, analicemos brevemente el contenido de la Tabla 1.

| __PersonaID__ | __Nombre__ | __Edad__ | __Sexo__ |
| ------------- | ---------- | -------- | -------- |
| 1             | John       | 24       | Masculino |
| 2             | Fernanda   | 18       | Femenino |
| 3             | Laura      | 30       | Femenino | 
| 4             | Esteban    | 15       | Masculino

Tabla 1. Ejemplo de clientes.

Como podemos apreciar en la Tabla 1, `John` se puede interpretar como un objeto (tal vez, tipo `Persona`, por ejemplo), y la `Edad` y `Sexo` son algunos de sus atributos. Para hacer este ejercicio dinámico, supongamos que la Tabla 1 procede de una aplicación bancaria. Resulta que cada una de estas personas tiene, al menos, una cuenta bancaria. Digamos que John tiene 2 cuentas, una de _ahorros_ y otra _corriente_. ¿Cómo se pueden relacionar la información de las _cuentas_ con las _personas_? A través de __relaciones entre tablas__. 

Antes de adentrarnos en resolver esta última pregunta, es importante entender que existen 4 tipos de relaciones entre tablas: _One-To-One_, _One-To-Many_, _Many-To-One_ y _Many-To-Many_. 

### 1.1. Relación _One-To-One_

La relación uno-a-uno permite establecer la relación entre una tabla y otra. Por ejemplo, tomando como referencia la Tabla 1, que describe la información de diferentes personas, sabemos que cada una de ellas tiene una dirección de residencia, como se aprecia en la Tabla 4.

| __ResidenciaID__ | __Ciudad__ | __Barrio__ | __Dirección__ | __PersonaID__ |
| ---------------- | ---------- | ---------- | ------------- | ------------- |
| 1                | Bogotá     | Usaquén    | Cll 13a #102  | 3             |
| 2                | Medellín   | El Poblado | Cra 35b #22   | 1             |
| 3                | Cúcuta     | El Doral   | Cll 12 #03    | 2             |

Tabla 4. Relación _One-to-One_ entre una __persona__ y su __dirección de residencia__.

De la Tabla 4, podemos distinguir la dirección de residencia de las personas descritas en la Tabla 1. Por ejemplo, ya sabemos que John (`PersonaID=1`) vive en Medellín, que Fernanda (`PersonaID=2`) vive en Cúcuta y que Laura (`PersonaID=3`) vive en Bogotá.

### 1.2. Relación _Many-To-Many_

En las relaciones _Muchos-A-Muchos_, se entiende que varios objetos de una tabla pueden relacionarse con varios objetos de otra, al mismo tiempo. Para facilitar el entendimiento, sabemos que una persona puede tener múltiples cuentas bancarias a su nombre, ya sea cuenta de _ahorros_ o _corriente_, y que algunas de ellas pueden ser compartidas. Por ejemplo: un padre puede abrirle una cuenta bancaria a su hijo menor de edad, en cuyo caso, la cuenta pertenecerá a las dos personas al mismo tiempo.

| __CuentaID__ | __Número_cuenta__ | __Tipo_cuenta__ | __Saldo__ | 
| ------------ | ----------------- | --------------- | ----------- |
| 1            | 56929384          | Ahorros         | $100,000 USD |
| 2            | 56382947          | Corriente       | $200 USD    |
| 3            | 56213723          | Ahorros         | $1,102 USD  | 
| 4            | 56213724          | Ahorros         | $2,305 USD  | 
| 5            | 56282371          | Corriente       | $23,500 USD |

Tabla 5. Ejemplo de cuentas bancarias.

En la Tabla 5, observamos la información de diferentes cuentas bancarias, donde podemos ver el número de cuenta, tipo de cuenta y saldo. Sin embargo, no hay ninguna forma de relacionar la información de las cuentas con las de las personas. ¿Qué se debería hacer? Deberíamos crear una tabla intermedia que relacione la información de las dos cuentas. _¿Cómo?_, a través de los atributos `PersonaID` (Tabla 1) y `CuentaID` (Tabla 5).

| __Cuenta_Persona_ID__ | __PersonaID__ | __CuentaID__ |
| --------------------- | ------------- | ------------ |
| 1                     | 1             | 1            |
| 2                     | 1             | 2            |
| 3                     | 2             | 3            |
| 4                     | 3             | 4            |
| 5                     | 4             | 5            |
| 6                     | 1             | 5            |

Tabla 6. Ejemplo de relación entre las Tablas 1 y 5.

Con la información expuesta en la Tabla 6, ya podemos conocer la información bancaria de las personas expuestas en la Tabla 1. Por ejemplo, con base en esta información, podemos identificar que Fernanda (`PersonaID=2`), tiene una cuenta de ahorros (`CuentaID=3`), por lo que tiene ahorrado $1,102 USD. De igual manera, John (`PersonaID=1`), tiene dos cuentas bancarias: una de ahorros y otra corriente. Además, Esteban (`PersonaID=4`) comparte una cuenta corriente (`CuentaID=5`) de $23,500 USD con John (`PersonaID=1`).

### 1.3. Relaciones _One-To-Many_ y _Many-To-One_

En este tipo de relaciones, se establece que un objeto en una tabla se relaciona con múltiples en otra. Por ejemplo, muc

Ejemplo de transacciones bancarias con las cuentas.