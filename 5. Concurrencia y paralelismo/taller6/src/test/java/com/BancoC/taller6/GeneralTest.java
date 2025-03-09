package com.BancoC.taller6;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.BeanUtils;

import modelos.Producto;

public class GeneralTest {

    protected Producto camisa;
    protected Producto pantalon;

    protected Producto camisaBD;
    protected Producto pantalonBD;

    protected Producto pantalonActualizado;

    @BeforeEach
    public void setUp() {
        //Objetos "request"
        camisa = Producto.builder()
            .nombre("Camisa cuadros")
            .precio(100_000.0)
            .descripcion("Hermosa camisa de cuadros. ¡Compra ya!")
            .inventario(15)
            .build();

        pantalon = Producto.builder()
            .nombre("Pantalón cuero")
            .precio(1_000_000.0)
            .descripcion("Hermoso pantalón de edición limitada. ¡Compra ya!")
            .inventario(2)
            .build();

        //Objetos "resposne"
        camisaBD = guardarProductoBD(101L, camisa);
        pantalonBD = guardarProductoBD(102L, pantalon);

        pantalonActualizado = new Producto();
        BeanUtils.copyProperties(pantalonBD, pantalonActualizado);
        pantalonActualizado.setInventario(10);
        pantalonActualizado.setPrecio(500_000.0);


    }

    private Producto guardarProductoBD(Long productoId, Producto producto) {
        Producto productoCopia = new Producto();
        BeanUtils.copyProperties(producto, productoCopia);
        productoCopia.setProductoId(productoId);
        return productoCopia;
    }
    
}
