<?php
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = $_POST["email"];
    $password = $_POST["password"];

    $archivo = "users.json";

    if (file_exists($archivo)) {
        $usuarios = json_decode(file_get_contents($archivo), true);
    } else {
        $usuarios = [];
    }

    foreach ($usuarios as $usuario) {
        if ($usuario["email"] === $email) {
            echo "<p>Este correo ya está registrado.</p>";
            exit;
        }
    }

    $nuevoUsuario = ["email" => $email, "password" => $password];
    $usuarios[] = $nuevoUsuario;
    file_put_contents($archivo, json_encode($usuarios, JSON_PRETTY_PRINT));
    echo "<p>Usuario registrado correctamente. Ya puedes iniciar sesión.</p>";
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
    <form method="POST">
        <label>Email:</label>
        <input type="email" name="email" required><br><br>
        <label>Contraseña:</label>
        <input type="password" name="password" required><br><br>
        <button type="submit">Registrarse</button>
    </form>
</body>

</html>