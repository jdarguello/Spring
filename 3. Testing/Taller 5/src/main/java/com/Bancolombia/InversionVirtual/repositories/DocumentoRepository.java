package com.Bancolombia.InversionVirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bancolombia.InversionVirtual.modelos.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    
}
