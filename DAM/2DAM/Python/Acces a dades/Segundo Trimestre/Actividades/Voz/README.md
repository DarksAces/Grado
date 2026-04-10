# 🎙️ VoiceAuth: Speech Recognition & Auth System
# 🎙️ VoiceAuth: Sistema de Autenticación por Voz

## 📋 Description | Descripción

A secure authentication module that uses **Voice Recognition** to verify user identity. It integrates **Google Speech Recognition** (via `speech_recognition`) with a **PostgreSQL** backend, utilizing the **DAO** and **Singleton** patterns for robust data management.

Un módulo de autenticación seguro que utiliza **Reconocimiento de Voz** para verificar la identidad del usuario. Integra **Google Speech Recognition** (vía `speech_recognition`) con un backend **PostgreSQL**, utilizando los patrones **DAO** y **Singleton** para una gestión de datos robusta.

---

## ✨ Key Features | Características Clave

- **Speech-to-Text Integration:** Converts live audio input into text to validate secret phrases or user commands. | *Integración Speech-to-Text: Convierte la entrada de audio en vivo en texto para validar frases secretas o comandos del usuario.*
- **Secure Persistence:** Stores user credentials and audio metadata in **PostgreSQL** using a dedicated `auth_dao.py`. | *Persistencia Segura: Almacena credenciales de usuario y metadatos de audio en **PostgreSQL** usando un `auth_dao.py` dedicado.*
- **Audio Debugging:** Includes specialized logic to save and analyze `.wav` files for troubleshooting and recognition accuracy. | *Depuración de Audio: Incluye lógica especializada para guardar y analizar archivos `.wav` para la resolución de problemas y precisión del reconocimiento.*
- **Clean Architecture:** Environment-based configuration and centralized database connection management. | *Arquitectura Limpia: Configuración basada en el entorno y gestión centralizada de la conexión a la base de datos.*

---

## 🛠️ Tech Stack | Tecnologías

- **Environment:** Python 3.9+
- **Voice APIs:** `SpeechRecognition`, `PyAudio`
- **Database:** PostgreSQL
- **Format:** WAV (Pulse-code modulation)

---

## 📂 Project Structure | Estructura del Proyecto

- **`src/main_app.py`**: The main entry point and user flow coordinator. | *El punto de entrada principal y coordinador del flujo de usuario.*
- **`src/voice_service.py`**: Core logic for audio capturing and recognition. | *Lógica central para la captura y reconocimiento de audio.*
- **`src/auth_dao.py`**: Data Access Object for secure user authentication. | *Objeto de Acceso a Datos para la autenticación segura.*
- **`src/conexion_db.py`**: Singleton conector for persistent DB sessions. | *Conector Singleton para sesiones de BD persistentes.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Set up **PostgreSQL** and import the schema from the `/db` folder. | *Configura **PostgreSQL** e importa el esquema de la carpeta `/db`.*
2.  Install system dependencies for audio (e.g., `PyAudio` requirements). | *Instala las dependencias del sistema para audio.*
3.  Install Python packages: `pip install -r requirements.txt`. | *Instala los paquetes de Python: `pip install -r requirements.txt`.*
4.  Run the application: `python -m src.main_app`. | *Ejecuta la aplicación: `python -m src.main_app`.*
