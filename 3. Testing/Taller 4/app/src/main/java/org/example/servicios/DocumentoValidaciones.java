package org.example.servicios;

import java.util.List;

import org.example.modelos.Documento;

public interface DocumentoValidaciones {
    Documento nuevoDocumento(Documento documento);
    Boolean existeDocumento(Long documentoId);
    Documento getDocumento(Long documentoId);
    Boolean eliminarDocumento(Long documentoId);
}
