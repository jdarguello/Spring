package org.example.Nequi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    /**
     * REQUERIMIENTOS FUNCIONALES:
     *  1. El cliente debe tener todos sus atributos definidos. 
     *  2. La fecha debería autogenerarse en el momento en que se crea
     *  3. El nuevo cliente debe tener una lista de billeteras vacías.
     *  4. El cliente debe poder agregar nuevas billeteras. No se debe poder repetir billeteras.
     *     Retorna "true" si la añadió de forma exitosa, si retorna "false" si ya existe.
     *  5. Desde el cliente, se debe poder obtener el saldo total de las billeteras.
     * 
     */
 
    private Billetera billeteraJohan1;
    private Billetera billeteraJohan2;

    private Cliente Johan;

    @BeforeEach
    public void setUp() {
        billeteraJohan1 = mock(Billetera.class);
        billeteraJohan2 = mock(Billetera.class);

        when(billeteraJohan1.getSaldoTotal()).thenReturn(50_000.0);
        when(billeteraJohan2.getSaldoTotal()).thenReturn(102_500.0);

        when(billeteraJohan1.getBilleteraId()).thenReturn(1L);
        when(billeteraJohan2.getBilleteraId()).thenReturn(2L);

        Johan = new Cliente("Johan Alvear");
    }

    //Requerimiento #1, #2 y #3
    @Test
    public void nuevoCliente() {
        assertEquals(Johan.getBilleteras().size(), 0);
        assertNotNull(Johan.getFechaVinculacion());
        assertEquals(Johan.getNombre(), "Johan Alvear");
        assertEquals(Johan.getClienteId(), 1L);
    }

    //Requerimiento #4
    @Test
    public void addBilletera() {
        //Esperamos que añada la billetera
        assertTrue(Johan.addBilletera(billeteraJohan1));
        assertEquals(Johan.getBilleteras().size(), 1);

        //Como ya está añadida, debe retornar "false" y continuar
        assertFalse(Johan.addBilletera(billeteraJohan1));
        assertEquals(Johan.getBilleteras().size(), 1);

        assertTrue(Johan.addBilletera(billeteraJohan2));
    }

    //Requerimiento #5
    @Test
    public void getSaldoTotal() {
        Johan.addBilletera(billeteraJohan1);

        assertEquals(Johan.getSaldoTotal(), 50_000.0);

        Johan.addBilletera(billeteraJohan2);

        assertEquals(Johan.getSaldoTotal(), 152_500.0);

    }

}
