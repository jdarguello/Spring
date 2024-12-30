---
sidebar_position: 1
---

# Taller 4

El objetivo de este taller es aprender sobre las bases de construcción de test unitarios. Para ello, no usaremos Spring Boot, sólo __JUnit__ y __Mockito__. El problema está ambientado en construir la lógica de inversión virtual de los clientes del BancoC, con base en las capas de _Modelos_ y _Servicios_ de la Figura 1.

![](../../../static/img/testing/Taller%204/diagramas.webp)

Figura 1. Modelos y servicios.

## 1. Adecuación del proyecto

Lo primero es habilitar la estructura de nuestro proyecto. Para ello, podemos usar diferentes estrategias. A pesar de no ser un proyecto en Spring Boot, se puede crear con Spring Initializr. También se puede habilitar el proyecto con Gradle ejecutando el siguiente comando:

```bash
gradle init
```

Lo que habilitará una serie de formularios. Para este caso, se recomienda:

1. Type of project: `Application` 
2. Implementation language: `Java`
3. Build script: `Groovy`
4. Test Framework: `JUnit Jupiter`
5. Project name: `Taller4`

Habilitando la siguiente estructura:

```
/Taller4
├── /app
|   |   ├──/src
|   |   |   ├── /java/org/example
|   |   |   |   ├── App.java
|   |   ├── /test
|   |   |   ├── /java/org/example
|   |   |   |   ├── AppTest.java
├── build.gradle
```

## 2. Modelos

A continuación, definiremos los modelos descritos en la Figura 1.

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<Tabs>
    <TabItem value="doc" label="Documento" default>
    ```java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Documento {
        private Long documentoId;
        private Cliente cliente;
        private String numeroDoc;
        private String tipo;
    }
    ```
    </TabItem>
    <TabItem value="cuenta" label="Cuenta Bancaria">
    ```java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CuentaBancaria {
        private Long cuentaId;
        private Documento documento;
        private Long numeroCuenta;
        private Double saldo;
        private Date fechaCreacion;
    }
    ```
    </TabItem>
    <TabItem value="inv" label="Inversión Virtual">
    ```java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class InversionVirtual {
        private Long inversionId;
        private CuentaBancaria cuentaOrigen;
        private CuentaBancaria cuentaDestino;
        private Cliente cliente;
        private Double valor;
        private Date fechaCreacion;
        private Date tiempoDuracion;

        public Date getFechaRetiro () {
            return //calcular fecha de retiro
        }
    }
    ```
    </TabItem>
    <TabItem value="cliente" label="Cliente">
    ```java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Cliente {
        private Long clienteId;
        private String nombre;
        private List<Documento> documentos;
        private List<CuentaBancaria> cuentas;
        private List<InversionVirtual> inversiones;
        private Date fechaVinculacion;

        public Double getTotalSaldo() {
            return //calcular total del saldo de las cuentas bancarias + inversiones
        }
    }
    ```
    </TabItem>
</Tabs>

En general, los modelos sólo se emplean como entidades que no suelen implementar ningún tipo de lógica. Debido a ello, no es necesario realizar test unitarios excepto en aquellos casos donde sí haya alguna validación lógica.

## 3. Servicios

La capa de servicios contiene la lógica de negocio. Esta es la capa que debemos trabajar con _Test-Driven Development_ (TDD, por sus siglas en inglés). Como se aprecia en la Figura 1, las clases a construir deben implementar una serie de interfaces que se deben relacionar entre sí. Al ser abstracciones, podemos crearlas directamente. Al momento de acoplarlas con las clases de servicio, construiremos primero los test unitarios con los requerimientos funcionales de cada una.

### 3.1. Validaciones de documentos

<Tabs>
    <TabItem value="req-docs" label="Requerimientos funcionales" default>
Los requerimientos funcionales de esta sección incluyen:

