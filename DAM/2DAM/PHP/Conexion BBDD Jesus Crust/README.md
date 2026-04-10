# ✝️ Jesus Crust: Backend Security & Data sync
# ✝️ Jesus Crust: Seguridad Backend & Sincronización de Datos

## 📋 Project Overview | Resumen del Proyecto

This project contains the **Classic Backend Architecture** for the "Jesus Crust" ecosystem, focused on secure user management and mobile-backend synchronization. The objective was to build a robust, secure PHP backend that serves as the "source of truth" for external clients (Mobile/Web).

Este proyecto contiene la **Arquitectura Backend Clásica** para el ecosistema "Jesus Crust", enfocada en la gestión segura de usuarios y la sincronización móvil-backend. El objetivo fue construir un backend PHP robusto y seguro que sirva como "fuente de la verdad" para clientes externos (Móvil/Web).

---

## 🛠️ Tech Stack | Tecnologías

- **Language | Lenguaje:** PHP
- **Database | Base de Datos:** MySQL (XAMPP/MariaDB)
- **Security | Seguridad:** SHA-256 Hashing, Prepared Statements (SQL Injection Prevention)
- **Data Format | Formato de Datos:** JSON

---

## 🔒 Security Implementation | Implementación de Seguridad

One of the core features of this backend is its focus on **Data Protection**:
*Una de las características principales de este backend es su enfoque en la **Protección de Datos**:*

- **Password Hashing**: Instead of storing plain passwords, I implemented **SHA-256** hashing in the `registra.php` script.
- **SQL Injection Prevention**: All database queries use **Prepared Statements** through the `mysqli` driver.
- **Hacheo de Contraseñas**: En lugar de almacenar contraseñas en plano, implementé el hacheo **SHA-256** en el script `registra.php`.
- **Prevención de Inyección SQL**: Todas las consultas de base de datos usan **Sentencias Preparadas** a través del controlador `mysqli`.

```php
// Critical Security Step: Hashing with SHA-256
$password_hash = hash('sha256', $password);

// Prepared Statement to prevent SQL Injection
$stmt = $conn->prepare("INSERT INTO usuarios (username, email, password_hash) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $username, $email, $password_hash);
```

---

## 📂 File Structure | Estructura de Archivos

- **`Creacion BBDD.txt`**: SQL Script to initialize the `jesuscrust_db` and the `usuarios` table. | *Script SQL para inicializar la BD `jesuscrust_db`.*
- **`registra.php`**: Endpoint for user registration. | *Endpoint para el registro de usuarios.*
- **`validacuenta.php`**: Secure login/validation against hashed storage. | *Login seguro y validación contra el almacenamiento hacheado.*
- **`consultausuarios.php`**: JSON output for user listings. | *Salida JSON para listados de usuarios.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Import the `Creacion BBDD.txt` into your MySQL server. | *Importa `Creacion BBDD.txt` en tu servidor MySQL.*
2.  Deploy the PHP scripts to your web root. | *Despliega los scripts PHP en tu raíz web.*
3.  Ensure your MySQL connection settings in `registra.php` match your environment. | *Asegúrate de que la configuración de conexión en `registra.php` coincida con tu entorno.*

---

> [!NOTE]
> This project was developed as part of the **2nd year of DAM** to master backend-frontend synchronization.
> *Este proyecto fue desarrollado como parte del **2º año de DAM** para dominar la sincronización backend-frontend.*
