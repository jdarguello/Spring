package org.example.servicios;

import java.util.ArrayList;
import java.util.List;

import org.example.modelos.Documento;
import org.example.servicios.DocumentoValidaciones;

public class DocumentoService implements DocumentoValidaciones {

    private List<Documento> documentos;

    public DocumentoService() {
        documentos = new ArrayList<>();
    }

    @Override
    public Documento nuevoDocumento(Documento documento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nuevoDocumento'");
    }

    @Override
    public Boolean existeDocumento(Long documentoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existeDocumento'");
    }

    @Override
    public Documento getDocumento(Long documentoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDocumento'");
    }

    @Override
    public Boolean eliminarDocumento(Long documentoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarDocumento'");
    }
    
}
