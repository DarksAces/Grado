# 🧮 MathEngine: OO-C# Calculator Framework (Entrega 1)
# 🧮 MathEngine: Framework de Calculadora C# Orientado a Objetos (Entrega 1)

## 📋 Description | Descripción

A sophisticated **C# Console Application** developed as a core laboratory for **2DAM (Entrega 1)**. **MathEngine** demonstrates advanced **Object-Oriented Programming (OOP)** principles by decoupling mathematical operations into specialized, inherited classes. Instead of a monolithic block, each operation (Sum, Square Root, Power, etc.) is its own entity, providing an industrial-grade example of the **Strategy** and **Factory** patterns in a CLI environment.

Una sofisticada **Aplicación de Consola en C#** desarrollada como laboratorio central para **2DAM (Entrega 1)**. **MathEngine** demuestra principios avanzados de **Programación Orientada a Objetos (POO)** al desacoplar las operaciones matemáticas en clases heredadas especializadas.

---

## ✨ Key Features | Características Clave

- **Granular Class Architecture:** Every operation is encapsulated in its own `.cs` file (`Suma`, `Resta`, `RaizCuadrada`, `Potencia`), ensuring maximum maintainability. | *Arquitectura de Clases Granular: Cada operación está encapsulada en su propia clase.*
- **Advanced Win-condition Logic:** Handling of complex floating-point exceptions and domain-specific errors (e.g., negative square roots). | *Lógica Avanzada: Manejo de excepciones de punto flotante.*
- **Modular Extension Support:** Easy to add new operations by implementing the base `Operacion.cs` interface/class. | *Soporte de Extensión Modular: Fácil de añadir nuevas operaciones.*
- **Interactive Multi-Op Shell:** A clean CLI wrapper that orchestrates the different operation objects throughout a session. | *Shell Multi-Op Interactiva.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** C# 10+.
- **Framework:** .NET Core / .NET SDK.
- **Patterns:** Inheritance, Polymorphism, Strategy.
- **Concepts:** Mathematical modeling, Class hierarchies, CLI interaction.

---

## 📂 Project Structure | Estructura del Proyecto

- **`Operacion.cs`**: The abstract base class/interface defining the contract for all math operations. | *La clase base abstracta.*
- **`Suma.cs` / `RaizCuadrada.cs` / `Potencia.cs`**: Specialized operation implementations. | *Implementaciones de operaciones especializadas.*
- **`Program.cs`**: The main orchestrator and user interface logic. | *El orquestador principal.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Open the solution in **Visual Studio**. | *Abre la solución en **Visual Studio**.*
2.  Press **F5** to compile and launch. | *Presiona **F5** para lanzar.*
3.  Select an operation from the menu and observe how the specialized objects handle your input. | *Selecciona una operación y observa cómo los objetos manejan la entrada.*
