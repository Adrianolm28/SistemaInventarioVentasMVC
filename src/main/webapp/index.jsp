
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sistema de Inventario</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>

<div class="container">
    <h1>Sistema de Inventario</h1>
    <p>Administración y control de productos.</p>

    <div class="acciones">
        <a class="btn" href="${pageContext.request.contextPath}/ProductoServlet?accion=nuevo">
            Registrar producto
        </a>

        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/ProductoServlet">
            Ver productos
        </a>
    </div>
</div>

</body>
</html>
