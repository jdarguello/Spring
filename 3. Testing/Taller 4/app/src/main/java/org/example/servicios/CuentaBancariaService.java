package org.example.servicios;

import org.example.modelos.CuentaBancaria;

public class CuentaBancariaService implements CuentaBancariaOperaciones {

    private DocumentoValidaciones documentoValidaciones;

    public CuentaBancariaService (DocumentoValidaciones documentoValidaciones) {
        this.documentoValidaciones = documentoValidaciones;
    }

    @Override
    public CuentaBancaria nuevaCuenta(CuentaBancaria cuenta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nuevaCuenta'");
    }

    @Override
    public CuentaBancaria transaccion(Double monto, Long cuentaId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transaccion'");
    }

    @Override
    public Boolean eliminarCuenta(Long cuentaId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarCuenta'");
    }
    
}
