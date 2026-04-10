# 🌐 Express.js API: Backend User Management
# 🌐 API en Express.js: Gestión de Usuarios Backend

## 📋 Description | Descripción

A modular **Node.js** backend built with the **Express** framework. This project serves as a RESTful API for user management, featuring database integration, CORS handling, and JSON request parsing.

Un backend modular de **Node.js** desarrollado con el framework **Express**. Este proyecto sirve como una API RESTful para la gestión de usuarios, incluyendo integración con base de datos, manejo de CORS y parseo de peticiones JSON.

---

## ✨ Key Features | Características Clave

- **RESTful Endpoints:** Implementation of standard HTTP methods (GET, POST, etc.) for user data retrieval and manipulation. | *Endpoints RESTful: Implementación de métodos HTTP estándar (GET, POST, etc.) para la obtención y manipulación de datos de usuarios.*
- **Database Integration:** Uses `mysql2` to interact with a MySQL database (`neurocrib`), including error handling for connection issues. | *Integración de Base de Datos: Usa `mysql2` para interactuar con una base de datos MySQL (`neurocrib`), incluyendo manejo de errores para problemas de conexión.*
- **Security & Middleware:** Configured with `CORS` to allow controlled cross-origin requests and `express.json()` for payload parsing. | *Seguridad y Middleware: Configurado con `CORS` para permitir peticiones de origen cruzado controladas y `express.json()` para el parseo de datos.*
- **Clean Environment Separation:** Setup ready for local development with configurable ports and origins. | *Separación de Entornos Limpia: Configuración lista para desarrollo local con puertos y orígenes configurables.*

---

## 🛠️ Tech Stack | Tecnologías

- **Runtime:** Node.js
- **Framework:** Express.js
- **Database:** MySQL
- **Tooling:** `mysql2`, `cors`

---

## 📂 Project Structure | Estructura del Proyecto

- **`/JavaScript/index.js`**: Core server logic, routes, and database connections. | *Lógica central del servidor, rutas y conexiones a la base de datos.*
- **`index.html`**: A basic frontend demo to interact with the API endpoints. | *Una demo básica de frontend para interactuar con los endpoints de la API.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Ensure you have **Node.js** installed. | *Asegúrate de tener **Node.js** instalado.*
2.  Install dependencies: `npm install express mysql2 cors`. | *Instala las dependencias: `npm install express mysql2 cors`.*
3.  Configure your MySQL server and ensure the `neurocrib` database exists. | *Configura tu servidor MySQL y asegúrate de que la base de datos `neurocrib` exista.*
4.  Launch the server: `node JavaScript/index.js`. | *Lanza el servidor: `node JavaScript/index.js`.*
