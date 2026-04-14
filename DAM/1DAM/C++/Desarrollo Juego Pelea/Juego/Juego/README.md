# ⚔️ RPG Combat Simulator | Simulador de Combate RPG

## 📋 Description | Descripción
This project is a terminal-based combat simulator developed in C++. It features a turn-based encounter where the player takes the role of a hero defending a city against an enemy. Through a simple command-line interface, players must make strategic decisions to manage health points and defeat their opponent.

Este proyecto es un simulador de combate basado en terminal desarrollado en C++. Presenta un encuentro por turnos donde el jugador asume el papel de un héroe que defiende una ciudad contra un enemigo. A través de una interfaz de línea de comandos sencilla, los jugadores deben tomar decisiones estratégicas para gestionar los puntos de vida y derrotar a su oponente.

## ✨ Key Features | Características Clave
*   **Turn-Based Logic:** Interactive combat system based on user input. | **Lógica por Turnos:** Sistema de combate interactivo basado en la entrada del usuario.
*   **Health System:** Real-time tracking of hit points for both the hero and the enemy. | **Sistema de Vida:** Seguimiento en tiempo real de los puntos de vida tanto del héroe como del enemigo.
*   **Action Commands:** Multiple gameplay options including "Attack" and "Dodge". | **Comandos de Acción:** Múltiples opciones de juego que incluyen "Atacar" y "Esquivar".
*   **Input Validation:** Handling of different text casings (uppercase/lowercase) for a smoother experience. | **Validación de Entrada:** Manejo de diferentes formatos de texto (mayúsculas/minúsculas) para una experiencia más fluida.
*   **Win/Loss Conditions:** Unique outcomes based on the battle's result. | **Condiciones de Victoria/Derrota:** Resultados únicos basados en el desenlace de la batalla.

## 🛠️ Tech Stack | Tecnologías
*   **Language:** C++
*   **IDE/Compiler:** Visual Studio (MSBuild)
*   **Standard Library:** iostream

## 📂 Project Structure | Estructura del Proyecto
*   `Juego.cpp`: Main source code containing the game logic and "main" function. | Código fuente principal que contiene la lógica del juego y la función "main".
*   `Juego.sln`: Visual Studio solution file. | Archivo de solución de Visual Studio.
*   `Juego.vcxproj`: Project configuration file for C++ development. | Archivo de configuración del proyecto para el desarrollo en C++.
*   `Juego.vcxproj.filters`: Organization of project files in the IDE. | Organización de los archivos del proyecto en el IDE.

## 🚀 How to Run | Cómo Ejecutar

### English
1.  **Requirement:** Have a C++ compiler installed (like GCC) or Visual Studio.
2.  **Via Visual Studio:** 
    *   Open the `Juego.sln` file.
    *   Press `Ctrl + F5` to run the program without debugging.
3.  **Via Terminal (GCC):**
    ```bash
    g++ Juego.cpp -o Juego
    ./Juego
    ```

### Español
1.  **Requisito:** Tener instalado un compilador de C++ (como GCC) o Visual Studio.
2.  **Vía Visual Studio:** 
    *   Abra el archivo `Juego.sln`.
    *   Presione `Ctrl + F5` para ejecutar el programa sin depurar.
3.  **Vía Terminal (GCC):**
    ```bash
    g++ Juego.cpp -o Juego
    ./Juego

<!-- AI-GENERATED-README -->