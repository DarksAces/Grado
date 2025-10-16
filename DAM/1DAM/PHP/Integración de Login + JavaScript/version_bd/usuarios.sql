-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS login_db;

-- Usar la base de datos
USE login_db;

-- Crear la tabla de usuarios con todos los campos necesarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) DEFAULT NULL,
    apellido VARCHAR(100) DEFAULT NULL,
    activo TINYINT(1) DEFAULT 1,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultima_conexion TIMESTAMP NULL DEFAULT NULL,
    intentos_fallidos INT DEFAULT 0,
    bloqueado_hasta TIMESTAMP NULL DEFAULT NULL
);

-- Crear tabla de logs de actividad
CREATE TABLE IF NOT EXISTS logs_actividad (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    accion VARCHAR(100) NOT NULL,
    descripcion TEXT,
    ip_address VARCHAR(45),
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Insertar usuarios de ejemplo (los mismos que estaban en el archivo JSON)
INSERT INTO usuarios (email, password, activo) VALUES 
('test@test.test', 'test', 1),
('danielgarbru@campus.monlau.com', '123', 1)
ON DUPLICATE KEY UPDATE email = email;