package org.example.modelos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documento {
    private Long documentoId;
    private Cliente cliente;
    private String numeroDoc;
    private String tipo;
}