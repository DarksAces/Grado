# 🔫 La Famiglia Nostra: Weapons Inventory API (Spring Boot)
# 🔫 La Famiglia Nostra: API de Inventario de Armas (Spring Boot)

## 📋 Description | Descripción

A robust **RESTful API** built with **Spring Boot** for managing a specialized weapons inventory system (themed after "La Famiglia Nostra"). It demonstrates professional backend development patterns including DTOs, Enums, and Repository-Service layers.

Una **API RESTful** robusta desarrollada con **Spring Boot** para gestionar un sistema de inventario de armas especializado (tematizado como "La Famiglia Nostra"). Demuestra patrones de desarrollo backend profesionales incluyendo DTOs, Enums y capas de Repositorio-Servicio.

---

## ✨ Key Features | Características Clave

- **Clean Architecture:** Strict separation of concerns using the **Controller-Service-Repository** pattern. | *Arquitectura Limpia: Separación estricta de responsabilidades usando el patrón **Controlador-Servicio-Repositorio**.*
- **Data Transfer Objects (DTO):** Efficient data handling between the API and the persistence layer to maintain security and performance. | *Objetos de Transferencia de Datos (DTO): Manejo eficiente de datos entre la API y la capa de persistencia para mantener la seguridad y el rendimiento.*
- **Enums Integration:** Robust type handling for weapon categories and status. | *Integración de Enums: Manejo robusto de tipos para categorías y estados de armas.*
- **Database Ready:** Includes pre-configured MySQL schema scripts with sample prices and services. | *Listo para BD: Incluye scripts de esquema MySQL preconfigurados con precios y servicios de ejemplo.*

---

## 🛠️ Tech Stack | Tecnologías

- **Framework:** Spring Boot 3.x
- **Language:** Java 17+
- **Build Tool:** Maven
- **Database:** MySQL
- **Dependencies:** Spring Web, Spring Data JPA, Lombok (intended).

---

## 📂 Project Structure | Estructura del Proyecto

- **`PruebaApiRestArmasApplication.java`**: Spring Boot entry point. | *Punto de entrada de Spring Boot.*
- **`/controller/`**: REST Endpoints for handling HTTP requests. | *Endpoints REST para el manejo de peticiones HTTP.*
- **`/service/`**: Core business logic and data processing. | *Lógica de negocio central y procesamiento de datos.*
- **`/repository/`**: Data access interfaces using Spring Data JPA. | *Interfaces de acceso a datos usando Spring Data JPA.*
- **`/model/` & `/dto/`**: Entity definitions and data structures. | *Definiciones de entidades y estructuras de datos.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Set up the MySQL database using `BBDD MySQL + Precio Armas + Servicios.txt`. | *Configura la base de datos MySQL usando `BBDD MySQL + Precio Armas + Servicios.txt`.*
2.  Configure your `application.properties` with the correct DB credentials. | *Configura tu `application.properties` con las credenciales correctas de la BD.*
3.  Run the application using Maven: `./mvnw spring-boot:run`. | *Ejecuta la aplicación usando Maven: `./mvnw spring-boot:run`.*
