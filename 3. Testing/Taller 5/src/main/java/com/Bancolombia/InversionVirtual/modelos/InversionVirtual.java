package com.Bancolombia.InversionVirtual.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Inversion_Virtual")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InversionVirtual {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inversionId;

    @Column(nullable = false)
    private Double valor;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Temporal(TemporalType.DATE)
    private LocalDate tiempoDuracion;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "cuentaOrigenId", referencedColumnName = "cuentaId", nullable = false)
    private CuentaBancaria cuentaOrigen;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "cuentaDestinoId", referencedColumnName = "cuentaId", nullable = false)
    private CuentaBancaria cuentaDestino;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "clienteId")
    private Cliente cliente;
}
