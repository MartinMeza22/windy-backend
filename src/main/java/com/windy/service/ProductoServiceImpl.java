package com.windy.service;

import com.windy.model.Producto;
import com.windy.repository.ProductoRepository;
import com.windy.repository.ProductoRepositoryImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Producto crearProducto(Producto producto) {
        validarProducto(producto);
        return productoRepository.guardar(producto);
    }

    @Override
    public Optional<Producto> obtenerPorId(Integer id) {
        validarId(id);
        return productoRepository.buscarPorId(id);
    }


    @Override
    public List<Producto> listarProductos() {
        return productoRepository.listarTodos();
    }

    @Override
    public boolean eliminarProducto(Integer id) {
        validarId(id);
        return productoRepository.eliminar(id);
    }


    private void validarProducto(Producto producto) {
        if(producto.getNombre() == null || producto.getNombre().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if(producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()){
            throw new IllegalArgumentException("La descripcion no puede estar vacia");
        }
        if (producto.getStock() == null){
            throw new IllegalArgumentException("El stock no puede estar vacio");
        }
        if(producto.getStock() < 0){
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if(producto.getPrecio() == null){
            throw new IllegalArgumentException("El precio no puede estar vacio");
        }
        if(producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }

    private void validarId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser null");
        }
    }
}
