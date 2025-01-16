package com.Bancolombia.InversionVirtual.controladores;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@RestController
@RequestMapping("/api/docs")
public class DocumentoControlador {
    
    private ClienteOperaciones operaciones;

    public DocumentoControlador(ClienteOperaciones operaciones) {
        this.operaciones = operaciones;
    }

    @PostMapping
    public ResponseEntity<?> nuevoDocumento(@RequestParam(name = "clienteId") Long clienteId, 
                                            @RequestBody Documento documento) throws Exception {

        Documento documentoRegistrado = new Documento();
        try {
            documentoRegistrado = this.operaciones.registrarNuevoDocumento(clienteId, documento);
        } catch (SQLException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                errorMessage(400, "Bad Request", 
                    exception.getMessage(), "/api/docs")
            );
        } catch (InstantiationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                errorMessage(400, "Bad Request", 
                    exception.getMessage(), "/api/docs")
            );
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
            documentoRegistrado
        );
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

    private Map errorMessage(Integer status, String error, String message, String path) {
        return Map.of(
            "timestamp", LocalDateTime.now(),
            "status", status,
            "error", error,
            "message", message,
            "path", path
        );
    }
}
