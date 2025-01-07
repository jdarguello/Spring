package com.Bancolombia.InversionVirtual.modelos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "Documento")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long documentoId;
    private String numeroDocumento;
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clienteId", nullable = false)
    private Cliente cliente;

    @OneToOne
    private CuentaBancaria cuenta;

    @Override
    public String toString() {
        return "";
    }
}
