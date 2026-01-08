-- 1. Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS supermarket;
USE supermarket;

-- 2. Crear la tabla 'frutas'
CREATE TABLE IF NOT EXISTS frutas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

-- 3. Insertar datos de ejemplo
-- Es IMPORTANTE que los IDs coincidan con los que espera la App para recortar el Sprite
INSERT INTO frutas (id, nombre, precio) VALUES
(1, 'Manzana', 1.50),
(2, 'Banana', 0.50),
(3, 'Naranja', 0.80),
(4, 'Uva', 2.00),
(5, 'Pera', 1.20),
(6, 'Sandía', 3.50)
ON DUPLICATE KEY UPDATE nombre=nombre; -- Evita error si ya existen