1. Todos los documentos registrados deben tener los atributos definidos. No hay argumentos opcionales.
2. Los tipos de documentos incluyen:
    * `CC`: Cédula de Ciudadanía. Debe contener sólo números, entre 8 y 10 digitos.
    * `TI`: Tarjeta de Identidad. Debe contener sólo números, entre 6 y 8 digitos.
    * `CE`: Cédula de Extranjería. Debe contener sólo números entre 6 y 8 digitos.
    * `PP`: Pasaporte. Debe contener 2 letras y 6 números.
3. Si al validar la existencia de un documento, no se encuentra registrado, debe fallar con el mensaje: `"404: Documento no encontrado"`.
4. Sólo se deben habilitar operaciones para:
    * Crear un nuevo documento.
    * Analizar si ya existe un documento registrado.
    * Obtener un documento __sin obtener toda la lista registrada__.
    * Eliminar un documento.
5. No habilitar método de actualización de documentos (se presta para problemas legales a futuro).
6. No se pueden eliminar documentos con cuentas bancarias vinculadas.
</TabItem>
<TabItem value="test-docs" label="Test unitarios">
A continuación, se definen los test unitarios que el código debe aprobar para cumplir con los requerimientos funcionales definidos.

```java
public class DocumentoServiceTest {
    
    private DocumentoValidaciones validaciones;

    //Clientes
    private Cliente Juan;
    private Cliente Fernanda;

    //Documentos
    private Documento documentoJuan;
    private Documento documentoFernanda;

    @BeforeEach
    void setUp () {
        validaciones = new DocumentoService();

        //Fecha de vinculación
        Calendar calendar = Calendar.getInstance();
        calendar.set(1992, Calendar.FEBRUARY, 10);

        //Clientes
        Juan = Cliente.builder()
        .nombre("Juan Esteban Hernandez")
        .fechaVinculacion(calendar.getTime())
        .build();

        calendar.set(2001, Calendar.MAY, 16);
        Fernanda = Cliente.builder()
        .nombre("Fernanda Aristizabal")
        .fechaVinculacion(calendar.getTime())
        .build();

        //Documentos
        documentoJuan = Documento.builder()
            .cliente(Juan)
            .numeroDoc("10982908")
            .tipo("CC")
            .build();

        documentoFernanda = Documento.builder()
            .cliente(Fernanda)
            .numeroDoc("AE392183")
            .tipo("PP")
            .build();
    }

    //Requerimientos funcionales 1, 2 y 4
    @Test
    void nuevoDocumentoLegitimo() {
        //Creación de CC
        documentoJuan = validaciones.nuevoDocumento(documentoJuan);

        //Si lo creó correctamente, debería ser capaz de retornarlo con la siguiente información
        Documento documentoObtenido = validaciones.getDocumento(documentoJuan.getDocumentoId());
        assertEquals(documentoObtenido.getNumeroDoc(), "10982908");
        assertEquals(documentoObtenido.getTipo(), "CC");
        assertEquals(documentoObtenido.getCliente().getNombre(), "Juan Esteban Hernandez");

        //Creación de PP
        documentoFernanda = validaciones.nuevoDocumento(documentoFernanda);

        documentoObtenido = validaciones.getDocumento(documentoFernanda.getDocumentoId());
        assertEquals(documentoObtenido.getNumeroDoc(), "AE392183");
        assertEquals(documentoObtenido.getTipo(), "PP");
        assertEquals(documentoObtenido.getCliente().getNombre(), "Fernanda Aristizabal");
    }

    //Requerimiento funcional 2
    @Test
    void nuevoDocumentoFalso () {
        //Creación de documentos por fuera de las especificaciones
        //Pasaporte con una letra
        documentoFernanda.setNumeroDoc("A29394");

        Exception exception = assertThrows(
            Exception.class, 
            () -> this.validaciones.nuevoDocumento(documentoFernanda)
        );

        assertEquals(exception.getMessage(), "Documento no válido: un PP debe tener dos letras y seis números.");

        //Cédula  con pocos digitos
        documentoJuan.setNumeroDoc("123");

        exception = assertThrows(
            Exception.class, 
            () -> this.validaciones.nuevoDocumento(documentoJuan)
        );

        assertEquals(exception.getMessage(), "Documento no válido: un CC debe tener entre 8 a 10 digitos.");

        //Cédula con muchos digitos
        documentoJuan.setNumeroDoc("123456789101112");

        exception = assertThrows(
            Exception.class, 
            () -> this.validaciones.nuevoDocumento(documentoJuan)
        );

        assertEquals(exception.getMessage(), "Documento no válido: un CC debe tener entre 8 a 10 digitos.");
    }


    //Requerimientos funcionales 3 y 4
    @Test
    void getDocumento () {
        //Guardar un documento válido
        documentoFernanda = validaciones.nuevoDocumento(documentoFernanda);

        //Validar que exista
        assertTrue(validaciones.existeDocumento(documentoFernanda.getDocumentoId()));

        //Validar que NO exista un documento random
        Long idRandom = 1293843292L;
        assertFalse(validaciones.existeDocumento(idRandom));

        Exception exception = assertThrows(
            Exception.class, 
            () -> validaciones.getDocumento(idRandom)
        );

        assertEquals(exception.getMessage(), "404: Documento no encontrado");
    }
}
```
</TabItem>
</Tabs>


