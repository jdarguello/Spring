package com.ecommerce.ProductosApi.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.ProductosApi.modelos.Articulo;
import com.ecommerce.ProductosApi.modelos.CarritoCompras;
import com.ecommerce.ProductosApi.repositorios.CarritoRepository;

@Service
public class CarritoService {

    private CarritoRepository repository;

    public CarritoService (CarritoRepository repository) {
        this.repository = repository;
    }

    public List<CarritoCompras> obtenerTodosCarritos () {
        return this.repository.findAll();
    }

    public CarritoCompras nuevoCarrito (CarritoCompras carrito) {
        return this.repository.save(carrito);
    }

    public CarritoCompras obtenerCarritoPorId (Long carritoId) {
        return this.repository.findByCarritoId(carritoId);
    }

    //Lógica para añadir un nuevo artículo al carrito.
    // Estamos suponiendo: el carrito YA EXISTE. El frontend lo tiene con su id
    public CarritoCompras añadirArticulo (Long carritoId, Articulo nuevoArticulo) {
        //1. Verificar el contenido del carrito en base de datos
        CarritoCompras carritoVeridico = this.obtenerCarritoPorId(carritoId);
        
        //2. Verificar si el artículo de algún producto ya existe en ese carrito
        if (yaExisteArticulo(carritoVeridico, nuevoArticulo)) {
            actualizarArticuloExistente(carritoVeridico, nuevoArticulo);
        } else {
            //3. Si no existe, añada el artículo
            nuevoArticulo.setCarrito(carritoVeridico);
            carritoVeridico.getArticulos().add(nuevoArticulo);
        }

        //4. Actualizar total y cantidadArticulos
        carritoVeridico.setCantidadArticulos(carritoVeridico.calcularCantidadArticulos());
        carritoVeridico.setTotal(carritoVeridico.calcularTotal());

        //5. Guardar el cambio
        carritoVeridico = this.repository.save(carritoVeridico);

        return carritoVeridico;
    }

    private boolean yaExisteArticulo (CarritoCompras carrito, Articulo nuevArticulo) {
        for (Articulo articuloExistente : carrito.getArticulos()) {
            if (articuloExistente.getProducto().getProductoId() == nuevArticulo.getProducto().getProductoId()) {
                return true;
            }
        }
        return false;
    }

    private Articulo actualizarArticuloExistente (CarritoCompras carrito, Articulo nuevArticulo) {
        //Si existe, súmele las cantidades a las ya existentes
        for (Articulo articuloExistente : carrito.getArticulos()) {
            if (articuloExistente.getProducto().getProductoId() == nuevArticulo.getProducto().getProductoId()) {
                Integer cantidadExistente = articuloExistente.getCantidad();
                articuloExistente.setCantidad(cantidadExistente + nuevArticulo.getCantidad());
                return articuloExistente;
            }
        }
        //Si no existe, devuelva el artículo
        return nuevArticulo;
    }
}
