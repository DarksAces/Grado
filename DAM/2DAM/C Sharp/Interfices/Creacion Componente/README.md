# 🕰️ WinForms Digital Clock: Custom Component Design
# 🕰️ Reloj Digital WinForms: Diseño de Componente Personalizado

## 📋 Description | Descripción

A professional **C# Class Library** project that implements a reusable **Digital Clock UserControl**. This project focuses on advanced WinForms development concepts, including custom properties grouped in categories, event-driven architecture for alarms, and override-based rendering using GDI+ (`OnPaint`).

Un proyecto profesional de **Biblioteca de Clases C#** que implementa un **UserControl de Reloj Digital** reutilizable. Este proyecto se centra en conceptos avanzados de desarrollo WinForms, incluyendo propiedades personalizadas agrupadas en categorías, arquitectura orientada a eventos para alarmas y renderizado basado en sobrescritura usando GDI+ (`OnPaint`).

---

## ✨ Key Features | Características Clave

- **Custom Properties:** Integrated with the Visual Studio Properties Window using `[Category]` and `[Description]` attributes (e.g., `FormatoHora`, `ColorTexto`). | *Propiedades Personalizadas: Integradas con la Ventana de Propiedades de Visual Studio usando atributos (ej. `FormatoHora`, `ColorTexto`).*
- **Alarm System:** Implements a custom `AlarmaActivada` event that triggers when the system time matches the user-defined `HoraAlarma`. | *Sistema de Alarma: Implementa un evento manual `AlarmaActivada` que se dispara cuando la hora del sistema coincide con la definida por el usuario.*
- **Custom Rendering:** Uses high-performance rendering with `OptimizedDoubleBuffer` and manual `Graphics.DrawString` calls for a sharp visual output. | *Renderizado Personalizado: Usa renderizado de alto rendimiento con doble búfer y llamadas manuales GDI+ para una salida visual nítida.*
- **Library Design:** Built as a standalone `.dll` (MisComponentesLib) to be easily imported into other WinForms projects. | *Diseño de Biblioteca: Construido como un `.dll` independiente para ser importado fácilmente en otros proyectos.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** C#
- **Framework:** .NET Framework / WinForms
- **Graphics API:** GDI+ (`System.Drawing`)
- **Metadata:** System.ComponentModel

---

## 📂 Project Structure | Estructura del Proyecto

- **`MisComponentesLib/RelojDigital.cs`**: The core component logic and properties. | *La lógica y propiedades centrales del componente.*
- **`TestRelojDigital/`**: A dedicated testing application to showcase the component in action. | *Una aplicación de prueba dedicada para mostrar el componente en acción.*
- **`RA3_RelojDigital.sln`**: The complete Visual Studio solution. | *La solución completa de Visual Studio.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Open `RA3_RelojDigital.sln` in **Visual Studio**. | *Abre `RA3_RelojDigital.sln` en **Visual Studio**.*
2.  Build the solution to generate the `MisComponentesLib.dll`. | *Compila la solución para generar el `.dll`.*
3.  Run the **TestRelojDigital** project to see the clock and test the alarm functionality. | *Ejecuta el proyecto **TestRelojDigital** para ver el reloj y probar la alarma.*