### 3.2. Operaciones de Cuentas Bancarias

Para las cuentas bancarias, se tiene los siguientes requerimientos funcionales.

1. Se debe crear una cuenta bancaria con un documento legítimo, por lo que debe cumplir con los requerimientos de la sección 3.1.
2. Cuando se crea una cuenta bancaria nueva, su saldo inicial debe ser cero. No puede tener otro valor.
3. El número de cuenta debe tener entre 8 y 12 caracteres. Sólo pueden ser números.
4. Los valores de las transacciones pueden ser por valores positivos (consignación) o negativos (retiros). No pueden ser cero. 
5. Después de cada transacción, la cuenta no puede quedar con saldo negativo. Debe fallar antes con error: `500: el saldo de la cuenta no puede ser negativo`.
6. Si la `cuentaId` no existe, debe fallar con el error: `404: no se encontró la cuenta bancaria con cuentaId = {cuentaId}`.
7. Antes de eliminar una cuenta bancaria, debe verificar que no haya sido registrada como cuenta bancaria de destino de una `InversionVirtual`.

### 3.3. Operaciones de Inversión Virtual

Las inversiones virtuales deben cumplir con los siguientes requerimientos.

1. Sólo se deben poder realizar dos operaciones de inversión:
    * Crear una nueva inversión: todos los campos son obligatorios.
    * Reclamar una inversión: la cuenta de destino debe existir. Sólo se puede reclamar después de la _fecha de retiro_.
2. Al reclamar una inversión, la cuenta bancaria de destino debe recibirlo en forma de consignación.
3. Al crear una inversión, se debe retirar de la cuenta de origen. La cuenta de origen debe tener saldo suficiente para ello, de lo contrario no se debe crear la inversión.

### 3.4. Operaciones del Cliente

Los requerimientos del cliente:

1. Para crear un cliente, sólo se requiere un `nombre` y una `fechaVinculacion`. Los `documentos`, `cuentas` e `inversiones` deben inicializarse como una lista vacía.
2. El cliente puede realizar las siguientes operaciones:
    * Registrar un nuevo documento.
    * Eliminar un documento existente.
    * Abrir o eliminar una cuenta bancaria.
    * Eliminar una cuenta bancaria.
    * Realizar transacciones con sus cuentas.
    * Abrir y reclamar inversiones.
3. Para eliminar a un cliente de la base de datos, este no debe tener registradas `inversiones`, `cuentas` ni `documentos` a su nombre. 