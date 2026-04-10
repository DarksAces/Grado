# 🔐 FastAPI Security: JWT & Vulnerability Mitigation
# 🔐 Seguridad en FastAPI: JWT y Mitigación de Vulnerabilidades

## 📋 Description | Descripción

A specialized **FastAPI** project developed to master modern web security standards. It demonstrates the implementation of **JSON Web Tokens (JWT)** for secure authentication (OAuth2), while also exploring common vulnerabilities (like Command Injection and SSRF) and their respective countermeasures.

Un proyecto especializado en **FastAPI** desarrollado para dominar los estándares modernos de seguridad web. Demuestra la implementación de **JSON Web Tokens (JWT)** para la autenticación segura (OAuth2), mientras explora vulnerabilidades comunes (como Inyección de Comandos y SSRF) y sus respectivas contramedidas.

---

## ✨ Key Features | Características Clave

- **JWT Authentication:** Complete OAuth2 flow using `jose` to encode/decode tokens with expiration logic and secure key handling. | *Autenticación JWT: Flujo OAuth2 completo usando `jose` para codificar/decodificar tokens con lógica de expiración.*
- **Security Dependency Injection:** Uses FastAPI's `Depends` for granular access control on protected routes. | *Inyección de Dependencias de Seguridad: Usa `Depends` de FastAPI para un control de acceso granular.*
- **Vulnerability Lab:** Includes intentional vulnerabilities (SSRF in `fetch_data`) to demonstrate real-world risks. | *Laboratorio de Vulnerabilidades: Incluye vulnerabilidades intencionales (SSRF) para demostrar riesgos reales.*
- **Mitigation Best Practices:** Shows a secure implementation of system pings (`ping_seguro`) that prevents Command Injection (CWE-78) by avoiding shell execution. | *Mitigación de Riesgos: Muestra una implementación segura de pings al sistema que evita la Inyección de Comandos.*
- **CORS Configuration:** Tailored for local testing, supporting `file://` and `null` origins for integrated HTML testing. | *Configuración CORS: Adaptada para pruebas locales, permitiendo orígenes `file://` para pruebas con HTML integrado.*

---

## 🛠️ Tech Stack | Tecnologías

- **Framework:** FastAPI
- **Security APIs:** `jose` (JWT), `OAuth2PasswordBearer`, `CORSMiddleware`.
- **Logic:** Asynchronous Python (async/await).

---

## 📂 Project Structure | Estructura del Proyecto

- **`main.py`**: The core API logic, security dependencies, and vulnerability demos. | *La lógica central de la API, dependencias de seguridad y demos de vulnerabilidad.*
- **`test.html`**: A frontend dashboard for testing JWT generation and accessing protected endpoints. | *Un dashboard frontend para probar la generación de JWT y el acceso a rutas protegidas.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Set the `JWT_SECRET_KEY` environment variable. | *Configura la variable de entorno `JWT_SECRET_KEY`.*
2.  Install dependencies: `pip install fastapi uvicorn python-jose[cryptography] requests`. | *Instala las dependencias.*
3.  Run the server: `uvicorn main:app --reload --port 8001`. | *Ejecuta el servidor.*
4.  Open `test.html` in your browser to interact with the security flow. | *Abre `test.html` en tu navegador para interactuar con el flujo de seguridad.*
