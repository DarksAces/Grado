# 🧵 SyncLab: Java Multiprocessing & Concurrency Patterns
# 🧵 SyncLab: Laboratorio de Concurrencia y Patrones Multiproceso

## 📋 Description | Descripción

A specialized **Advanced Java** laboratory developed in **2DAM** that explores the mechanics of concurrent execution and resource synchronization. **SyncLab** provides a hands-on comparison between shared-resource systems with and without synchronization. It allows developers to observe **Race Conditions** in real-time and implement corrective patterns using `synchronized` blocks and **Atomic** operations within a multi-client network environment.

Un laboratorio especializado de **Java Avanzado** desarrollado en **2DAM** que explora las mecánicas de ejecución concurrente y sincronización de recursos. **SyncLab** facilita una comparación directa entre sistemas con y sin sincronización, permitiendo observar **Condiciones de Carrera** en tiempo real.

---

## ✨ Key Features | Características Clave

- **Race Condition Simulation:** The `RaceConditionServer.java` module demonstrates the failure of data integrity when multiple threads access shared state without locks. | *Simulación de Condiciones de Carrera: Demuestra fallos en la integridad de datos.*
- **Monitor-based Synchronization:** Implementation of mutual exclusion using Java's `synchronized` monitors in `SynchronizedServer.java`. | *Sincronización basada en Monitores: Implementación de exclusión mutua.*
- **Atomic Operation Lab:** Usage of `java.util.concurrent.atomic` classes in `AtomicServer.java` for high-performance, lock-free thread safety. | *Laboratorio de Operaciones Atómicas: Uso de librerías concurrentes modernas.*
- **Multi-threaded Traffic Model:** `ConcurrentClient.java` designed to launch massive simultaneous requests to test server stability and accuracy. | *Modelo de Tráfico Multihilo: Cliente diseñado para pruebas de estabilidad.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 17+.
- **Concurrency API:** `Threads`, `AtomicInteger`, `Synchronized`.
- **Network API:** Java Sockets.
- **Concepts:** Race Conditions, Critical Sections, Thread-safety, Mutex.

---

## 📂 Project Structure | Estructura del Proyecto

- **`RaceConditionServer.java`**: The "vulnerable" server demonstrating data inconsistency. | *El servidor 'vulnerable' que demuestra inconsistencia.*
- **`SynchronizedServer.java`**: Reliable execution through traditional monitoring. | *Ejecución confiable mediante monitores tradicionales.*
- **`AtomicServer.java`**: Modern, highly-optimized concurrent execution. | *Ejecución concurrente moderna y optimizada.*
- **`ConcurrentClient.java`**: The stress-testing utility for all three servers. | *La utilidad de pruebas de estrés.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Compile all laboratory source files. | *Compila todos los archivos fuente.*
2.  Start the `RaceConditionServer` and run the `ConcurrentClient` to see the results fluctuate. | *Lanza el servidor vulnerable y observa los resultados.*
3.  Launch the `SynchronizedServer` or `AtomicServer` to verify consistency under the same load. | *Lanza los servidores sincronizados para verificar la consistencia.*
