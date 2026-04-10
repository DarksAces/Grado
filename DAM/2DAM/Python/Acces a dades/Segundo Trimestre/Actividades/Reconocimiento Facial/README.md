# 🤖 BioPass: Biometric Data Access & Facial Logic
# 🤖 BioPass: Acceso a Datos Biométricos y Lógica Facial

## 📋 Description | Descripción

A professional **2DAM** project focusing on sophisticated data persistence and biometric authentication. **BioPass** implements the **DAO (Data Access Object) Pattern** using Python to manage a MySQL database that stores user facial signatures and metadata. It demonstrates a clean separation between database orchestration, configuration management, and application logic.

Un proyecto profesional de **2DAM** centrado en la persistencia de datos sofisticada y la autenticación biométrica. **BioPass** implementa el **Patrón DAO** usando Python para gestionar una base de datos MySQL que almacena firmas faciales y metadatos de usuarios.

---

## ✨ Key Features | Características Clave

- **DAO Design Pattern:** Specialized `usuario_dao.py` for decoupled database operations, ensuring clean and maintainable code. | *Patrón de Diseño DAO: Implementación desacoplada para operaciones de BD.*
- **Automated DB Scaffolding:** Includes `setup_db.py` and `init_db.py` for rapid environment deployment and table initialization. | *Andamiaje de BD Automatizado: Scripts para despliegue rápido de tablas.*
- **Secure Persistence:** Centralized configuration through `config.py` using environment variables or secure constants. | *Persistencia Segura: Configuración centralizada.*
- **Modular Utility Engine:** Specialized `/utils/` module for handling generic tasks like image processing or data formatting. | *Motor de Utilidades Modular: Módulo especializado para tareas genéricas.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Python 3.9+
- **Database:** MySQL (Connector/Python).
- **Patterns:** DAO (Data Access Object), Factory/Singleton for connections.
- **Concepts:** Biometric metadata storage, Schema management.

---

## 📂 Project Structure | Estructura del Proyecto

- **`biopass_app.py`**: The primary application orchestrator. | *El orquestador principal de la aplicación.*
- **`usuario_dao.py`**: The Data Access Object for user-related queries. | *El DAO para consultas de usuarios.*
- **`conexion_db.py`**: Centralized connection management with error pooling. | *Gestión centralizada de conexiones.*
- **`setup_db.sql`**: Schema definition files (located in the SQL/DB subfolders). | *Archivos de definición de esquema.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Configure your MySQL credentials in `config.py`. | *Configura tus credenciales de MySQL en `config.py`.*
2.  Initialize the database: `python src/setup_db.py`. | *Inicializa la base de datos.*
3.  Launch the application: `python src/biopass_app.py`. | *Lanza la aplicación.*
