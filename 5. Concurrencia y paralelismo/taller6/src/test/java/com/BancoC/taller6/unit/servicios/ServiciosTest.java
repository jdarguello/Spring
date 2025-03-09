package com.BancoC.taller6.unit.servicios;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;

import com.BancoC.taller6.GeneralTest;
import com.BancoC.taller6.repositories.ProductoRepository;
import com.BancoC.taller6.servicios.ProductoService;
import com.BancoC.taller6.servicios.contratos.ProductoOperaciones;

import modelos.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ServiciosTest extends GeneralTest {
    
    protected ProductoRepository productoRepository;
    
    protected ProductoOperaciones productoOperaciones;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        //Mocks
        productoRepository = mock(ProductoRepository.class);

        //Inyección de mocks
        productoOperaciones = new ProductoService(productoRepository);

        //Comportamiento de mocks
        when(productoRepository.save(camisa))
            .thenReturn(Mono.just(camisaBD));

        when(productoRepository.findAll())
            .thenReturn(Flux.fromArray(new Producto[]{
                camisaBD,
                pantalonBD
            }));
        
        when(productoRepository.findById(102L))
            .thenReturn(Mono.just(pantalonBD));

        when(productoRepository.findById(100L))
            .thenReturn(Mono.empty());
        
        when(productoRepository.save(pantalonActualizado))
            .thenReturn(Mono.just(pantalonBD));

        when(productoRepository.deleteById(102L))
            .thenReturn(Mono.empty());
    }

}
