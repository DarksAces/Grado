# 🌐 Java Sockets: Distributed Systems & Networking
# 🌐 Sockets en Java: Sistemas Distribuidos y Redes

## 📋 Description | Descripción

A comprehensive collection of **Networking** projects developed in **Java** using the `java.net` API. This package covers the fundamentals of distributed systems, from simple client-server chats to complex multi-threaded web servers and ticket management systems.

Una colección completa de proyectos de **Redes** desarrollados en **Java** usando la API `java.net`. Este paquete cubre los fundamentos de los sistemas distribuidos, desde chats simples cliente-servidor hasta servidores web multihilo complejos y sistemas de gestión de tickets.

---

## ✨ Key Features | Características Clave

- **Multi-threaded Web Server:** A background server (`ejercicio5_webserver`) capable of serving static content to multiple concurrent clients. | *Servidor Web Multihilo: Un servidor capaz de servir contenido estático a múltiples clientes concurrentes.*
- **Distributed Chat System:** Real-time communication bridge using TCP/IP protocols. | *Sistema de Chat Distribuido: Puente de comunicación en tiempo real usando protocolos TCP/IP.*
- **Concurrency & Logic:** Specialized simulations (`ejercicio3`, `ejercicio4`) demonstrating race conditions and synchronization over networks. | *Concurrencia y Lógica: Simulaciones especializadas que demuestran condiciones de carrera y sincronización en red.*
- **Ticket Management:** Distributed architecture for handling centralized resource requests. | *Gestión de Tickets: Arquitectura distribuida para el manejo de peticiones de recursos centralizados.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 8+
- **Protocol:** TCP/IP (Sockets / ServerSockets).
- **Architecture:** Client-Server, Multi-threading (Worker patterns).

---

## 📂 Project Structure | Estructura del Proyecto

- **`ejercicio1_chat`**: TCP chat logic. | *Lógica de chat TCP.*
- **`ejercicio2_tickets`**: Network-based ticket issuance. | *Emisión de tickets basada en red.*
- **`ejercicio5_webserver`**: HTTP-like server implementation. | *Implementación de servidor tipo HTTP.*
- **`/www/`**: Static resource directories for the web server testing. | *Directorios de recursos estáticos para pruebas del servidor web.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Compile the projects in your preferred Java IDE. | *Compila los proyectos en tu IDE de Java preferido.*
2.  Start the **Server** component first (e.g., in `ejercicio1` or `ejercicio5`). | *Inicia primero el componente **Servidor**.*
3.  Connect one or more **Clients** using the target IP and Port defined in the code. | *Conecta uno o más **Clientes** usando la IP y Puerto definidos.*
