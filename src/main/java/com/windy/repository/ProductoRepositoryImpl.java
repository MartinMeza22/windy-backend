package com.windy.repository;

import com.windy.config.DatabaseConfig;
import com.windy.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoRepositoryImpl implements ProductoRepository {

    private final Connection connection;

    public ProductoRepositoryImpl(Connection connection){
        this.connection = connection;
    }


    @Override
    public Producto guardar(Producto producto) {
        String sql = """
                INSERT INTO producto(nombre, descripcion, precio, stock, activo, id_categoria)
                VALUES (?, ?, ?, ?, ?, ?);
        """;
        
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){ //Esto le dice a Postgre que me devuelva el ID generado
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setBigDecimal(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.setBoolean(5, producto.isActivo());
            if (producto.getIdCategoria() != null) {
                ps.setInt(6, producto.getIdCategoria());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Integer idGenerado = rs.getInt(1);
                    producto.setIdProducto(idGenerado);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el producto", e);
        }
        return producto;
    }

    @Override
    public Optional<Producto> buscarPorId(Integer id) {

        String sql = "SELECT * FROM producto WHERE id_producto = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id); //

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Producto producto = mapearProducto(rs);
                    return Optional.of(producto);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error buscando producto: " + e.getMessage());
        }

        return Optional.empty();
    }


    @Override
    public List<Producto> listarTodos() {
        String sql = "SELECT * FROM producto";

        List<Producto> productos = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto producto = mapearProducto(rs);
                productos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error listando productos: " + e.getMessage());
        }

        return productos;
    }


    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto", e);
        }

    }


    private Producto mapearProducto(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id_producto"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getBigDecimal("precio"),
                rs.getInt("stock"),
                rs.getBoolean("activo"),
                rs.getInt("id_categoria")
        );
    }
}
