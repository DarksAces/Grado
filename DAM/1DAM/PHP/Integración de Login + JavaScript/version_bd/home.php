<?php
session_start();
if (!isset($_SESSION["usuario"])) {
    header("Location: login.php");
    exit;
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Home - Panel de Control</title>
</head>
<body>
    <div class="container">
        <h1>Bienvenido/a, <?php echo htmlspecialchars($_SESSION["usuario"]); ?></h1>
        <p style="text-align: center;">Estás en la zona privada del sistema.</p>
        
        <div class="user-info">
            <p><strong>Usuario:</strong> <?php echo htmlspecialchars($_SESSION["usuario"]); ?></p>
            <p><strong>Rol:</strong> <?php echo htmlspecialchars($_SESSION["rol"]); ?></p>
            <p><strong>Fecha de acceso:</strong> <?php echo htmlspecialchars($_SESSION["fecha"]); ?></p>
            <p><strong>IP:</strong> <?php echo htmlspecialchars($_SESSION["ip"]); ?></p>
        </div>

        <div class="nav-links">
            <a href="./AnimeApi/index.php">Ir al Buscador de Anime</a>
            <a href="dashboard.php">Gestionar Usuarios</a>
        </div>

        <div class="logout">
            <p><a href="logout.php">Cerrar sesión</a></p>
        </div>
    </div>
</body>
</html>