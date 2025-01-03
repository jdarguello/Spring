package org.example.Nequi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BolsilloTest {
    /*
     * REQUERIMIENTOS FUNCIONALES:
     *  1. Cuando se cree un bolsillo, debería tener saldo en 0.0. Tiene que 
     *      tener un id
     *  2. Cuando se consigne, se debe incrementar el saldo.
     *  3. Cuando se retire, debe disminuir el saldo.
     *  4. Antes de retirar, debe corroborar que el saldo no sea negativo. 
     *      Si da negativo, debe informar con un error: 
     *      "500: el valor a retirar es mayor que el saldo disponible"
     */

     private static final Logger logger = LogManager.getLogger(BolsilloTest.class);

     private Bolsillo bolsilloMigue;

     @BeforeEach
     public void setUp () {
        bolsilloMigue = new Bolsillo();
     }

     //Cumple el primer requerimiento funcional
    @Test
    public void crearBolsillo() {
        //1. El id del bolsillo tiene que ser mayor que cero
        logger.info("Inicio de test (prueba)");
        assertTrue(bolsilloMigue.contadorIds > 0);
        assertEquals(bolsilloMigue.getBolsilloId(), 1L);

        //2. El saldo debe ser 0.0
        assertEquals(bolsilloMigue.getSaldo(), 0.0);
    } 

    //Requerimiento funcional #2
    @Test
    public void consignacion () {
        bolsilloMigue.consignacion(100_000.0);

        assertEquals(bolsilloMigue.getSaldo(), 100_000.0);

        bolsilloMigue.consignacion(50_000.0);

        assertEquals(bolsilloMigue.getSaldo(), 150_000.0);
    }

    //Requerimiento funcional #3 y #4
    @Test
    public void retiro() throws Exception {
        //Requerimiento #4
        Exception exception = assertThrows(
            Exception.class, 
            () -> bolsilloMigue.retirar(100_000.0)
        );

        assertEquals(exception.getMessage(), "500: el valor a retirar es mayor que el saldo disponible");
        
        //Requerimiento #3
        bolsilloMigue.consignacion(50_000.0);
        bolsilloMigue.retirar(10_000.0);

        assertEquals(bolsilloMigue.getSaldo(), 40_000.0);


    }
}
