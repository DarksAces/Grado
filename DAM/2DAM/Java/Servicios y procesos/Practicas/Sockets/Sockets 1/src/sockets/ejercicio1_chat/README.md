# 💬 JavaMessenger: Multi-User Socket Chat System
# 💬 JavaMessenger: Sistema de Chat Multiusuario por Sockets

## 📋 Description | Descripción

A robust **real-time communication** platform developed in **2DAM** using **Java Sockets**. **JavaMessenger** implements a classic Client-Server architecture where multiple users can connect to a central hub and exchange messages simultaneously. It demonstrates asynchronous I/O management and the fundamental principles of TCP/IP communication for collaborative tools.

Una plataforma de **comunicación en tiempo real** robusta desarrollada en **2DAM** usando **Java Sockets**. **JavaMessenger** implementa una arquitectura Cliente-Servidor clásica donde múltiples usuarios pueden conectarse a un hub central e intercambiar mensajes.

---

## ✨ Key Features | Características Clave

- **Multi-Client Synchronization:** High-concurrency server capable of managing multiple active socket connections. | *Sincronización Multi-Cliente: Servidor de alta concurrencia.*
- **Bidirectional I/O Streams:** Real-time message transmission and reception using `InputStream` and `OutputStream` orchestration. | *Streams de E/S Bidireccionales: Transmisión y recepción en tiempo real.*
- **Robust Protocol Design:** Implementation of a custom message broadcast logic to ensure all connected clients receive updates. | *Diseño de Protocolo Robusto: Implementación de lógica de broadcast.*
- **Clean CLI Interface:** Intuitive command-line interaction for both server monitoring and user chatting. | *Interfaz CLI Limpia: Interacción intuitiva por línea de comandos.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 17+.
- **Network API:** `java.net.Socket`, `java.net.ServerSocket`.
- **Concepts:** TCP/IP communication, Threading (for concurrent clients), Network state management.

---

## 📂 Project Structure | Estructura del Proyecto

- **`ChatServer.java`**: The central communication orchestrator and message broadcaster. | *El orquestador central de comunicación.*
- **`ChatClient.java`**: The user-facing application for connecting and participating in the session. | *La aplicación para el usuario.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Compile both source files. | *Compila ambos archivos fuente.*
2.  Start the server: `java ChatServer`. | *Inicia el servidor.*
3.  Connect one or more clients: `java ChatClient`. | *Conecta uno o más clientes.*
4.  Begin the real-time conversation! | *¡Comienza la conversación en tiempo real!*
