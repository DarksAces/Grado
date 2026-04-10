# 🧵 Java Concurrency: Multi- threading Fundamentals (Hilos 1)
# 🧵 Concurrencia en Java: Fundamentos de Multihilo (Hilos 1)

## 📋 Description | Descripción

A foundational project developed in **2DAM** focusing on the primary pillars of **Concurrent Programming** in Java. This module explores the lifecycle of threads, synchronization mechanisms, and classic concurrency patterns like the **Producer-Consumer** problem, using both native `Thread` inheritance and the `Runnable` interface.

Un proyecto fundamental desarrollado en **2DAM** enfocado en los pilares de la **Programación Concurrente** en Java. Este módulo explora el ciclo de vida de los hilos, mecanismos de sincronización y patrones clásicos como el problema **Productor-Consumidor**.

---

## ✨ Key Features | Características Clave

- **Thread Evolution:** Comparative implementation of threading using the `Thread` class vs. the `Runnable` interface. | *Evolución de Hilos: Implementación comparativa usando Thread y Runnable.*
- **Resource Management:** Demonstrates the use of `synchronized` blocks and methods to prevent race conditions (e.g., `SincronizacionContador.java`). | *Gestión de Recursos: Demuestra el uso de sincronización para prevenir condiciones de carrera.*
- **Producer-Consumer Pattern:** Classic distributed system simulation where threads coordinate through shared buffers. | *Patrón Productor-Consumidor: Simulación clásica de sistemas distribuidos.*
- **Daemon & Priority Support:** Exploration of background tasks (Daemon threads) and thread priority scheduling (`DemoSleepYieldPriority.java`). | *Soporte de Daemons y Prioridades: Exploración de tareas en segundo plano.*
- **Thread Groups:** Advanced organization of concurrent tasks for collective management. | *Grupos de Hilos: Organización avanzada de tareas concurrentes.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 8+.
- **Core Library:** `java.lang.Thread`, `java.lang.Runnable`.
- **Concurrency Concepts:** Monitors, Semaphores (logic), Thread Lifecycle, Synchronization.

---

## 📂 Project Structure | Estructura del Proyecto

- **`CajeraRunnable.java` / `CajeraThread.java`**: Real-world supermarket simulation using different threading models. | *Simulación de cajera de supermercado.*
- **`ProductorConsumidorSimple.java`**: Implementation of protected resource sharing. | *Implementación de compartición de recursos protegidos.*
- **`EjemploGruposHilos.java`**: Management of hierarchical thread structures. | *Gestión de estructuras de hilos jerárquicas.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Compile the source files in the `src/test` directory. | *Compila los archivos fuente.*
2.  Run the individual class files (e.g., `java hilos.Main`) to see different concurrency behaviors. | *Ejecuta las clases individuales.*
3.  Observe the terminal output to understand how thread interleaving works. | *Observa la salida de terminal para entender el entrelazado de hilos.*
