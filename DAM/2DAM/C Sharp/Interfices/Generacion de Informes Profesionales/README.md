# 📊 ReportMaster: Professional C# Reporting System (WinForms)
# 📊 ReportMaster: Sistema de Generación de Informes Profesionales (WinForms)

## 📋 Description | Descripción

A robust desktop application developed in **2DAM** using **C#** and **Windows Forms**. **ReportMaster** is designed for high-precision data visualization and reporting. It integrates a local database (MySQL/SQL Server) with a professional reporting engine to generate structured document summaries, financial reports, and data audits, demonstrating advanced mastery of the .NET ecosystem.

Una aplicación de escritorio robusta desarrollada en **2DAM** usando **C#** y **Windows Forms**. **ReportMaster** está diseñado para la visualización de datos de alta precisión y la generación de informes. Integra una base de datos local con un motor de informes profesional para generar resúmenes de documentos, informes financieros y auditorías de datos.

---

## ✨ Key Features | Características Clave

- **Dynamic Reporting Engine:** Specialized `/Reports/` module for designing and rendering professional document templates. | *Motor de Informes Dinámico: Módulo especializado para el diseño de plantillas.*
- **Database Orchestration:** Includes `setup_db.sql` for automated schema initialization and sample data ingestion. | *Orquestación de Base de Datos: Incluye scripts SQL para inicialización automatizada.*
- **Modern WinForms UI:** High-performance dashboard (`Form1.cs`) featuring advanced data grids and interactive controls. | *UI WinForms Moderna: Dashboard de alto rendimiento.*
- **Data Export capabilities:** Logic specialized for transforming internal datasets into printable or digital report formats. | *Capacidades de Exportación: Lógica para transformar datos en informes.*

---

## 🛠️ Tech Stack | Tecnologías

- **Environment:** Visual Studio 2022 (.NET SDK).
- **Language:** C# (Sharp).
- **Framework:** Windows Forms (WinForms).
- **Database:** SQL (Compatible with MySQL/SQL Server).
- **Tools:** Reporting Services / Crystal Reports (logic).

---

## 📂 Project Structure | Estructura del Proyecto

- **`Form1.cs`**: The primary dashboard and reporting orchestrator. | *El dashboard principal y orquestador.*
- **`/Reports/`**: Directory containing specialized report definitions and templates. | *Directorio con definiciones de informes.*
- **`setup_db.sql`**: Database schema and initial data scripts. | *Esquema de BD y datos iniciales.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Open the `.sln` solution file in **Visual Studio**. | *Abre el archivo de solución en **Visual Studio**.*
2.  Execute the `setup_db.sql` script in your local SQL environment. | *Ejecuta el script SQL en tu base de datos.*
3.  Configure the connection string in the application (Data folder). | *Configura la cadena de conexión.*
4.  Build and Run (Press **F5**). | *Compila y ejecuta.*
