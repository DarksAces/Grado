<?php
$servername = "localhost"; // nombre del servidor
$username = "root"; // nombre de usuario de la base de datos
$password = ""; // contraseña de la base de datos
$dbname = "jmh"; // nombre de la base de datos

$conn = new mysqli($servername, $username, $password, $dbname);

// Comprobar la conexión
if ($conn->connect_error) {
    die("La conexión ha fallado: " . $conn->connect_error);
}

// Obtener los datos del formulario
$usuario = $_POST["usuario"];
$contrasena = $_POST["contrasena"];

// Consulta SQL para comprobar si el usuario y la contraseña son válidos
$sql = "SELECT * FROM usuarios WHERE usuario='$usuario' AND contrasena='$contrasena'";
$resultado = $conn->query($sql);

// Crear el documento XML de respuesta
header('Content-Type: text/xml');
$xml = new SimpleXMLElement('<respuesta/>');

// Comprobar si la consulta devuelve algún resultado
if ($resultado->num_rows > 0) {
    // Usuario y contraseña válidos
    $xml->addChild('estado', 'ok');
} else {
    // Usuario o contraseña incorrectos
    $xml->addChild('estado', 'ko');
}

// Imprimir el documento XML de respuesta
echo $xml->asXML();

// Cerrar la conexión a la base de datos
$conn->close();
?>
