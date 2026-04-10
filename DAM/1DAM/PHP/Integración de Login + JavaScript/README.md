# 🎌 AnimeVault: Login Integration & API Management
# 🎌 AnimeVault: Integración de Login y Gestión de API

## 📋 Description | Descripción

A dual-version web application developed as part of **1DAM**. This project demonstrates the integration of a secure **PHP-based Login System** with an interactive frontend that consumes anime-related data. I've implemented two storage paradigms: **Relational (MySQL)** and **NoSQL (Static JSON)**, to showcase versatile data management skills.

Una aplicación web de doble versión desarrollada como parte de **1DAM**. Este proyecto demuestra la integración de un **Sistema de Login seguro basado en PHP** con un frontend interactivo que consume datos relacionados con el anime. He implementado dos paradigmas de almacenamiento: **Relacional (MySQL)** y **NoSQL (JSON Estático)**, para mostrar habilidades versátiles de gestión de datos.

---

## ✨ Key Features | Características Clave

- **Dual Backend Versions:** Includes both a database-driven implementation (`version_bd`) and a file-based JSON implementation (`version_json`). | *Versiones de Backend Dobles: Incluye tanto una implementación basada en BD como una basada en archivos JSON.*
- **Secure Authentication:** PHP sessions for user login and protected access to the Anime Dashboard. | *Autenticación Segura: Sesiones PHP para el inicio de sesión y acceso protegido al Dashboard.*
- **JavaScript Interaction:** Dynamic UI updates and AJAX calls to fetch and display anime metadata. | *Interacción JavaScript: Actualizaciones de UI dinámicas y llamadas AJAX para obtener metadatos de anime.*
- **Responsive Layout:** Theming applied through CSS to ensure a consistent look across both versions. | *Diseño Responsivo: Tematización aplicada a través de CSS para asegurar una apariencia consistente.*

---

## 🛠️ Tech Stack | Tecnologías

- **Languages:** PHP (Backend), JavaScript (Frontend), HTML5, CSS3.
- **Database:** MySQL (MariaDB).
- **Data Format:** JSON.

---

## 📂 Project Structure | Estructura del Proyecto

- **`/version_bd/`**: The standard MySQL version using relational tables. | *La versión estándar de MySQL usando tablas relacionales.*
- **`/version_json/`**: A lightweight version using JSON persistence. | *Una versión ligera que usa persistencia JSON.*
- **`/JS/`**: Frontend logic for API consumption and DOM manipulation. | *Lógica frontend para el consumo de API y manipulación del DOM.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Place the project in your local server (e.g., **XAMPP / WAMPServer**). | *Coloca el proyecto en tu servidor local.*
2.  For the DB version, import the schema found in `index.php` or the dedicated SQL folder. | *Para la versión de BD, importa el esquema SQL.*
3.  Access via browser: `http://localhost/AnimeApi/index.php`. | *Accede a través del navegador.*
