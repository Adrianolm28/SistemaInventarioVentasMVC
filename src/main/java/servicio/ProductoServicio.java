package servicio;

import dao.ProductoDAO;
import interfaces.ProductoDAOInterface;
import modelo.Producto;

import java.sql.SQLException;
import java.util.List;

public class ProductoServicio {

    private final ProductoDAOInterface productoDAO;

    public ProductoServicio(ProductoDAOInterface productoDAO) {
        this.productoDAO = productoDAO;
    }

    // REGISTRAR PRODUCTO
    public void registrarProducto(
            String nombre,
            double precio,
            int stock,
            String categoria
    ) throws SQLException {

        validarDatos(nombre, precio, stock, categoria);

        Producto producto = new Producto(
                nombre.trim(),
                precio,
                stock,
                categoria.trim()
        );

        productoDAO.insertarProducto(producto);
    }

    // LISTAR PRODUCTOS
    public List<Producto> obtenerProductos() throws SQLException {

        return productoDAO.listarProductos();
    }

    // BUSCAR PRODUCTO
    public Producto obtenerProducto(int id) throws SQLException {

        validarId(id);

        return productoDAO.buscarProducto(id);
    }

    // ACTUALIZAR PRODUCTO
    public void actualizarProducto(
            int id,
            String nombre,
            double precio,
            int stock,
            String categoria
    ) throws SQLException {

        validarId(id);
        validarDatos(nombre, precio, stock, categoria);

        Producto producto = productoDAO.buscarProducto(id);

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no existe."
            );
        }

        producto.setNombre(nombre.trim());
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria.trim());

        productoDAO.actualizarProducto(producto);
    }

    // ELIMINAR PRODUCTO
    public void eliminarProducto(int id) throws SQLException {

        validarId(id);

        if (!productoDAO.eliminarProducto(id)) {
            throw new IllegalArgumentException(
                    "El producto no existe."
            );
        }
    }

    // VALIDAR DATOS
    private void validarDatos(
            String nombre,
            double precio,
            int stock,
            String categoria
    ) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del producto es obligatorio."
            );
        }

        if (!Double.isFinite(precio) || precio <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero."
            );
        }

        if (stock < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo."
            );
        }

        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La categoría es obligatoria."
            );
        }
    }

    // VALIDAR ID
    private void validarId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID del producto no es válido."
            );
        }
    }
}