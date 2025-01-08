package com.Bancolombia.InversionVirtual.servicios.Interfaces;

import com.Bancolombia.InversionVirtual.modelos.Documento;

public interface DocumentoValidaciones extends DocumentoOperaciones{
    Boolean existeDocumento(Long documentoId);
}
