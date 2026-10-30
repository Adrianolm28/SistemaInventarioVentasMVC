package controlador;

import servicio.ProductoServicio;
import dao.ProductoDAO;
import modelo.Producto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {

    private ProductoServicio servicio;

    @Override
    public void init() throws ServletException {

        try {

            servicio = new ProductoServicio(new ProductoDAO());

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al inicializar el módulo de productos.", e
            );
        }
    }

    // CONSULTAR Y MOSTRAR PRODUCTOS
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        String accion = req.getParameter("accion");

        try {

            if ("nuevo".equals(accion)) {

                req.getRequestDispatcher("/formularioProducto.jsp")
                        .forward(req, resp);

            } else if ("editar".equals(accion)) {

                int id = Integer.parseInt(req.getParameter("id"));

                Producto producto = servicio.obtenerProducto(id);

                if (producto == null) {
                    resp.sendError(
                            HttpServletResponse.SC_NOT_FOUND,
                            "Producto no encontrado."
                    );
                    return;
                }

                req.setAttribute("producto", producto);

                req.getRequestDispatcher("/formularioProducto.jsp")
                        .forward(req, resp);

            } else {

                List<Producto> productos = servicio.obtenerProductos();

                req.setAttribute("productos", productos);

                req.getRequestDispatcher("/listaProductos.jsp")
                        .forward(req, resp);
            }

        } catch (NumberFormatException e) {

            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El ID del producto no es válido."
            );

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al consultar los productos.", e
            );
        }
    }

    // REGISTRAR, ACTUALIZAR Y ELIMINAR
    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String accion = req.getParameter("accion");

        try {

            if ("registrar".equals(accion)) {

                String nombre = req.getParameter("nombre");
                double precio = Double.parseDouble(req.getParameter("precio"));
                int stock = Integer.parseInt(req.getParameter("stock"));
                String categoria = req.getParameter("categoria");

                servicio.registrarProducto(
                        nombre, precio, stock, categoria
                );

            } else if ("actualizar".equals(accion)) {

                int id = Integer.parseInt(req.getParameter("id"));

                String nombre = req.getParameter("nombre");
                double precio = Double.parseDouble(req.getParameter("precio"));
                int stock = Integer.parseInt(req.getParameter("stock"));
                String categoria = req.getParameter("categoria");

                servicio.actualizarProducto(
                        id, nombre, precio, stock, categoria
                );

            } else if ("eliminar".equals(accion)) {

                int id = Integer.parseInt(req.getParameter("id"));

                servicio.eliminarProducto(id);

            } else {

                resp.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Acción no reconocida."
                );

                return;
            }

            resp.sendRedirect(
                    req.getContextPath() + "/ProductoServlet"
            );

        } catch (NumberFormatException e) {

            req.setAttribute(
                    "error",
                    "El precio, stock o identificador tienen un formato incorrecto."
            );

            mostrarProductos(req, resp);

        } catch (IllegalArgumentException e) {

            req.setAttribute("error", e.getMessage());

            mostrarProductos(req, resp);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al procesar la operación del producto.", e
            );
        }
    }

    private void mostrarProductos(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        try {

            List<Producto> productos = servicio.obtenerProductos();

            req.setAttribute("productos", productos);

            req.getRequestDispatcher("/listaProductos.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar los productos.", e
            );
        }
    }
}