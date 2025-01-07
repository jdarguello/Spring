package com.Bancolombia.InversionVirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bancolombia.InversionVirtual.modelos.CuentaBancaria;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long> {
    
}
