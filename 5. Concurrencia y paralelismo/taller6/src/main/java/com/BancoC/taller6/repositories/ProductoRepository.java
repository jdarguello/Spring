package com.BancoC.taller6.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import modelos.Producto;

public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {
    
}
