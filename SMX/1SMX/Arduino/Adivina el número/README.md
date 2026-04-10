# 🎲 LogicGuess: Arduino Interactive Serial Game
# 🎲 LogicGuess: Juego Interactivo de Serial con Arduino

## 📋 Description | Descripción

A creative **Arduino laboratory** developed in **1DAM** that implements an interactive **"Guess the Number"** game using Serial communication. **LogicGuess** generates a random hidden number and orchestrates a real-time feedback loop with the user through the Serial Monitor, mastering the integration of randomized algorithms, sequential conditional branching, and hardware-software user input.

Un laboratorio creativo de **Arduino** desarrollado en **1DAM** que implementa un juego interactivo de **"Adivina el número"** usando comunicación Serial. **LogicGuess** genera un número oculto aleatorio y orquesta un bucle de feedback en tiempo real con el usuario.

---

## ✨ Key Features | Características Clave

- **Dynamic Feedback Loop:** Real-time user interaction through the `Serial.available()` and `Serial.read()` orchestration. | *Bucle de Feedback Dinámico: Interacción en tiempo real por Serial.*
- **Randomized Logic Engine:** High-precision use of `random()` and `randomSeed()` for unpredictable game states. | *Motor de Lógica Aleatoria: Uso de funciones random.*
- **Interactive Decision Tree:** Implementation of complex `if-else` structures to provide directional hints (Higher/Lower). | *Árbol de Decisión Interactivo: Pistas direccionales (Más alto/Bajo).*
- **Clean Serial Interface:** Professional formatting of Serial outputs for a clear and engaging CLI-over-hardware experience. | *Interfaz Serial Limpia: Salidas formateadas.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** C++ (Arduino).
- **Protocol:** UART / Serial Communication.
- **Concepts:** Randomization, Conditional logic, I/O Buffers, Serial orchestration.

---

## 📂 Project Structure | Estructura del Proyecto

- **`sketch_may30b/sketch_may30b.ino`**: The core logical source for the interactive game. | *La fuente lógica core para el juego interactivo.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Upload the sketch to your **Arduino board**. | *Sube el sketch a tu placa Arduino.*
2.  Open the **Serial Monitor** in the Arduino IDE (ensure the baud rate matches the code). | *Abre el **Serial Monitor**.*
3.  Follow the on-screen instructions to guess the hidden number. | *Sigue las instrucciones en pantalla para adivinar el número.*
