-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS inventario;

-- Usar la base de datos
USE inventario;

-- Eliminar la tabla si existe
DROP TABLE IF EXISTS producto;

-- Crear la tabla producto
CREATE TABLE producto (
    id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL
);

-- Insertar algunos datos de ejemplo
INSERT INTO producto (id, nombre, precio) VALUES 
(100, 'Laptop', 799.99),
(101, 'Monitor', 199.99),
(102, 'Teclado', 49.99),
(103, 'Ratón', 19.99),
(104, 'Impresora', 299.99),
(105, 'Disco Duro', 89.99),
(106, 'Memoria USB', 15.99),
(107, 'Auriculares', 59.99),
(108, 'Webcam', 39.99),
(109, 'Altavoces', 69.99);
