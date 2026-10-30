
package servicio;

import dao.ProductoDAO;
import interfaces.ProductoDAOInterface;
import modelo.Producto;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class ProductoServicio {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductoServicio.class);

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

        logger.info(
                "Producto registrado: ID={}, Nombre={}",
                producto.getId(),
                producto.getNombre()
        );
    }

    // LISTAR PRODUCTOS
    public List<Producto> obtenerProductos() throws SQLException {

        List<Producto> productos = productoDAO.listarProductos();

        logger.info(
                "Consulta de productos realizada. Registros encontrados: {}",
                productos.size()
        );

        return productos;
    }

    // BUSCAR PRODUCTO
    public Producto obtenerProducto(int id) throws SQLException {

        validarId(id);

        Producto producto = productoDAO.buscarProducto(id);

        if (producto == null) {
            logger.warn("Producto no encontrado: ID={}", id);
        }

        return producto;
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

        logger.info(
                "Producto actualizado: ID={}, Nombre={}",
                producto.getId(),
                producto.getNombre()
        );
    }

    // ELIMINAR PRODUCTO
    public void eliminarProducto(int id) throws SQLException {

        validarId(id);

        if (!productoDAO.eliminarProducto(id)) {

            logger.warn("No se encontró el producto para eliminar: ID={}", id);

            throw new IllegalArgumentException(
                    "El producto no existe."
            );
        }

        logger.info("Producto eliminado: ID={}", id);
    }

    // VALIDAR DATOS
    private void validarDatos(
            String nombre,
            double precio,
            int stock,
            String categoria
    ) {

        if (StringUtils.isBlank(nombre)) {
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

        if (StringUtils.isBlank(categoria)) {
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
