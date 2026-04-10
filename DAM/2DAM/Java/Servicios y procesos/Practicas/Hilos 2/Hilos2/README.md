# 🧵 Advanced Multi-threading: Concurrency Patterns in Java
# 🧵 Multihilo Avanzado: Patrones de Concurrencia en Java

## 📋 Description | Descripción

An advanced exploration of **Java Concurrency** and multi-threading models. This project covers modern synchronization techniques, thread pool management, and asynchronous programming using high-level APIs from `java.util.concurrent`.

Una exploración avanzada de la **Concurrencia en Java** y los modelos multihilo. Este proyecto cubre técnicas de sincronización modernas, gestión de pools de hilos y programación asíncrona usando APIs de alto nivel de `java.util.concurrent`.

---

## ✨ Key Features | Características Clave

- **Modern Async Patterns:** Implementation of `Callable`, `Future`, and `CompletableFuture` for non-blocking task execution. | *Patrones Asíncronos Modernos: Implementación de `Callable`, `Future` y `CompletableFuture` para la ejecución de tareas no bloqueantes.*
- **Thread Management:** Demonstrates the use of `FixedThreadPool` and `CachedThreadPool` for efficient resource allocation. | *Gestión de Hilos: Demuestra el uso de hilos fijos y cacheados para una asignación eficiente de recursos.*
- **Explicit Synchronization:** Use of `ReentrantLock` and `Condition` in the `LockBufferDemo` for granular control over shared resources. | *Sincronización Explícita: Uso de `ReentrantLock` y `Condition` en `LockBufferDemo` para un control granular sobre recursos compartidos.*
- **Traffic Simulation:** A complex **Swing-based GUI** application (`TrafficSwingSim`) that visualizes thread interactions through a real-time traffic light simulation. | *Simulación de Tráfico: Una aplicación **GUI basada en Swing** compleja que visualiza interacciones de hilos mediante una simulación de semáforos en tiempo real.*
- **Concurrency Hazards:** Practical examples of Visibility and Atomicity issues and how to solve them. | *Peligros de Concurrencia: Ejemplos prácticos de problemas de visibilidad y atomicidad y cómo resolverlos.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 8+
- **APIs:** `java.util.concurrent`, `java.awt`, `javax.swing` (for simulation).
- **Hardward/Kernel:** Thread-safe memory models.

---

## 📂 Project Structure | Estructura del Proyecto

- **`TrafficSwingSim.java`**: The visual traffic simulation orchestrator. | *El orquestador de la simulación visual de tráfico.*
- **`ThreadPoolDemo.java`**: Management of worker threads. | *Gestión de hilos de trabajo.*
- **`CallableFutureDemo.java`**: Handling task results and exceptions. | *Manejo de resultados y excepciones de tareas.*
- **`LockBufferDemo.java`**: Producer-Consumer pattern with locks. | *Patrón Productor-Consumidor con bloqueos.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Open the project in **NetBeans** or any Java IDE. | *Abre el proyecto en **NetBeans** o cualquier IDE de Java.*
2.  Run `TrafficSwingSim.java` to see the visual concurrent simulation. | *Ejecuta `TrafficSwingSim.java` para ver la simulación concurrente visual.*
3.  Execute individual demos to explore specific concurrency concepts. | *Ejecuta las demos individuales para explorar conceptos de concurrencia específicos.*
