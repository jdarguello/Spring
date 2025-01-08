package com.Bancolombia.InversionVirtual.servicios.Interfaces;

import com.Bancolombia.InversionVirtual.modelos.Documento;

public interface DocumentoOperaciones {
    Documento nuevDocumento(Documento documento);
    Documento getDocumento(Long documentoId);
    Boolean eliminarDocumento(Long documentoId);
}
