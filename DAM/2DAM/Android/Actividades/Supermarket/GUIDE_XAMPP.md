# Guía de Configuración XAMPP para Supermarket

Para que la aplicación funcione con XAMPP, necesitas crear la base de datos y el script PHP en tu servidor local (carpeta `htdocs`).

## 1. Base de Datos (MySQL/MariaDB)

Abre **phpMyAdmin** (http://localhost/phpmyadmin) y ejecuta el siguiente script SQL en la pestaña "SQL":

```sql
-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS supermarket;
USE supermarket;

-- Crear la tabla 'frutas'
CREATE TABLE IF NOT EXISTS frutas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

-- Insertar datos de ejemplo (Deben coincidir con los sprites de la App)
-- ID 1: Manzana, 2: Banana, 3: Naranja, 4: Uva, 5: Pera, 6: Sandía
INSERT INTO frutas (id, nombre, precio) VALUES
(1, 'Manzana', 1.50),
(2, 'Banana', 0.50),
(3, 'Naranja', 0.80),
(4, 'Uva', 2.00),
(5, 'Pera', 1.20),
(6, 'Sandía', 3.50);
```

## 2. Script PHP (Backend)

Crea una carpeta llamada `supermarket` dentro de tu carpeta `htdocs` de XAMPP (normalmente `C:\xampp\htdocs\supermarket`).

Dentro, crea un archivo llamado `get_frutas.php` con el siguiente contenido:

```php
<?php
// get_frutas.php
header('Content-Type: application/json');

// Credenciales
$host = "localhost";
$user = "root";
$pass = ""; // Por defecto en XAMPP está vacío
$db   = "supermarket";

// Conexión
$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    die(json_encode(["error" => "Conexión fallida: " . $conn->connect_error]));
}

// Consulta
$sql = "SELECT id, nombre, precio FROM frutas";
$result = $conn->query($sql);

$frutas = array();

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        // Convertir tipos si es necesario
        $row['id'] = (int)$row['id'];
        $row['precio'] = (float)$row['precio'];
        $frutas[] = $row;
    }
}

// Devolver JSON
echo json_encode($frutas);

$conn->close();
?>
```

## 3. Probar conexión

1. Inicia Apache y MySQL en XAMPP.
2. Abre tu navegador y ve a: `http://localhost/supermarket/get_frutas.php`
3. Deberías ver el JSON con las frutas.

## 4. Configurar App Android

La app ya está configurada para conectar a `http://10.0.2.2/supermarket/get_frutas.php`.
- `10.0.2.2` es la IP especial que usa el **Emulador de Android** para acceder al localhost de tu PC.
- Si usas un **móvil real**, debes cambiar esa IP en `MainActivity.java` por la IP local de tu PC (ej. `192.168.1.35`) y asegurarte de que ambos están en la misma red WiFi.
