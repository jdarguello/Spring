package com.Bancolombia.InversionVirtual.modelos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Cuenta_Bancaria")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuentaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cuentaId;
    private Long numeroCuenta;
    private Double saldo = 0.0;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @OneToOne(mappedBy = "cuenta", cascade = CascadeType.ALL)
    @JoinColumn(name = "documentoId")
    private Documento documento;

    //@OneToMany(mappedBy = "cuentaOrigen", cascade = CascadeType.ALL)
    //@JsonIgnore
    //private List<InversionVirtual> inversionesAbiertas;

    //@OneToMany(mappedBy = "cuentaDestino", cascade = CascadeType.ALL)
    //private List<InversionVirtual> inversionesConsignadas;

}
