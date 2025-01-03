package org.example.modelos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaBancaria {
    private Long cuentaId;
    private Documento documento;
    private Long numeroCuenta;
    private Double saldo;
    private Date fechaCreacion;
}
