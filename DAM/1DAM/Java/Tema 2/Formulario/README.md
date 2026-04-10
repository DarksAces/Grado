# 🖼️ Java Swing GUI: User Registration & CSV Export
# 🖼️ Java Swing GUI: Registro de Usuarios y Exportación CSV

## 📋 Description | Descripción

A desktop application developed with **Java Swing** that implements a professional User Registration Form. This project focuses on frontend-to-backend data flows, providing a rich graphical interface (GUI) with real-time feedback and persistent storage in locally managed **CSV** files.

Una aplicación de escritorio desarrollada con **Java Swing** que implementa un Formulario de Registro de Usuarios profesional. Este proyecto se centra en los flujos de datos de frontend a backend, proporcionando una interfaz gráfica enriquecida (GUI) con feedback en tiempo real y almacenamiento persistente en archivos **CSV** gestionados localmente.

---

## ✨ Key Features | Características Clave

- **Rich Swing Interface:** Comprehensive use of `JFrame`, `JPanel`, and specialized layout managers for a clean UI. | *Interfaz Swing Enriquecida: Uso exhaustivo de `JFrame` y gestores de diseño para una UI limpia.*
- **CSV Data Persistence:** Integrated logic to save and retrieve user records from a local `user_data_DGB.csv` file. | *Persistencia CSV: Lógica integrada para guardar y recuperar registros de usuarios.*
- **Input Neutralization:** Advanced handling of text fields, checkboxes, and radio buttons with proper state management. | *Neutralización de Entrada: Manejo avanzado de campos de texto, checkboxes y radio buttons.*
- **Modular Packaging:** Clean separation between the view layer (`view.gui`) and the helper classes for file management. | *Paquetizado Modular: Separación limpia entre la capa de vista y las clases de ayuda.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java
- **Framework:** Java Swing (AWT).
- **Format:** CSV (Comma Separated Values).
- **IDE Support:** Compatible with NetBeans GUI Builder.

---

## 📂 Project Structure | Estructura del Proyecto

- **`JFrameMain.java`**: The primary GUI container and event orchestrator. | *El contenedor principal de la GUI y orquestador de eventos.*
- **`ClassFichero.java`**: Specialized helper for CSV I/O operations. | *Ayudante especializado para operaciones de E/S de CSV.*
- **`user_data_DGB.csv`**: Local persistent storage for user profiles. | *Almacenamiento persistente local para perfiles de usuario.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Open the project in **NetBeans** or any Java IDE with Swing support. | *Abre el proyecto en NetBeans o cualquier IDE con soporte Swing.*
2.  Run the `JFrameMain.java` file. | *Ejecuta el archivo `JFrameMain.java`.*
3.  Fill in the form and click the registration button to see the data saved to the CSV. | *Rellena el formulario y haz clic en registrar para ver los datos en el CSV.*
