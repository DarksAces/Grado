<?php
// Incluir archivo de conexión
require_once "conexiones.php";

$mensaje = "";

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = $_POST["email"];
    $password = $_POST["password"];
    
    // Conectar a la base de datos
    $conexion = conectar();
    
    // Verificar si el correo ya existe
    $consulta = $conexion->prepare("SELECT * FROM usuarios WHERE email = ? LIMIT 1");
    $consulta->bind_param("s", $email);
    $consulta->execute();
    $resultado = $consulta->get_result();
    
    if ($resultado->num_rows > 0) {
        $mensaje = "<p style='color: red;'>Este correo ya está registrado.</p>";
    } else {
        // Insertar nuevo usuario (en una aplicación real deberías usar password_hash)
        $insertar = $conexion->prepare("INSERT INTO usuarios (email, password) VALUES (?, ?)");
        $insertar->bind_param("ss", $email, $password);
        
        if ($insertar->execute()) {
            $mensaje = "<p style='color: green;'>Usuario registrado correctamente. Ya puedes <a href='login.php'>iniciar sesión</a>.</p>";
        } else {
            $mensaje = "<p style='color: red;'>Error al registrar el usuario: " . $conexion->error . "</p>";
        }
    }
    
    // Cerrar la conexión
    $conexion->close();
}
?>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Registro</title>
</head>

<body>
    <h1>Registro de usuario</h1>
    
    <?php echo $mensaje; ?>
    
    <form method="POST">
        <label>Email:</label>
        <input type="email" name="email" required><br><br>
        <label>Contraseña:</label>
        <input type="password" name="password" required><br><br>
        <button type="submit">Registrarse</button>
    </form>
    
    <p><a href="login.php">¿Ya tienes cuenta? Inicia sesión aquí</a></p>
</body>

</html>