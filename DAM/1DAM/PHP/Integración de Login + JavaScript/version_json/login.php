<?php
session_start();

// Si ya está logueado, redirigir
if (isset($_SESSION["usuario"])) {
    header("Location: home.php");
    exit;
}

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = $_POST["email"];
    $password = $_POST["password"];
    $archivo = "users.json";

    if (file_exists($archivo)) {
        $usuarios = json_decode(file_get_contents($archivo), true);
        foreach ($usuarios as $usuario) {
            if ($usuario["email"] === $email && $usuario["password"] === $password) {
                $_SESSION["usuario"] = $email;
                $_SESSION["rol"] = "admin";
                $_SESSION["fecha"] = date("Y-m-d H:i:s");
                $_SESSION["ip"] = $_SERVER['REMOTE_ADDR'];
                // Cambiado: redirigir a home.php en lugar de ./AnimehApi/index.php
                header("Location: home.php");
                exit;
            }
        }
    }
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