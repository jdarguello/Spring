package org.example.modelos;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    private Long clienteId;
    private String nombre;
    private List<Documento> documentos;
    private Date fechaVinculacion;
}
