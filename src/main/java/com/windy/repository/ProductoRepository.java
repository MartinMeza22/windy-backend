package com.windy.repository;

import com.windy.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(Integer id);

    List<Producto> listarTodos();

    boolean eliminar(Integer id);
}
