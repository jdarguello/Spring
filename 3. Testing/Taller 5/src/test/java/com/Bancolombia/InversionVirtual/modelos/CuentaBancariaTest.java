package com.Bancolombia.InversionVirtual.modelos;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.Bancolombia.InversionVirtual.repositories.CuentaBancariaRepository;

@DataJpaTest
@ActiveProfiles("test_unitarios")
public class CuentaBancariaTest extends ModelosTest {
    
    @Autowired
    private CuentaBancariaRepository repository;

    @Test
    public void saveAndFind() {
        cuentaFernanda = repository.save(cuentaFernanda);

        CuentaBancaria cuentaObtenida = repository.findById(cuentaFernanda.getCuentaId()).get();

        assertNotNull(cuentaObtenida);
        assertEquals("Fernanda Aristizabal", cuentaObtenida.getCliente().getNombre());
        assertEquals(31398734562L, cuentaObtenida.getNumeroCuenta());
    }

    @Test
    public void delete() {
        cuentaFernanda = repository.save(cuentaFernanda);

        repository.delete(cuentaFernanda);

        assertThrows(
            Exception.class,
            () -> repository.findById(cuentaFernanda.getCuentaId()).get()
        );
    }
}
