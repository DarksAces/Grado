# 🛡️ BOLA: Broken Object Level Authorization Lab
# 🛡️ BOLA: Laboratorio de Autorización a Nivel de Objeto

## 📋 Description | Descripción

A specialized cybersecurity laboratory focusing on the **BOLA (Broken Object Level Authorization)** vulnerability, ranked #1 in the OWASP API Security Top 10. This project demonstrates how inadequate authorization checks in secondary API endpoints can lead to unauthorized data exposure, allowing users to access or modify resources belonging to others.

Un laboratorio de ciberseguridad especializado centrado en la vulnerabilidad **BOLA (Broken Object Level Authorization)**, la número 1 en el Top 10 de Seguridad de APIs de OWASP. Este proyecto demuestra cómo la falta de validación de permisos en endpoints secundarios puede exponer datos privados, permitiendo que un usuario acceda a recursos ajenos.

---

## ✨ Key Features | Características Clave

- **Vulnerability Demonstration:** Real-world scenario involving a Flask API where an authenticated user can access any profile by simply changing the `user_id` in the URL. | *Demostración de Vulnerabilidad: Escenario real donde un usuario autenticado puede acceder a cualquier perfil cambiando el ID.*
- **JWT Integration:** Use of **JSON Web Tokens (Flask-JWT-Extended)** to simulate a professional authentication environment. | *Integración JWT: Uso de tokens JWT para simular un entorno de autenticación profesional.*
- **Exploit Scenarios:** Documented steps to simulate an IDOR (Insecure Direct Object Reference) attack using tools like Postman or Burp Suite. | *Escenarios de Exploit: Pasos documentados para simular un ataque IDOR.*
- **Mitigation Logic:** Strategic recommendations on how to implement ownership checks (comparing the token's identity with the requested resource). | *Lógica de Mitigación: Recomendaciones para implementar validaciones de propiedad.*

---

## 🛠️ Tech Stack | Tecnologías

- **Framework:** Flask (Python).
- **Authentication:** JWT (jsonwebtoken).
- **Database:** Mock memory-based NoSQL storage.
- **Security Concepts:** OWASP API1:2023, IDOR, Authorization vs Authentication.

---

## 📂 Project Structure | Estructura del Proyecto

- **`server_insecure.py`**: The vulnerable API implementation lacking authorization filters. | *La implementación de la API vulnerable sin filtros de autorización.*
- **`README.md`**: Technical documentation of the flaw and mitigation strategies. | *Documentación técnica del fallo y estrategias de mitigación.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Install dependencies: `pip install flask flask-jwt-extended flask-cors`. | *Instala las dependencias.*
2.  Start the server: `python server_insecure.py`. | *Inicia el servidor.*
3.  Obtain a token via `/login` and attempt to access a different user's profile at `/user/<id>`. | *Obtén un token y prueba a acceder a otros perfiles.*
