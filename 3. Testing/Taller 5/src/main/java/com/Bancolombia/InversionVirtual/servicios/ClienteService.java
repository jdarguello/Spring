package com.Bancolombia.InversionVirtual.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bancolombia.InversionVirtual.modelos.Cliente;
import com.Bancolombia.InversionVirtual.modelos.CuentaBancaria;
import com.Bancolombia.InversionVirtual.modelos.Documento;
import com.Bancolombia.InversionVirtual.modelos.InversionVirtual;
import com.Bancolombia.InversionVirtual.repositories.ClienteRepository;
import com.Bancolombia.InversionVirtual.servicios.Interfaces.ClienteOperaciones;
import com.Bancolombia.InversionVirtual.servicios.Interfaces.DocumentoValidaciones;

@Service
public class ClienteService implements ClienteOperaciones {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private DocumentoValidaciones documentoValidaciones;

    @Override
    public Cliente nuevoCliente(Cliente cliente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nuevoCliente'");
    }

    @Override
    public Cliente getCliente(Long clienteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCliente'");
    }

    @Override
    public Documento registrarNuevoDocumento(Long clienteId, Documento documento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarNuevoDocumento'");
    }

    @Override
    public CuentaBancaria abrirNuevaCuenta(Long clienteId, CuentaBancaria nuevaCuenta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abrirNuevaCuenta'");
    }

    @Override
    public InversionVirtual abrirNuevaInversion(Long clienteId, InversionVirtual nuevaInversion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abrirNuevaInversion'");
    }

    @Override
    public Boolean reclamarInversion(Long clienteId, Long inversionId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reclamarInversion'");
    }

    @Override
    public Boolean eliminarCuenta(Long cuentaId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarCuenta'");
    }
    
}
