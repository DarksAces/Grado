<?php
// Script: registra.php
// Objetivo: Recibir datos de registro desde la App Android, hashear la contraseña
// con SHA-256 e insertarlos en la base de datos 'jesuscrust_db'.

header('Content-Type: application/json'); // Devolver siempre una respuesta JSON

// --- CONFIGURACIÓN DE TU BASE DE DATOS ---
// Asegúrate de que estos datos coincidan con tu configuración de XAMPP.
$servername = "localhost";
$username_db = "root"; 
$password_db = "";    // Deja vacío si no configuraste una contraseña en XAMPP
$dbname = "jesuscrust_db"; 
// -----------------------------------------

$conn = new mysqli($servername, $username_db, $password_db, $dbname);

if ($conn->connect_error) {
    // Error si no se puede conectar a la base de datos
    die(json_encode(["status" => "error", "message" => "Error de conexión a la BD: " . $conn->connect_error]));
}

// 1. Recibir datos desde Android (POST request)
$username = $_POST["username"] ?? ''; 
$email = $_POST["email"] ?? '';
$password = $_POST["password"] ?? '';

if (empty($username) || empty($email) || empty($password)) {
    echo json_encode(["status" => "error", "message" => "Por favor, completa todos los campos."]);
    $conn->close();
    exit();
}

// 2. 🚨 PASO CRÍTICO: HASHEAR la contraseña con SHA-256
$password_hash = hash('sha256', $password);

// 3. Preparar e insertar consulta (usando consultas preparadas para mayor seguridad)
// ⚠️ NOTA: Asegúrate de que tu tabla se llama 'usuarios' y las columnas son 'username', 'email' y 'password_hash'.
$stmt = $conn->prepare("INSERT INTO usuarios (username, email, password_hash) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $username, $email, $password_hash); // "sss" indica que son tres strings

if ($stmt->execute()) {
    // Éxito en la inserción
    echo json_encode(["status" => "success", "message" => "Usuario registrado con éxito."]);
} else {
    // Manejo de errores de inserción (ej. usuario o email ya existen por restricción UNIQUE)
    $error_message = "Error al registrar el usuario.";
    // Si es un error de duplicidad (código 1062 en MySQL), damos un mensaje más específico.
    if ($conn->errno == 1062) {
        $error_message = "El nombre de usuario o correo electrónico ya están registrados.";
    }
    echo json_encode(["status" => "error", "message" => $error_message . " Debug: " . $stmt->error]);
}

$stmt->close();
$conn->close();
?>