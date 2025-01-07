package com.Bancolombia.InversionVirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bancolombia.InversionVirtual.modelos.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
