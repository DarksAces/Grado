# 🎮 TetriCore: Arduino-Powered Portable Retro Game
# 🎮 TetriCore: Juego Retro Portátil Potenciado por Arduino

## 📋 Description | Descripción

A high-impact **Arduino Game Development** laboratory developed in **1DAM** that implements a functional **Tetris** logic for micro-controllers. **TetriCore** masters the orchestration of low-level graphical rendering (for LCD/OLED displays) and synchronized input handling. This project represents the pinnacle of 1DAM Arduino track, demonstrating expertise in game loops, piece rotation algorithms, and collision detection within constrained hardware environments.

Un laboratorio de **Desarrollo de Juegos con Arduino** de alto impacto desarrollado en **1DAM** que implementa una lógica de **Tetris** funcional para microcontroladores. **TetriCore** domina la orquestación del renderizado de bajo nivel y el manejo sincronizado de entradas.

---

## ✨ Key Features | Características Clave

- **Advanced Matrix Orchestration:** High-precision management of the game grid and falling piece logical states. | *Orquestación de Matriz Avanzada: Gestión de la rejilla de juego.*
- **Real-time Game Loop:** Robust implementation of the battle sequence, randomized piece generation, and gravity logic. | *Bucle de Juego en Tiempo Real: Implementación de la secuencia de batalla.*
- **Piece Physics & Rotation:** C++ algorithms for 90-degree rotations and multi-axis collision detection. | *Física y Rotación de Piezas: Algoritmos para rotaciones de 90 grados.*
- **Custom Hardware Integration:** Orchestration between the Arduino logic and specialized output displays (LCD/OLED). | *Integración de Hardware: Orquestación con pantallas (LCD/OLED).*

---

## 🛠️ Tech Stack | Tecnologías

- **Platform:** Arduino.
- **Language:** C++ (Arduino Sketch).
- **Libraries:** LiquidCrystal / Adafruit_GFX (depending on display).
- **Concepts:** Game Loops, Collision Physics, Pieces rotation, Buffering.

---

## 📂 Project Structure | Estructura del Proyecto

- **`Tetris/Tetris.ino`**: The primary game engine and rendering source. | *El motor de juego principal y fuente de renderizado.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Wired your display and buttons correctly to the Arduino pins specified in the sketch. | *Cablea la pantalla y botones correctamente.*
2.  Upload `Tetris.ino` to the board. | *Sube `Tetris.ino` a la placa.*
3.  Launch the Serial Monitor (if debug is enabled) or start playing directly on the screen. | *Empieza a jugar directamente en la pantalla.*
