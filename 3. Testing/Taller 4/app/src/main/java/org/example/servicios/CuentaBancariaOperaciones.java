package org.example.servicios;

import org.example.modelos.CuentaBancaria;

public interface CuentaBancariaOperaciones {
    CuentaBancaria nuevaCuenta(CuentaBancaria cuenta);
    CuentaBancaria transaccion(Double monto, Long cuentaId);
    Boolean eliminarCuenta(Long cuentaId);
}
