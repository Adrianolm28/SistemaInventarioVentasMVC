
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Formulario de Producto</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>

<div class="container">
    <h1>
        <c:choose>
            <c:when test="${not empty producto}">
                Editar producto
            </c:when>
            <c:otherwise>
                Registrar producto
            </c:otherwise>
        </c:choose>
    </h1>

    <form action="${pageContext.request.contextPath}/ProductoServlet" method="post">

        <c:choose>
            <c:when test="${not empty producto}">
                <input type="hidden" name="accion" value="actualizar">
                <input type="hidden" name="id" value="${producto.id}">
            </c:when>
            <c:otherwise>
                <input type="hidden" name="accion" value="registrar">
            </c:otherwise>
        </c:choose>

        <label>Nombre:</label>
        <input type="text" name="nombre"
               value="<c:out value='${producto.nombre}'/>"
               maxlength="120" required>

        <label>Precio (S/):</label>
        <input type="number" name="precio"
               value="${producto.precio}"
               step="0.01" min="0.01" required>

        <label>Stock:</label>
        <input type="number" name="stock"
               value="${producto.stock}"
               min="0" step="1" required>

        <label>Categoría:</label>
        <input type="text" name="categoria"
               value="<c:out value='${producto.categoria}'/>"
               maxlength="100" required>

        <div class="acciones">
            <button type="submit" class="btn">
                <c:choose>
                    <c:when test="${not empty producto}">
                        Actualizar producto
                    </c:when>
                    <c:otherwise>
                        Registrar producto
                    </c:otherwise>
                </c:choose>
            </button>

            <a class="btn btn-secundario"
               href="${pageContext.request.contextPath}/ProductoServlet">
                Cancelar
            </a>
        </div>

    </form>
</div>

</body>
</html>
