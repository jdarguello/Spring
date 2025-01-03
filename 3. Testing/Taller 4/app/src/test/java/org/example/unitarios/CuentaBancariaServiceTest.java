package org.example.unitarios;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.example.servicios.CuentaBancariaOperaciones;
import org.example.servicios.CuentaBancariaService;
import org.example.servicios.DocumentoValidaciones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CuentaBancariaServiceTest {
    
    private DocumentoValidaciones documentoValidaciones;

    private CuentaBancariaOperaciones operaciones;

    @BeforeEach
    void setUp() {
        documentoValidaciones = mock(DocumentoValidaciones.class);
        operaciones = new CuentaBancariaService(documentoValidaciones);
    }

    @Test
    void ejemplo() {
    }

}
