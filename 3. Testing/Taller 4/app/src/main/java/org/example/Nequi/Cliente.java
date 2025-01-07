package org.example.Nequi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class Cliente {
    private static Long idsClientes = 0L;
    private Long clienteId;
    private String nombre;
    private List<Billetera> billeteras;
    private LocalDate fechaVinculacion;

    public Cliente (String nombre) {
        idsClientes += 1;
        this.clienteId = idsClientes;
        this.billeteras = new ArrayList<>();
        this.fechaVinculacion = LocalDate.now();
        this.nombre = nombre;
    }

    public Double getSaldoTotal () {
        Double suma = 0.0;
        for (Billetera billetera : billeteras) {
            suma += billetera.getSaldoTotal();
        }
        return suma;
    }

    public Boolean addBilletera (Billetera nuevaBilletera) {
        for (Billetera billetera : billeteras) {
            if (billetera.getBilleteraId() == nuevaBilletera.getBilleteraId()) {
                //La billetera existe y está registrada
                return false;
            }
        }
        this.billeteras.add(nuevaBilletera);
        return true;
    }

}
