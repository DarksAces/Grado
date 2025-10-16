<?php
session_start();

// Si ya está logueado, redirigir
if (isset($_SESSION["usuario"])) {
    header("Location: index.php");
    exit;
}

// Incluir archivo de conexión
require_once "conexiones.php";

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = $_POST["email"];
    $password = $_POST["password"];
    
    // Conectar a la base de datos
    $conexion = conectar();
    
    // Preparar consulta SQL (usando prepared statements para seguridad)
    $consulta = $conexion->prepare("SELECT * FROM usuarios WHERE email = ? LIMIT 1");
    $consulta->bind_param("s", $email);
    $consulta->execute();
    $resultado = $consulta->get_result();
    
    if ($resultado->num_rows > 0) {
        $usuario = $resultado->fetch_assoc();
        // Verificar contraseña (en una aplicación real deberías usar password_hash/password_verify)
        if ($usuario["password"] === $password) {
            $_SESSION["usuario"] = $email;
            $_SESSION["rol"] = "admin";
            $_SESSION["fecha"] = date("Y-m-d H:i:s");
            $_SESSION["ip"] = $_SERVER['REMOTE_ADDR'];
            // Cerrar la conexión
            $conexion->close();
            // Redirigir a index.php
            header("Location: index.php");
            exit;
        }
    }
    
    // Cerrar la conexión
    $conexion->close();
    $error = "Email o contraseña incorrectos.";
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h1 style="text-align: center; color: #1a1a8c;">Inicio de sesión</h1>
    <?php if (isset($error)): ?>
        <p class="error"><?php echo $error; ?></p>
    <?php endif; ?>
    <form method="POST" action="login.php">
        <label>Email:</label>
        <input type="email" name="email" required>
        
        <label>Contraseña:</label>
        <input type="password" name="password" required>
        
        <button type="submit">Entrar</button>
    </form>
    <div class="link">
        <p><a href="register.php">¿No tienes cuenta? Regístrate aquí</a></p>
    </div>
</body>
</html>