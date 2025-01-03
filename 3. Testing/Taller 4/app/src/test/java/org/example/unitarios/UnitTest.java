package org.example.unitarios;

import java.util.Calendar;

import org.example.modelos.Cliente;
import org.example.modelos.Documento;
import org.junit.jupiter.api.BeforeEach;

public class UnitTest {

    //Clientes
    protected Cliente Juan;
    protected Cliente Fernanda;

    //Documentos
    protected Documento documentoJuan;
    protected Documento documentoFernanda;

    void setUp () {
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
    
}
