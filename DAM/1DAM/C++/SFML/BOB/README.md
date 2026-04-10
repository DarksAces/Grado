# 🎾 SFML Pong: 2D Multi-threaded Game Engine in C++
# 🎾 SFML Pong: Motor de Juego 2D Multihilo en C++

## 📋 Description | Descripción

A high-performance **2D Pong** implementation developed natively in **C++** using the **SFML** (Simple and Fast Multimedia Library). This project demonstrates advanced real-time game loop management, hardware-accelerated rendering, and specialized game physics.

Una implementación de **Pong 2D** de alto rendimiento desarrollada nativamente en **C++** usando **SFML**. Este proyecto demuestra la gestión avanzada del bucle de juego en tiempo real, renderizado acelerado por hardware y física de juego especializada.

---

## ✨ Key Features | Características Clave

- **Dynamic Game Loop:** Frame-rate independent movement using `deltaTime` to ensure smooth gameplay across different hardware. | *Bucle de Juego Dinámico: Movimiento independiente de los FPS usando `deltaTime` para asegurar fluidez en cualquier hardware.*
- **Advanced Game Mechanics:** Includes a random multiplier system (x2, x3, x5) that spawns dynamically and reacts to ball collisions. | *Mecánicas Avanzadas: Incluye un sistema de multiplicadores aleatorios que aparecen dinámicamente y reaccionan a las colisiones.*
- **AI Competitor:** Predictive paddle logic using timed AI updates to simulate a challenging opponent. | *Oponente IA: Lógica de pala predictiva que usa actualizaciones temporizadas para simular un oponente desafiante.*
- **SFML Integration:** Full use of SFML Graphics (Shapes, Fonts, Rects) and Audio (Collision sounds) modules. | *Integración SFML: Uso completo de los módulos Graphics y Audio de SFML.*
- **Real-time UI:** Reactive HUD (Heads-Up Display) showing score, multiplier status, and game time. | *UI en Tiempo Real: HUD reactivo que muestra puntuación, estado del multiplicador y tiempo de juego.*

---

## 🛠️ Tech Stack | Tecnologías

- **Environment:** Visual Studio (MSVC)
- **Language:** C++17
- **Multimedia Library:** SFML 2.5+
- **APIs:** GDI-based rendering (accelerated).

---

## 📂 Project Structure | Estructura del Proyecto

- **`main.cpp`**: The complete game engine, logic, and rendering orchestrator. | *El motor de juego completo, lógica y orquestador de renderizado.*
- **`/resources/`**: Integrated assets including fonts (`sansation.ttf`) and audio (`ball.wav`). | *Activos integrados incluyendo fuentes y audio.*
- **`BOB.sln`**: Visual Studio solution for rapid deployment. | *Solución de Visual Studio para despliegue rápido.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Ensure **SFML** is correctly configured in your Visual Studio environment (Include/Libraries paths). | *Asegúrate de que **SFML** esté bien configurado en Visual Studio.*
2.  Open `BOB.sln` and build in **Release** or **Debug** mode. | *Abre `BOB.sln` y compila.*
3.  Press **Space** to start the game. Use the **Up/Down** arrows to control your paddle. | *Presiona **Espacio** para empezar. Usa las flechas para controlar la pala.*
