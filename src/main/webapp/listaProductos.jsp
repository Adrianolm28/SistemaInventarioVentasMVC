
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Productos Registrados</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>

<div class="container">

    <h1>Productos Registrados</h1>

    <c:if test="${not empty error}">
        <div class="alerta">
            <c:out value="${error}"/>
        </div>
    </c:if>

    <div class="acciones">
        <a class="btn"
           href="${pageContext.request.contextPath}/ProductoServlet?accion=nuevo">
            Registrar producto
        </a>

        <a class="btn btn-secundario"
           href="${pageContext.request.contextPath}/index.jsp">
            Inicio
        </a>
    </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Stock</th>
            <th>Categoría</th>
            <th>Acciones</th>
        </tr>
        </thead>

        <tbody>

        <c:choose>

            <c:when test="${not empty productos}">

                <c:forEach var="p" items="${productos}">

                    <tr>
                        <td>${p.id}</td>
                        <td><c:out value="${p.nombre}"/></td>
                        <td>S/ ${p.precio}</td>
                        <td>${p.stock}</td>
                        <td><c:out value="${p.categoria}"/></td>

                        <td>
                            <div class="acciones">

                                <a class="btn btn-editar"
                                   href="${pageContext.request.contextPath}/ProductoServlet?accion=editar&id=${p.id}">
                                    Editar
                                </a>

                                <form action="${pageContext.request.contextPath}/ProductoServlet"
                                      method="post"
                                      onsubmit="return confirm('¿Desea eliminar este producto?');">

                                    <input type="hidden" name="accion" value="eliminar">
                                    <input type="hidden" name="id" value="${p.id}">

                                    <button type="submit" class="btn btn-eliminar">
                                        Eliminar
                                    </button>

                                </form>

                            </div>
                        </td>
                    </tr>

                </c:forEach>

            </c:when>

            <c:otherwise>

                <tr>
                    <td colspan="6" class="sin-datos">
                        No hay productos registrados.
                    </td>
                </tr>

            </c:otherwise>

        </c:choose>

        </tbody>
    </table>

</div>

</body>
</html>
