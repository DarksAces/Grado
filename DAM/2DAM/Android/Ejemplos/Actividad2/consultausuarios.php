<?php
$host = "localhost";
$username = "root"; // Usuario predeterminado de XAMPP
$password = "";     // Contraseña predeterminada de XAMPP (vacia)
$dbname = "jmh"; // Cambia este nombre por el nombre de tu base de datos

// Conexión a la base de datos
$conn = new mysqli($host, $username, $password, $dbname);

// Verifica la conexión
if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}

// Consulta para obtener todos los usuarios
$sql = "SELECT usuario, contrasena FROM usuarios";
$result = $conn->query($sql);

header("Content-type: text/xml");
echo '<?xml version="1.0" encoding="UTF-8"?>';
echo '<usuarios>';

// Verifica si hay resultados y los muestra
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        echo '<usuario>';
        echo '<nombre>' . $row["usuario"] . '</nombre>';
        echo '<contrasena>' . $row["contrasena"] . '</contrasena>';
        echo '</usuario>';
    }
} else {
    echo "0 resultados";
}

echo '</usuarios>';

$conn->close();
?>
