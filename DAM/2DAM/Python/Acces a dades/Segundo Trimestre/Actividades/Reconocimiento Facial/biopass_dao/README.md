# 🆔 BioPass DAO: Biometric Access Control
# 🆔 BioPass DAO: Control de Acceso Biométrico

## 📋 Overview | Resumen de Proyecto

A secure biometric access control system implemented in **Python**, utilizing **PostgreSQL** for persistence and **OpenCV** for real-time facial recognition. This project demonstrates professional software engineering patterns, specifically **DAO (Data Access Object)** and **Singleton**, ensuring high maintainability and performance.

Un sistema de control de acceso biométrico seguro implementado en **Python**, utilizando **PostgreSQL** para la persistencia y **OpenCV** para el reconocimiento facial en tiempo real. Este proyecto demuestra patrones de ingeniería de software profesionales, específicamente **DAO (Data Access Object)** y **Singleton**, asegurando una alta mantenibilidad y rendimiento.

---

## ✨ Key Features | Características Clave

- **Facial Recognition:** Uses LBPH (Local Binary Patterns Histograms) through OpenCV for real-time identity verification and training. | *Reconocimiento Facial: Usa LBPH a través de OpenCV para la verificación de identidad y entrenamiento en tiempo real.*
- **DAO Pattern:** Centralized data access logic in `src/usuario_dao.py`, decoupling business logic from SQL queries. | *Patrón DAO: Lógica de acceso a datos centralizada en `src/usuario_dao.py`, desacoplando la lógica de negocio de las consultas SQL.*
- **Singleton Pattern:** Implementation of a thread-safe singleton in `src/conexion_db.py` to manage the database connection life-cycle. | *Patrón Singleton: Implementación de un singleton seguro en `src/conexion_db.py` para gestionar el ciclo de vida de la conexión a la BD.*
- **Blob Storage:** Direct storage of biometric templates (images) as binary large objects (BLOBs) within PostgreSQL. | *Almacenamiento Blob: Almacenamiento directo de plantillas biométricas (imágenes) como objetos binarios grandes (BLOBs) dentro de PostgreSQL.*

---

## 🛠️ Tech Stack | Tecnologías

- **Environment:** Python 3.9+
- **Computer Vision:** OpenCV
- **Database:** PostgreSQL
- **Pattern:** DAO, Singleton
- **Environment:** `.env` for secure credential management.

---

## 📂 Project Structure | Estructura del Proyecto

- **`src/biopass_app.py`**: Main application GUI and flow. | *GUI y flujo de la aplicación principal.*
- **`src/usuario_dao.py`**: Data Access Object for user management. | *Objecto de Acceso a Datos para la gestión de usuarios.*
- **`src/conexion_db.py`**: Singleton database connector. | *Conector de base de datos Singleton.*
- **`src/utils/camera_utils.py`**: OpenCV logic and image processing helpers. | *Lógica de OpenCV y utilidades de procesamiento de imágenes.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Set up **PostgreSQL** and create a database named `biopass_db`. | *Configura **PostgreSQL** y crea una BD llamada `biopass_db`.*
2.  Configure your credentials in the `.env` file. | *Configura tus credenciales en el archivo `.env`.*
3.  Install dependencies: `pip install -r requirements.txt`. | *Instala las dependencias: `pip install -r requirements.txt`.*
4.  Initialize the schema: `python -m src.init_db`. | *Inicializa el esquema: `python -m src.init_db`.*
5.  Run the app: `python -m src.biopass_app`. | *Ejecuta la app: `python -m src.biopass_app`.*
