package com.Bancolombia.InversionVirtual.modelos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Cliente")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clienteId;
    private String nombre;
    private Date fechaVinculacion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Documento> documentos = new ArrayList<>();   

    //@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    //private List<CuentaBancaria> cuentas;

    //@OneToMany(mappedBy = "cliente", cascade = CascadeType.DETACH)
    //private List<InversionVirtual> inversiones;
    
}
