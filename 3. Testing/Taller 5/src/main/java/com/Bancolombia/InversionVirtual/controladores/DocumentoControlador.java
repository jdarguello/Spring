package com.Bancolombia.InversionVirtual.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Bancolombia.InversionVirtual.modelos.Documento;
import com.Bancolombia.InversionVirtual.servicios.Interfaces.ClienteOperaciones;
import com.Bancolombia.InversionVirtual.servicios.Interfaces.DocumentoOperaciones;
import com.Bancolombia.InversionVirtual.servicios.Interfaces.DocumentoValidaciones;

@RestController
@RequestMapping("/api/docs")
public class DocumentoControlador {
    
    private ClienteOperaciones operaciones;

    public DocumentoControlador(ClienteOperaciones operaciones) {
        this.operaciones = operaciones;
    }

    @PostMapping
    public ResponseEntity<Documento> nuevoDocumento(@RequestParam(name = "clienteId") Long clienteId, 
        @RequestBody Documento documento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nuevDocumento'");
    }

    @GetMapping
    public Documento getDocumento(@RequestParam(name = "clienteId") Long clienteId, 
        @RequestParam(name = "documentoId") Long documentoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDocumento'");
    }

    @DeleteMapping
    public Boolean eliminarDocumento(@RequestParam(name = "clienteId") Long clienteId, 
        @RequestParam(name = "documentoId") Long documentoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarDocumento'");
    }

}
