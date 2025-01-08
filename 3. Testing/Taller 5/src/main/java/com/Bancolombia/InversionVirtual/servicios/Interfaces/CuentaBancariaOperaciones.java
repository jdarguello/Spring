package com.Bancolombia.InversionVirtual.servicios.Interfaces;

import com.Bancolombia.InversionVirtual.modelos.CuentaBancaria;

public interface CuentaBancariaOperaciones {
    CuentaBancaria nuevaCuenta(CuentaBancaria cuenta);
    CuentaBancaria transaccion(Double monto, Long cuentaId);
    Boolean eliminarCuenta (Long cuentaId);
}
