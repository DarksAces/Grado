CREATE DATABASE InformeProDB;
GO
USE InformeProDB;
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Productos')
BEGIN
    CREATE TABLE Productos (
        Id INT PRIMARY KEY IDENTITY,
        Nombre NVARCHAR(50),
        Precio DECIMAL(10,2),
        Categoria NVARCHAR(50),
        Stock INT
    );
END

-- Clear existing data (optional, for clean start)
-- TRUNCATE TABLE Productos;

INSERT INTO Productos (Nombre, Precio, Categoria, Stock) VALUES
('Laptop Pro 15', 1200.00, 'Electrónica', 10),
('Smartphone X', 850.50, 'Electrónica', 25),
('Monitor 4K', 350.00, 'Electrónica', 15),
('Teclado Mecánico', 80.00, 'Electrónica', 50),
('Ratón Gaming', 45.00, 'Electrónica', 40),
('Silla Ergonómica', 250.00, 'Muebles', 12),
('Escritorio Madera', 180.00, 'Muebles', 8),
('Estantería Metálica', 120.00, 'Muebles', 20),
('Lámpara de Pie', 60.00, 'Muebles', 30),
('Sofá 2 Plazas', 450.00, 'Muebles', 5),
('Cuaderno A4', 5.50, 'Papelería', 100),
('Bolígrafo Azul (Pack 10)', 12.00, 'Papelería', 200),
('Calculadora Científica', 25.00, 'Papelería', 45),
('Grapadora Profesional', 15.00, 'Papelería', 30),
('Papel Impresora 500h', 7.50, 'Papelería', 80);
