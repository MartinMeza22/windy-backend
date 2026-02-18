package com.windy.model;

import java.math.BigDecimal;

public class Producto {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private boolean activo;
    private Integer idCategoria;

    public Producto(){
    }

    public Producto(Integer idProducto, String nombre, String descripcion, BigDecimal precio, Integer stock, boolean activo, Integer idCategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.activo = activo;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public BigDecimal getPrecio() {
        return precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
/*create table producto(
	id_producto serial primary key,
	nombre varchar(80) not null,
	descripcion text,
	precio numeric(10,2) not null,
	stock integer default 0,
	fecha_creacion timestamp default now(),
	id_categoria integer,
	activo boolean default true,
	foreign key (id_categoria) references categoria(id_categoria)
);
*/