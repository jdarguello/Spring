package com.Bancolombia.InversionVirtual.modelos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.Bancolombia.InversionVirtual.repositories.ClienteRepository;
import com.Bancolombia.InversionVirtual.repositories.CuentaBancariaRepository;
import com.Bancolombia.InversionVirtual.repositories.DocumentoRepository;
import com.Bancolombia.InversionVirtual.repositories.InversionVirtualRepository;

@DataJpaTest
@ActiveProfiles("test_unitarios")
public class ClienteTest extends ModelosTest {
    
    private static final Logger logger = LoggerFactory.getLogger(ClienteTest.class);

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private CuentaBancariaRepository cuentaRepository;
    @Autowired
    private InversionVirtualRepository inversionVirtualRepository;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        Fernanda.setDocumentos(new ArrayList<>());
        Fernanda.getDocumentos().add(documentoFernanda);
        logger.info(Fernanda.toString());
        logger.info("Hola");
        Fernanda = repository.save(Fernanda);
        System.out.println(Fernanda);
        
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void saveAndFind() {
        Cliente clienteObtenido = repository.findById(Fernanda.getClienteId()).get();

        assertNotNull(clienteObtenido);
        assertEquals("Fernanda Aristizabal", clienteObtenido.getNombre());
        assertTrue(clienteObtenido.getDocumentos().size() > 0);
        assertEquals("AE392183", clienteObtenido.getDocumentos().get(0).getNumeroDocumento());
    }
}
