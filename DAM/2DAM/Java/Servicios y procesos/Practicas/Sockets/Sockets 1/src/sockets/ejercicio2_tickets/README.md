# 🎫 TicketVault: Concurrent Ticket Management System
# 🎫 TicketVault: Sistema de Gestión de Tickets Concurrente

## 📋 Description | Descripción

A high-concurrency **Java Network** laboratory developed in **2DAM** representing a Ticket Distribution System. **TicketVault** evolves through 3 distinct architectural versions (`v1`, `v2`, `v3`), moving from basic sequential state management to a fully distributed, synchronized multi-client system. It includes a specialized `HeavyLoadTicketClient` to simulate industrial-scale traffic and validate atomicity in ticket sales.

Un laboratorio de **Redes en Java** de alta concurrencia desarrollado en **2DAM** que representa un Sistema de Distribución de Tickets. **TicketVault** evoluciona a través de 3 versiones arquitectónicas distintas, desde la gestión secuencial básica hasta un sistema multicliente sincronizado y distribuido.

---

## ✨ Key Features | Características Clave

- **Architectural Evolution:** 3-stage progression (`v1-v3`) focusing on thread safety and network atomicity. | *Evolución Arquitectónica: Progresión de 3 etapas enfocada en thread safety.*
- **Concurrency Stress Lab:** Specialized `HeavyLoadTicketClient.java` designed to simulate thousands of simultaneous requests. | *Laboratorio de Estrés de Concurrencia: Cliente diseñado para miles de peticiones.*
- **Atomic State Management:** Implementation of synchronized blocks and locks to prevent double-booking and race conditions. | *Gestión de Estado Atómica: Evita condiciones de carrera y duplicados.*
- **Distributed Architecture:** Clean separation between the Ticket Server (State provider) and the Client (Consumer). | *Arquitectura Distribuida: Separación entre Servidor y Cliente.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 17+.
- **Network API:** Java Sockets (TCP/IP).
- **Concepts:** Concurrency, Stress Testing, Synchronization, Atomic Operations.

---

## 📂 Project Structure | Estructura del Proyecto

- **`v1 / v2 / v3`**: Directories containing the iterative improvements of the ticket engine. | *Directorios con las mejoras iterativas del motor.*
- **`TicketClient.java`**: Standard user-facing client for single ticket purchase. | *Cliente estándar para la compra de tickets.*
- **`HeavyLoadTicketClient.java`**: Technical utility for large-scale stress testing. | *Utilidad técnica para pruebas de estrés.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Compile the desired version (`v1`, `v2`, or `v3`). | *Compila la versión deseada.*
2.  Start the Ticket Server from that version. | *Inicia el Servidor de Tickets.*
3.  Launch multiple instances of the `TicketClient` or a single `HeavyLoadTicketClient`. | *Lanza múltiples clientes.*
4.  Observe the synchronization logs and verify that no duplicate tickets are issued. | *Verifica que no se emitan tickets duplicados.*
