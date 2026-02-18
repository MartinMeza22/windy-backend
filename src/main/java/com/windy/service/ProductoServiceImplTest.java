package com.windy.service;

import com.windy.model.Producto;
import com.windy.repository.ProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productoService = new ProductoServiceImpl(productoRepository);
    }

    @Test
    void seDebePoderCrearElProductoCorrectamente(){
        //Arrange -> Preparar Datos
        Producto producto = new Producto();
        producto.setNombre("Bermuda rustica");
        producto.setDescripcion("Algodón 98% Elastano 2%");
        producto.setPrecio(new BigDecimal("45000"));
        producto.setStock(10);
        when(productoRepository.guardar(producto)).thenReturn(producto);

        //Act -> Ejecutar
        Producto resultado = productoService.crearProducto(producto);

        //Assert -> Corroborar que devuelva lo esperado
        assertNotNull(resultado);
        assertEquals("Bermuda rustica", resultado.getNombre());
    }

    @Test
    void seDebePoderBuscarPorId() {
        // Arrange
        Producto producto = new Producto();
        producto.setIdProducto(1);
        producto.setNombre("Bermuda rustica");

        when(productoRepository.buscarPorId(1))
                .thenReturn(Optional.of(producto));

        // Act
        Optional<Producto> resultado = productoService.obtenerPorId(1);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getIdProducto());

        verify(productoRepository).buscarPorId(1);
    }


    @Test
    void noSeDebePoderCrearElProductoSiElNombreEsNull(){
        Producto producto = new Producto();
        producto.setNombre(null);
        producto.setDescripcion("Desc");
        producto.setStock(10);
        producto.setPrecio(new BigDecimal("10"));

        assertThrows(IllegalArgumentException.class,() -> productoService.crearProducto(producto));

        verify(productoRepository, never()).guardar(any());
    }

    @Test
    void debeRetornarProductoSiIdExiste() {
        Integer id = 1;

        Producto producto = new Producto();
        producto.setIdProducto(id);
        producto.setNombre("Bermuda Lisa");
        producto.setDescripcion("100% Algodón");
        producto.setStock(10);
        producto.setPrecio(new BigDecimal("30000"));

        when(productoRepository.buscarPorId(id)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoService.obtenerPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getIdProducto());

        verify(productoRepository).buscarPorId(id);
    }

    @Test
    void debeRetornarOptionalVacioSiIdNoExiste(){
        Integer id = 1;
        // Optional<Producto> buscarPorId(Integer id);
        Producto producto = new Producto();
        producto.setNombre("Bermuda Lisa");
        producto.setDescripcion("100% Algodón");
        producto.setStock(10);
        producto.setPrecio(new BigDecimal("30000"));

        when(productoRepository.buscarPorId(1)).thenReturn(Optional.empty());

        Optional<Producto> resultado = productoService.obtenerPorId(id);

        assertTrue(resultado.isEmpty());
        verify(productoRepository).buscarPorId(id);
    }

}
