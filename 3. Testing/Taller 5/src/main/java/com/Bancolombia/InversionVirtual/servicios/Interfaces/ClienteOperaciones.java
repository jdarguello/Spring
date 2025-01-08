package com.Bancolombia.InversionVirtual.servicios.Interfaces;

import com.Bancolombia.InversionVirtual.modelos.Cliente;
import com.Bancolombia.InversionVirtual.modelos.CuentaBancaria;
import com.Bancolombia.InversionVirtual.modelos.Documento;
import com.Bancolombia.InversionVirtual.modelos.InversionVirtual;

public interface ClienteOperaciones {
    Cliente nuevoCliente (Cliente cliente);
    Cliente getCliente (Long clienteId);
    Documento registrarNuevoDocumento (Long clienteId, Documento documento);
    CuentaBancaria abrirNuevaCuenta (Long clienteId, CuentaBancaria nuevaCuenta);
    InversionVirtual abrirNuevaInversion (Long clienteId, InversionVirtual nuevaInversion);
    Boolean reclamarInversion (Long clienteId, Long inversionId);
    Boolean eliminarCuenta (Long cuentaId);
}
