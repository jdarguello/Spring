package com.Bancolombia.InversionVirtual.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.Bancolombia.InversionVirtual.repositories.ClienteRepository;
import com.Bancolombia.InversionVirtual.repositories.CuentaBancariaRepository;
import com.Bancolombia.InversionVirtual.repositories.InversionVirtualRepository;

@DataJpaTest
@ActiveProfiles("test_unitarios")
public class InversionVirtualTest extends ModelosTest {
    
    @Autowired
    private InversionVirtualRepository inversionRepository;

    @Autowired
    private CuentaBancariaRepository cuentaRepository;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();  //Ejecuta la lógica del padre

        //Crear la cuenta bancaria de Fernanda, para que sirva 
        //como cuenta de Origen y Destino
        cuentaRepository.save(cuentaFernanda);
    }

    @AfterEach
    public void tearDown() {
        cuentaRepository.deleteAll();
    }

    @Test
    public void saveAndFind() {
        inversionFernanda = inversionRepository.save(inversionFernanda);

        InversionVirtual inversionObtenida = inversionRepository.findById(inversionFernanda.getInversionId()).get();

        assertNotNull(inversionObtenida);
        assertEquals("Fernanda Aristizabal", inversionObtenida.getCliente().getNombre());
        assertEquals(31398734562L, inversionObtenida.getCuentaOrigen().getNumeroCuenta());
        assertEquals(31398734562L, inversionObtenida.getCuentaDestino().getNumeroCuenta());
    }
}
