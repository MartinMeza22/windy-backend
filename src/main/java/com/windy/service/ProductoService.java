package com.windy.service;

import com.windy.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Producto crearProducto(Producto producto);
    Optional<Producto> obtenerPorId(Integer id);
    List<Producto> listarProductos();
    boolean eliminarProducto(Integer id);

}
