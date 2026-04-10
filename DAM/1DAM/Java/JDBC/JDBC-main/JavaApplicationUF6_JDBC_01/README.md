# 📦 JDBC-Manager: Industrial MySQL Integration & DAO Lab
# 📦 JDBC-Manager: Integración Industrial de MySQL y Laboratorio DAO

## 📋 Description | Descripción

A professional **Java Backend** laboratory developed in **1DAM (UF6)** focused on the industrial orchestration of **JDBC (Java Database Connectivity)**. **JDBC-Manager** implements a complete **DAO (Data Access Object)** pattern to manage product inventory within a MySQL/MariaDB database, mastering the use of `Statement`/`PreparedStatement`, result set mapping, and secure connection orchestration.

Un laboratorio profesional de **Backend de Java** desarrollado en **1DAM (UF6)** centrado en la orquestación industrial de **JDBC**. **JDBC-Manager** implementa un patrón **DAO** completo para gestionar inventarios en bases de datos MySQL.

---

## ✨ Key Features | Características Clave

- **DAO Pattern Orchestration:** specialized `dao` and `model` layers to ensure clean separation of concerns. | *Orquestación de Patrón DAO: Capas especializadas para una separación limpia.*
- **Secure SQL Execution:** High-precision usage of `PreparedStatement` to mitigate SQL injection risks. | *Ejecución Segura de SQL: Uso de PreparedStatement.*
- **Inventory Logic Hub:** High-performance `ProductoManager` logic for CRUD (Create, Read, Update, Delete) orchestration. | *Hub de Lógica de Inventario: Lógica para operaciones CRUD.*
- **Connection Resource Management:** Professional orchestration of database connections and resource closing. | *Gestión de Recursos de Conexión.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 8+.
- **DBMS:** MySQL / MariaDB.
- **Protocol:** JDBC.
- **Concepts:** DAO Pattern, RowSet mapping, DDL/DML, Resource Management.

---

## 📂 Project Structure | Estructura del Proyecto

- **`src/monlau/dao/`**: Data Access Object implementation for MySQL synchronization. | *Implementación DAO.*
- **`src/monlau/model/`**: Dynamic data entities representing products and records. | *Entidades de datos.*
- **`ProductoManager.java`**: The primary execution engine for inventory control. | *Motor de ejecución primario.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Configure your MySQL database using the provided schema. | *Configura tu base de datos MySQL.*
2.  Update the connection parameters in the specialized `dao` package. | *Actualiza los parámetros de conexión.*
3.  Open the project in **NetBeans** or any Java IDE. | *Abre el proyecto en tu IDE.*
4.  Execute the main manager to interact with the database records. | *Ejecuta el manager principal.*
