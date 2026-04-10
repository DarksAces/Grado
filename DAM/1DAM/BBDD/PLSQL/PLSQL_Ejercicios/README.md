# 🛠️ PL/SQL Mastery: Advanced Oracle Database Programming
# 🛠️ Maestría en PL/SQL: Programación Avanzada de Bases de Datos Oracle

## 📋 Overview | Resumen de Proyecto

This repository contains a comprehensive, categorized collection of **PL/SQL** scripts designed to master procedural programming within Oracle Database environments. From basic block structures to advanced solutions for complex database problems like **Mutating Tables**.

Este repositorio contiene una colección completa y categorizada de scripts **PL/SQL** diseñados para dominar la programación procedimental en entornos de bases de Datos Oracle. Desde estructuras de bloques básicas hasta soluciones avanzadas para problemas complejos como **Tablas Mutantes**.

---

## 📂 Project Structure | Estructura del Proyecto

- **`01_Estructura_Basica`**: Fundamentals of PL/SQL block structure. | *Fundamentos de la estructura de bloques PL/SQL.*
- **`02_Cursores`**: Advanced cursor management (Explicit, Implicit, Dynamic, Parameters). | *Gestión avanzada de cursores (Explícitos, Implícitos, Dinámicos, Parámetros).*
- **`03_Tipos_Datos`**: Use of `ROWTYPE`, `RECORD`, and custom types. | *Uso de `ROWTYPE`, `RECORD` y tipos personalizados.*
- **`04_Colecciones`**: Indexed tables, associative arrays, and **BULK COLLECT**. | *Tablas indexadas, arrays asociativos y **BULK COLLECT**.*
- **`05_Excepciones`**: Error handling and custom exception management. | *Manejo de errores y gestión de excepciones personalizadas.*
- **`06_Procedimientos_Funciones`**: Reusable modules and business logic encapsulation. | *Módulos reutilizables y encapsulación de lógica de negocio.*
- **`07_Triggers`**: Row-level and Statement-level triggers, including **Mutating Table fixes**. | *Triggers de fila y sentencia, incluyendo **soluciones a tablas mutantes**.*
- **`08_Bucles`**: Advanced control structures and loops. | *Estructuras de control avanzadas y bucles.*

---

## ✨ Featured Solutions | Soluciones Destacadas

### 🔄 Mutating Table Solution | Solución a Tabla Mutante
A common issue in Oracle where a trigger tries to query or modify the same table that fired it. 
*Un problema común en Oracle donde un trigger intenta consultar o modificar la misma tabla que lo disparó.*

- See: [`07_Triggers/02_trigger_compuesto_solucion_tabla_mutante.sql`](07_Triggers/02_trigger_compuesto_solucion_tabla_mutante.sql)

---

## 🛠️ Tech Stack | Tecnologías

- **Environment:** Oracle SQL Developer / SQL Plus
- **Language:** PL/SQL (Procedural Language for SQL)
- **Concepts:** OOP-like patterns in DB, Real-time auditing via triggers.

---

## 🚀 How to Run | Cómo Ejecutar

1.  Connect to your **Oracle Database** instance. | *Conéctate a tu instancia de **Base de Datos Oracle**.*
2.  Open the desired `.sql` script in a worksheet. | *Abre el script `.sql` deseado en una hoja de trabajo.*
3.  Execute the script to compile the objects or run the anonymous blocks. | *Ejecuta el script para compilar los objetos o correr los bloques anónimos.*

---

> [!TIP]
> Use this collection as a technical reference for database automation and security audits.
> *Usa esta colección como referencia técnica para la automatización de bases de datos y auditorías de seguridad.*
