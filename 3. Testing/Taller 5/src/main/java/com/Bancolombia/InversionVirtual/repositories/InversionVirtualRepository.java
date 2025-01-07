package com.Bancolombia.InversionVirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bancolombia.InversionVirtual.modelos.InversionVirtual;

public interface InversionVirtualRepository extends JpaRepository<InversionVirtual, Long> {
    
}
