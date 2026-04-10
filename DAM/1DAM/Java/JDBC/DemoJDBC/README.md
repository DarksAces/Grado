# 🍃 Spring Boot JDBC: Modern Relational Persistence
# 🍃 Spring Boot JDBC: Persistencia Relacional Moderna

## 📋 Description | Descripción

An advanced **1DAM** project demonstrating the integration of **Spring Boot** with **JDBC** for high-performance relational database management. This module moves beyond basic JDBC libraries to explore the **Repository Pattern**, automated dependency injection, and modern application lifecycles while maintaining direct control over SQL execution.

Un proyecto avanzado de **1DAM** que demuestra la integración de **Spring Boot** con **JDBC** para la gestión de bases de datos relacionales de alto rendimiento. Este módulo explora el **Patrón Repositorio**, inyección de dependencias automatizada y ciclos de vida modernos manteniendo el control directo sobre la ejecución de SQL.

---

## ✨ Key Features | Características Clave

- **Spring Ecosystem:** Full use of the Spring container for bean management and specialized data access components. | *Ecosistema Spring: Uso completo del contenedor de Spring para la gestión de componentes.*
- **Repository Pattern:** Specialized implementation of the DAO (Data Access Object) pattern using Spring's abstractions. | *Patrón Repositorio: Implementación especializada del patrón DAO.*
- **Dual Phase Evolution:** Divided into two parts (Parte 1 & Parte 2) reflecting the transition from raw JDBC to optimized Spring Data patterns. | *Evolución de Doble Fase: Dividido en dos partes que reflejan la transición a patrones optimizados.*
- **Automated Lifecycle:** Managed build and execution using **Maven**, including integrated tests and environment configuration. | *Ciclo de Vida Automatizado: Compilación y ejecución gestionadas con Maven.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 11+.
- **Framework:** Spring Boot.
- **Data Access:** JDBC Template / Spring Data.
- **Build Tool:** Maven.

---

## 📂 Project Structure | Estructura del Proyecto

- **`/src/main/java/.../model/`**: Pure business entities representing database tables. | *Entidades de negocio.*
- **`/src/main/java/.../repository/`**: Persistence logic and SQL mapping. | *Lógica de persistencia y mapeo SQL.*
- **`pom.xml`**: Project metadata and Spring Boot starters. | *Metadatos del proyecto y dependencias.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Open the project in **IntelliJ IDEA** or any Maven-compatible IDE. | *Abre el proyecto en IntelliJ u otro IDE compatible con Maven.*
2.  Configure your database credentials in `src/main/resources/application.properties`. | *Configura las credenciales de la BD.*
3.  Run the application: `./mvnw spring-boot:run`. | *Ejecuta la aplicación.*
4.  Verify the connection and data persistence via the integrated console logs. | *Verifica la conexión y persistencia.*
