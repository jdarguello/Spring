package org.example.Nequi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Bolsillo {
    public static Long contadorIds = 0L;
    private Long bolsilloId;
    private Double saldo;

    public Bolsillo() {
        contadorIds += 1L;
        this.bolsilloId = contadorIds;
        this.saldo = 0.0;
    }

    public Bolsillo (Double saldo) {
        this();     //Inicializa constructor vacío
    }

    public Bolsillo (Long contador, Double saldo) {
        this();     //Inicializa constructor vacío
    }

    public void consignacion (Double valor) {
        this.saldo += valor;
    }

    public void retirar (Double valor) throws Exception {
        if (this.saldo < valor) {
            throw new Exception("500: el valor a retirar es mayor que el saldo disponible");
        }

        this.saldo -= valor;
    }
}
