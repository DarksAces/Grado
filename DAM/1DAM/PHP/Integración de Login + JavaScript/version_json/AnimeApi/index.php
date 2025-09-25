<?php
session_start();
// Verificar si el usuario está logueado
if (!isset($_SESSION["usuario"])) {
    // Si no está logueado, redirigir al login que está en el directorio padre
    header("Location: ../login.php");
    exit;
}
?>
<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8">
  <title>Buscador de Anime</title>
  <link rel="stylesheet" href="./CSS/style.css">
</head>

<body>
  <header>
    <nav>
      <ul class="nav">
        <li><a href="index.php">Inicio</a></li>
        <li><a href="./HTML/favoritos.html">Favoritos</a></li>
        <li><a href="./HTML/contacto.html">Contacto</a></li>
        <li><a href="../home.php">Panel de Control</a></li>
        <li><a href="../logout.php">Cerrar Sesión</a></li>
      </ul>
    </nav>
  </header>
  
  <div style="background: #f8f9fa; padding: 10px; text-align: center; margin-bottom: 20px; border-radius: 5px;">
    <span>Bienvenido/a, <strong><?php echo htmlspecialchars($_SESSION["usuario"]); ?></strong></span>
  </div>
  
  <h1>Busca tu Anime</h1>
  <form id="formulario">
    <input type="text" id="busqueda" placeholder="Nombre del anime...">
    <button type="submit">Buscar</button>
  </form>
  <div id="resultado"></div>

  <script src="./JS/script.js"></script>
</body>

</html>