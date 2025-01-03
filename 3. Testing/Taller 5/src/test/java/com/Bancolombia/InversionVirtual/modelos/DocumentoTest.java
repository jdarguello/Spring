package com.Bancolombia.InversionVirtual.modelos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.Bancolombia.InversionVirtual.repositories.DocumentoRepository;

@DataJpaTest
@ActiveProfiles("test_unitarios")
public class DocumentoTest {
    
    @Autowired
    private DocumentoRepository repository;

    //Clientes
    private Cliente Fernanda;

    //Documentos
    private Documento documentoFernanda;

    @BeforeEach
    void setUp () {
        //Fecha de vinculación
        Calendar calendar = Calendar.getInstance();
        calendar.set(2001, Calendar.MAY, 16);

        //Clientes
        Fernanda = Cliente.builder()
        .nombre("Fernanda Aristizabal")
        .fechaVinculacion(calendar.getTime())
        .build();

        documentoFernanda = Documento.builder()
            .cliente(Fernanda)
            .numeroDocumento("AE392183")
            .tipo("PP")
            .build();
    }

    @Test
    public void saveAndFind() {
        documentoFernanda = repository.save(documentoFernanda);

        Documento documentoObtenido = repository.findById(documentoFernanda.getDocumentoId()).get();

        assertNotNull(documentoObtenido);
        assertEquals(documentoObtenido.getNumeroDocumento(), "AE392183");
        assertEquals(documentoObtenido.getTipo(), "PP");
    }

    @Test
    public void delete() {
        documentoFernanda = repository.save(documentoFernanda);

        repository.delete(documentoFernanda);

        assertThrows(
            Exception.class,
            () -> repository.findById(documentoFernanda.getDocumentoId()).get()
        );
    }
}