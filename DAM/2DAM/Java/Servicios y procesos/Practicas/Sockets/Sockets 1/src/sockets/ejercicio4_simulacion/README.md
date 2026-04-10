# 🏢 ServerSim: Web Server Architectural Laboratory
# 🏢 ServerSim: Laboratorio de Arquitectura de Servidores Web

## 📋 Description | Descripción

An advanced **Java Networking** simulation developed in **2DAM** that explores the architectural differences between major web server models. **ServerSim** provides functional implementations of both **Apache-style** (Process-driven/Thread-per-request) and **Nginx-style** (Event-driven/Reactive) models using Java Sockets. This laboratory allows for a direct performance comparison under varying loads and explores the efficiency of different request handling strategies.

Una simulación avanzada de **Redes en Java** desarrollada en **2DAM** que explora las diferencias arquitectónicas entre los principales modelos de servidores web. **ServerSim** proporciona implementaciones funcionales tanto del modelo estilo **Apache** como del modelo estilo **Nginx**.

---

## ✨ Key Features | Características Clave

- **Apache Architecture Simulation:** Focused on the thread-per-connection paradigm implemented in `ApacheSimulator.java`. | *Simulación de Arquitectura Apache: Enfocada en el paradigma de hilo-por-conexión.*
- **Nginx Architecture Simulation:** Focused on the high-performance event loop and reactive processing in `NginxSimulator.java`. | *Simulación de Arquitectura Nginx: Enfocada en el bucle de eventos.*
- **Heavy Load Modeling:** Specialized `HeavyLoadClient.java` designed to benchmark both simulators and identify architectural bottlenecks. | *Modelado de Carga Pesada: Cliente especializado para benchmarking.*
- **Performance Auditing:** Real-time metrics on response times, resource consumption, and concurrent session limits. | *Auditoría de Rendimiento: Métricas en tiempo real.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 17+.
- **Concepts:** Event-driven vs Process-driven models, Threading, Sockets, Scalability, I/O multiplexing logic.

---

## 📂 Project Structure | Estructura del Proyecto

- **`ApacheSimulator.java`**: The process-heavy architectural model. | *El modelo arquitectónico basado en procesos/hilos.*
- **`NginxSimulator.java`**: The lightweight, high-concurrency event-driven model. | *El modelo ligero basado en eventos.*
- **`HeavyLoadClient.java`**: Orchestrator for large-scale stress testing. | *Orquestador para pruebas de estrés.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Compile all simulators and the load client. | *Compila todos los simuladores.*
2.  Launch the `ApacheSimulator` and run the `HeavyLoadClient` to measure overhead. | *Lanza el simulador Apache y mide el rendimiento.*
3.  Switch to the `NginxSimulator` and repeat the stress test to compare efficiency. | *Cambia a Nginx y compara la eficiencia.*
