package com.Bancolombia.InversionVirtual.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;

public abstract class ModelosTest {

    //Clientes
    protected Cliente Fernanda;

    //Documentos
    protected Documento documentoFernanda;

    //Cuentas Bancarias
    protected CuentaBancaria cuentaFernanda;

    //Inversiones Virtuales
    protected InversionVirtual inversionFernanda;

    @BeforeEach
    public void setUp () {
        //Fecha de vinculación
        Calendar calendar = Calendar.getInstance();
        calendar.set(2001, Calendar.MAY, 16);

        //Clientes
        Fernanda = Cliente.builder()
        .nombre("Fernanda Aristizabal")
        .fechaVinculacion(calendar.getTime())
        .build();

        //Documentos
        documentoFernanda = Documento.builder()
            .cliente(Fernanda)
            .numeroDocumento("AE392183")
            .tipo("PP")
            .build();
        
        //Cuentas Bancarias
        cuentaFernanda = CuentaBancaria.builder()
            .numeroCuenta(31398734562L)
            .cliente(Fernanda)
            .documento(documentoFernanda)
            .build();

        //Inversiones Virtuales
        inversionFernanda = InversionVirtual.builder()
            .valor(200_000_000.0)
            .tiempoDuracion(LocalDate.of(0,6,1))
            .cliente(Fernanda)
            .cuentaOrigen(cuentaFernanda)
            .cuentaDestino(cuentaFernanda)
            .build();
    }
}
