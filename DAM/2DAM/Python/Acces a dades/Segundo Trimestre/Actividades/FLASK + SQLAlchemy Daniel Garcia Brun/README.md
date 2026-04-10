# 🐍 Flask & SQLAlchemy: Book Inventory API
# 🐍 Flask y SQLAlchemy: API de Inventario de Libros

## 📋 Description | Descripción

A production-grade **RESTful API** built with **Flask** and **SQLAlchemy**. This project demonstrates advanced backend patterns in Python, including the **Singleton** pattern for database management, **Marshmallow** for object serialization, and a clean **Route/Controller** separation.

Una **API RESTful** de grado de producción desarrollada con **Flask** y **SQLAlchemy**. Este proyecto demuestra patrones backend avanzados en Python, incluyendo el patrón **Singleton** para la gestión de la base de datos, **Marshmallow** para la serialización de objetos y una separación limpia entre **Rutas/Controladores**.

---

## ✨ Key Features | Características Clave

- **ORM Integration:** Uses **SQLAlchemy** to interact with a SQLite database (`db.sqlite`) through Python classes instead of raw SQL queries. | *Integración ORM: Usa **SQLAlchemy** para interactuar con una base de datos SQLite a través de clases de Python en lugar de consultas SQL puras.*
- **Schema Validation:** Implements **Marshmallow** schemas to ensure data integrity and automated JSON serialization/deserialization. | *Validación de Esquemas: Implementa esquemas de **Marshmallow** para asegurar la integridad de los datos y la serialización/deserialización JSON automatizada.*
- **Modular Architecture:** Organized into `src/config`, `src/models`, and `src/routes` for high maintainability. | *Arquitectura Modular: Organizada en `src/config`, `src/models` y `src/routes` para una alta mantenibilidad.*
- **API Testing Ready:** Includes `requests.http` file for rapid testing within the IDE using REST Client tools. | *Listo para Pruebas de API: Incluye el archivo `requests.http` para pruebas rápidas dentro del IDE.*

---

## 🛠️ Tech Stack | Tecnologías

- **Framework:** Flask
- **ORM:** SQLAlchemy
- **Serialization:** Marshmallow
- **Database:** SQLite

---

## 📂 Project Structure | Estructura del Proyecto

- **`app.py`**: Main application entry point and configuration. | *Punto de entrada y configuración de la aplicación.*
- **`/src/models/`**: Data models (Entities). | *Modelos de datos (Entidades).*
- **`/src/routes/`**: API endpoint definitions. | *Definiciones de los endpoints de la API.*
- **`requirements.txt`**: Dependency list. | *Lista de dependencias.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Install dependencies: `pip install -r requirements.txt`. | *Instala las dependencias: `pip install -r requirements.txt`.*
2.  Run the application: `python app.py`. | *Ejecuta la aplicación: `python app.py`.*
3.  Test the API: Use the `requests.http` file or an external tool like Postman. | *Prueba la API: Usa el archivo `requests.http` o una herramienta externa como Postman.*
