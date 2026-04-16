# 🥖 Bakery POS: Desktop-API Synchronization System
# 🥖 Bakery POS: Sistema de Sincronización Desktop-API

## 📋 System Overview | Descripción del Sistema

A complete **Point of Sale (POS)** system that demonstrates modern RESTful software architecture by separating the terminal interface from the core business logic. This project highlights specialized integration between a Windows-native client and a high-performance Python backend.

Un sistema completo de **Punto de Venta (POS)** que demuestra una arquitectura de software RESTful moderna al separar la interfaz de la terminal de la lógica de negocio central. Este proyecto destaca la integración especializada entre un cliente nativo de Windows y un backend de Python de alto rendimiento.

---

## ✨ Key Features | Características Clave

- **Frontend (C# Desktop Client):** A rich user interface built with **Windows Forms** that handles sales, inventory management, and technical reporting. | *Frontend (Cliente C#): Una interfaz de usuario enriquecida construida con **Windows Forms** que gestiona ventas, inventario e informes técnicos.*
- **Backend (Python FastAPI Server):** The "brain" of the system that manages stock validation, sales history, and JSON data persistence. | *Backend (Servidor FastAPI): El "cerebro" del sistema que gestiona la validación de stock, histórico de ventas y persistencia JSON.*
- **Real-time Synchronization:** All transactions are validated against the server in real-time using asynchronous network calls. | *Sincronización en Tiempo Real: Todas las transacciones se validan contra el servidor en tiempo real usando llamadas de red asíncronas.*
- **Fault Tolerance:** Robust handling of API error codes (e.g., `HTTP 400 - Insufficient Stock`) to ensure data integrity. | *Tolerancia a Fallos: Manejo robusto de códigos de error de la API para asegurar la integridad de los datos.*

---

## 🛠️ Tech Stack | Tecnologías

- **Frontend:** C# .NET Framework (WinForms)
- **Backend:** Python 3.x, FastAPI
- **Data Persistence:** JSON (NoSQL approach)
- **C# Networking:** `HttpClient`, `Newtonsoft.Json` (Async/Await)

---

## 📂 Folder Structure | Estructura de Carpetas

- **`/BackendAPI/`**: Python FastAPI server files. | *Archivos del servidor FastAPI en Python.*
- **`/ClienteApp/`**: C# Solution containing the desktop POS project. | *Solución C# que contiene el proyecto de escritorio.*
- **`datos.json`**: Inventory database. | *Base de datos de inventario.*
- **`ventas.json`**: Transaction historical record. | *Registro histórico de transacciones.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  **Start the Server**: Run `python main.py` in the `/BackendAPI/` directory. | *Inicia el Servidor: Ejecuta `python main.py` en el directorio `/BackendAPI/`.*
2.  **Start the Client**: Open the `.sln` file and run the application in Visual Studio. | *Inicia el Cliente: Abre el archivo `.sln` y ejecuta la aplicación en Visual Studio.*
3.  **Synchronization**: The client will automatically connect to `http://127.0.0.1:8000`. | *Sincronización: El cliente se conectará automáticamente a la dirección local.*

---

> [!IMPORTANT]
> This project demonstrates the power of **Multiplatform Architectures** (Windows Desktop + Python Server) which is a core skill in the **2nd year of DAM**.
> *Este proyecto demuestra el poder de las **Arquitecturas Multiplataforma** (Escritorio + Servidor), una habilidad core en **2º de DAM**.*
