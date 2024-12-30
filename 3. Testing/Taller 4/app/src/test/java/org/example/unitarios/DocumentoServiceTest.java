package org.example.unitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Calendar;

import org.example.modelos.Cliente;
import org.example.modelos.Documento;
import org.example.servicios.DocumentoService;
import org.example.servicios.DocumentoValidaciones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        //Si lo creo correctamente, debería ser capaz de retornarlo con la siguiente información
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
