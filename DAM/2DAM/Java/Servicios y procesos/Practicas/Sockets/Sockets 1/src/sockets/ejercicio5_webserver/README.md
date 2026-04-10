# 🌐 JavaNet: Multi-threaded Custom Web Server
# 🌐 JavaNet: Servidor Web Multihilo Personalizado

## 📋 Description | Descripción

A high-performance **Java Network** application developed in **2DAM** that implements a functional **HTTP Web Server** from scratch. This project demonstrates advanced mastery of **Java Sockets**, multi-threaded request processing, and the implementation of fundamental web protocols. The server supports concurrent client handling, virtual host management, and automated logging of HTTP traffic.

Una aplicación de **Redes en Java** de alto rendimiento desarrollada en **2DAM** que implementa un **Servidor Web HTTP** funcional desde cero. Este proyecto demuestra el dominio avanzado de **Java Sockets**, procesamiento de peticiones multihilo e implementación de protocolos web fundamentales.

---

## ✨ Key Features | Características Clave

- **Multi-threaded Session Handling:** Uses a scalable thread pool (or per-request threads) to handle multiple simultaneous client connections. | *Manejo de Sesiones Multihilo: Procesa múltiples conexiones simultáneas.*
- **Virtual Host Support:** Specialized `VirtualHost.java` logic to serve different content based on the incoming domain/host header. | *Soporte de Virtual Host: Sirve contenido diferente basado en el header.*
- **Custom HTTP Handler:** Robust `HttpHandler.java` for parsing GET requests, managing MIME types, and serving static assets. | *Handler HTTP Personalizado: Análisis de peticiones GET y gestión de tipos MIME.*
- **Industrial Logging:** Integrated `Logger.java` for real-time traffic auditing and error tracking. | *Logging Industrial: Auditoría de tráfico en tiempo real.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 17+.
- **Network API:** `java.net.ServerSocket`, `java.net.Socket`.
- **Concepts:** TCP/IP Stack, HTTP Protocol (1.0/1.1), Threading, I/O Streams.

---

## 📂 Project Structure | Estructura del Proyecto

- **`WebServer.java`**: The main entry point and listener orchestrator. | *El punto de entrada principal y orquestador.*
- **`HttpHandler.java`**: The core logical engine for processing HTTP requests. | *El motor lógico core para procesar peticiones.*
- **`Config.java`**: Configuration provider for ports, directories, and server limits. | *Proveedor de configuración.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Compile the project using your preferred Java IDE or CLI. | *Compila el proyecto.*
2.  Launch the server: `java WebServer`. | *Lanza el servidor.*
3.  Open a browser and navigate to `http://localhost:<port>`. | *Abre un navegador y navega a la URL.*
4.  Observe the server logs in the terminal as it serves the static files. | *Observa los logs del servidor.*
