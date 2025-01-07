package org.example.Nequi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BilleteraTest {
    /**
     * REQUERIMIENTOS FUNCIONALES:
     *  1. En el momento de crear una billetera, debería crear un bolsillo vacío. También, el saldo
     *      del bolsillo debería estar en 0.0.
     *  2. Tiene que estar vinculado a un cliente y debe tener un número de celular. Adicional, 
     *      se debería verificar que no exista otra billetera con ese mismo número.
     *  3. El número de celular debe tener diez digitos.
     *  4. Si no se reconcoe el bolsillo de la transacción, debe fallar con un error 
     *      "500: no se reconoce el bolsillo con bolsilloId = {bolsilloId}"
     *  5. Debe sumar el saldo de todos los bolsillos asociados.
     */

     private Billetera billeteraJohan;

     private Cliente Johan;

     @BeforeEach
     public void setUp() {
        Johan = new Cliente("Johan Alvear");
     }

     @Test
     public void crearBilleteraExitosa () throws Exception {
        billeteraJohan = new Billetera(Johan, 3119083030L);

        assertNotNull(billeteraJohan.getBolsillos());
        assertEquals(billeteraJohan.getBolsillos().size(), 1);
        assertEquals(billeteraJohan.getBolsillos().get(0).getSaldo(), 0.0);

        assertEquals(billeteraJohan.getNumeroCelular(), 3119083030L);
     }

     @Test
     public void crearBilleteraFalsa () {
        Exception exception = assertThrows(
            Exception.class, 
            () -> new Billetera(Johan, 123L)    
        );

        assertEquals(exception.getMessage(), "500: el número de celular tiene que tener 10 digitos");
     }

     @Test
     public void transacciones () throws Exception {
        billeteraJohan = new Billetera(Johan, 3119083030L);

        //1. Nueva transacción a un bolsillo existente
        Bolsillo bolsilloJohan1 = billeteraJohan.getBolsillos().get(0);

        billeteraJohan.transaccion(10_000.0, bolsilloJohan1.getBolsilloId());

        assertEquals(bolsilloJohan1.getSaldo(), 10_000.0);
        assertEquals(billeteraJohan.getSaldoTotal(), 10_000.0);


        //2. Transacción a un bolsillo que no existe
        Exception exception = assertThrows(
            Exception.class, 
            () -> billeteraJohan.transaccion(500_000.0, 2L)    
        );

        assertEquals(exception.getMessage(), "500: no se reconoce el bolsillo con bolsilloId = 2");
        //3. Bolsillo se queda sin saldo
        assertThrows(
            Exception.class, 
            () -> billeteraJohan.transaccion(-400_000.0, bolsilloJohan1.getBolsilloId())
        );
     }


}
