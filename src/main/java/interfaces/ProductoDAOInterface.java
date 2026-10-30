package interfaces;

import modelo.Producto;
import java.sql.SQLException;
import java.util.List;

public interface ProductoDAOInterface {

    void insertarProducto(Producto producto) throws SQLException;

    List<Producto> listarProductos() throws SQLException;

    Producto buscarProducto(int id) throws SQLException;

    boolean actualizarProducto(Producto producto) throws SQLException;

    boolean eliminarProducto(int id) throws SQLException;
}