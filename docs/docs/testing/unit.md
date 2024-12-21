---
sidebar_position: 2
--- 

# Test Unitarios

Un _test unitario_ se concibe como una prueba aislada y controlada en donde se verifica la veracidad de la lógica de un método o clase. Por ejemplo, si tenemos una función de `suma` como la mostrada a continuación.

```java
public class Calculadora {
    public Integer suma(Integer x, Integer y) {
        return x + y;
    }
}
```

Tal vez podamos pensar que, en sí misma, es suficiente para cualquier operación de suma. Pero, la cruda realidad, es que no. ¿Qué pasa si quiero sumar más de dos números?, ¿qué ocurre si, por alguna razón, uno de los valores a sumar es `Null`?, ¿y si necesito que los valores de la suma sólo puedan ser números positivos? Estos y más interrogantes corresponden a los __requerimientos funcionales__ de nuestra función `suma`. 

Para garantizar que nuestra operación de suma cumpla con todos los requerimientos, lo recomendable es aplicar la práctica de _Test-Driven Development_ (TDD), que consiste en construir un set de pruebas que, de aprobarse, garantizarán el cumplimiento de estos requerimientos. Para este ejemplo, podría ser algo como:

```java
@Test
public void sumarMuchosNumeros() {
    Calculadora calculadoraTest = new Calculadora();    //objeto de pruebas

    assertEquals(calculadoraTest.suma(2,5,3,10), 20);    //Suma de pruebas. Debe dar como resultado 20.
    assertEquals(calculadoraTest.suma(2,2,5), 9);       //Suma de pruebas. Debe dar como resultado 9.
}

@Test
public void sumaConNull() {
    Calculadora calculadoraTest = new Calculadora();    //objeto de pruebas

    //Tal vez, requiramos que, si hay un 'Null', lo ignore y sume todo lo que sí deba sumar
    assertEquals(calculadoraTest.suma(1, Null), 1);
    assertEquals(calculadoraTest.suma(1, 2, Null), 3);

    //O, tal vez necesitamos que falle la función, con un mensaje específico
    Exception exception = assertThrows(
        Exception.class,
        () -> calculadoraTest.suma(1, Null),
    );

    assertEquals("No se pueden sumar argumentos 'Null'", exception.getMessage());
}

@Test
public void sumarNegativos() {
    //Si hay algún número negativo, deberá fallar
    Exception exception = assertThrows(
        Exception.class,
        () -> calculadoraTest.suma(1, -2, 3),
    );

    assertEquals("No se permite sumar números negativos", exception.getMessage());
}
```

Como podemos apreciar, se construye un `@Test` unitario por cada requerimiento funcional. __Esto es TDD__. Al reconstruir la función que garantiza estos nuevos requerimientos, quedaría algo como:

```java
public class Calculadora {
    public Integer suma(Integer... numeros) {
        suma = 0;
        for (Integer numero : numeros) {
            if (numero == null) {
                throw new Exception("No se pueden sumar argumentos 'Null'");
            }
            else if (numero < 0) {
                throw new Exception("No se permite sumar números negativos");
            }
            else {
                suma += numero;
            }
        }
        return suma;
    }
}
```

Como podemos ver, los test unitarios permiten que nuestro desarrollo se anteponga ante diferentes situaciones, controlando su comportamiento y dando resultados medibles y esperables. __Cumpliendo con los requerimientos funcionales establecidos__. Además, y lo más importante, es que garantizan su funcionalidad base con el tiempo; de forma que, cuando lleguen nuevos requerimientos, los cambios en el software no rompen las funcionalidades anteriormente definidas, __garantizando la mantenibilidad__. 

## 1. Estructura base

La estructura base de un _test unitario_ es la siguiente:

JUnit fue el primer framework de desarrollo de pruebas unitarias en cualquier lenguaje de programación, lo que lo hace uno de las librerías más robustas para la elaboración de test unitarios. 

## 2. Mocks

