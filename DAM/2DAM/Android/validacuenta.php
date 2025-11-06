<?php
// Script: validacuenta.php
// Objetivo: Recibir las credenciales (usuario y contraseña) desde la App Android,
// hashear la contraseña de ENTRADA con SHA-256 y verificarla contra el hash almacenado.

header('Content-Type: application/json'); // Devolver siempre una respuesta JSON

// --- CONFIGURACIÓN DE TU BASE DE DATOS ---
$servername = "localhost";
$username_db = "root"; 
$password_db = "";    // Deja vacío si no configuraste una contraseña en XAMPP
$dbname = "jesuscrust_db"; 
// -----------------------------------------

$conn = new mysqli($servername, $username_db, $password_db, $dbname);

if ($conn->connect_error) {
    // Error de conexión a la BD
    die(json_encode(["status" => "error", "message" => "Error de conexión a la BD: " . $conn->connect_error]));
}

// 1. Recibir datos desde Android (POST request)
$usuario_input = $_POST["username"] ?? ''; 
$contrasena_input = $_POST["password"] ?? '';

if (empty($usuario_input) || empty($contrasena_input)) {
    echo json_encode(["status" => "error", "message" => "Por favor, ingresa usuario y contraseña."]);
    $conn->close();
    exit();
}

// 2. 🚨 PASO CRÍTICO: HASHEAR la contraseña de ENTRADA con SHA-256
// ESTE HASH DEBE COINCIDIR CON EL ALMACENADO EN LA BD.
$hashed_password_input = hash('sha256', $contrasena_input);

// Usamos consultas preparadas para sanear el usuario y prevenir inyecciones SQL.

// 3. Consulta SQL: Buscar por nombre de usuario Y el hash de la contraseña.
// ⚠️ NOTA: Usamos la columna 'password_hash' para la contraseña cifrada.
$stmt = $conn->prepare("SELECT id FROM usuarios WHERE username = ? AND password_hash = ?");

// "ss" indica que son dos strings (usuario y hash)
$stmt->bind_param("ss", $usuario_input, $hashed_password_input); 

$stmt->execute();
$resultado = $stmt->get_result();

if ($resultado === false) {
    // Manejo de errores de ejecución de consulta
    echo json_encode(["status" => "error", "message" => "Error en la consulta SQL: " . $stmt->error]);
} elseif ($resultado->num_rows > 0) {
    // Éxito: El hash generado coincide con el hash almacenado
    echo json_encode(["status" => "success", "message" => "¡Bienvenido! Credenciales correctas."]);
} else {
    // Error: No se encontró la combinación usuario/contraseña
    echo json_encode(["status" => "error", "message" => "Usuario o Contraseña incorrectos."]);
}

$stmt->close();
$conn->close();
?>