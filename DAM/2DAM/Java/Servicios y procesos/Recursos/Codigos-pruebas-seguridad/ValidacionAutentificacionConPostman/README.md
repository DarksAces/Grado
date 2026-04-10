# 🔐 AuthGuard: API Authentication & Postman Validation Lab
# 🔐 AuthGuard: Laboratorio de Autenticación de API y Validación con Postman

## 📋 Description | Descripción

A specialized cybersecurity and backend laboratory developed in **2DAM** focusing on the primary **API Authentication** protocols. This project provides a collection of Python-based servers demonstrating the implementation and testing of various security schemes (Basic Auth, Bearer Tokens, API Keys, and JWT) using **Postman** for real-time validation.

Un laboratorio especializado de ciberseguridad y backend desarrollado en **2DAM** enfocado en los principales protocolos de **Autenticación de APIs**. Este proyecto proporciona una colección de servidores basados en Python que demuestran la implementación y prueba de varios esquemas de seguridad.

---

## ✨ Key Features | Características Clave

- **Multi-Protocol Lab:** Indvidiual modules covering the entire authentication spectrum:
    - **Basic Auth:** Traditional username/password headers. | *Autenticación Básica.*
    - **Bearer Tokens:** Modern token-based access control. | *Tokens de Portador.*
    - **API Keys:** Static key-based identification. | *Claves de API.*
    - **JWT (JSON Web Tokens):** Decoupled, stateless security sessions. | *JWT (Tokens Web JSON).*
- **Postman Integration:** Specialized collection logic for testing headers, payloads, and response codes. | *Integración con Postman: Lógica para probar cabeceras y payloads.*
- **Stateful Validation:** Demonstrates how servers interpret and verify different token structures. | *Validación de Estado: Demuestra cómo los servidores interpretan los tokens.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Python 3.9+.
- **Framework:** Flask (Python).
- **Security:** JWT, Base64 Encoding, SHA-256 (where applicable).
- **Tools:** Postman / Insomnia for API testing.

---

## 📂 Project Structure | Estructura del Proyecto

- **`BasicAuth.py`**: Implementation of standardized HTTP Basic Auth. | *Implementación de Auth Básica.*
- **`BearerToken.py`**: Logic for validating modern Bearer strings. | *Lógica para validar strings de portador.*
- **`ApiKey.py`**: Header-based key verification. | *Verificación de claves basada en cabeceras.*
- **`jwt1.py`**: Advanced logic for signing and verifying JSON Web Tokens. | *Lógica avanzada para firmar y verificar JWT.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Open the desired module (e.g., `python jwt1.py`). | *Inicia el módulo deseado.*
2.  Open **Postman** and configure the corresponding Authorization header (Basic, Bearer, etc.). | *Abre **Postman** y configura la cabecera correspondiente.*
3.  Send a `GET` or `POST` request to the local server (usually port 5000). | *Envía una petición al servidor local.*
4.  Observe the server logs to verify successful authentication. | *Observa los logs para verificar el éxito.*
