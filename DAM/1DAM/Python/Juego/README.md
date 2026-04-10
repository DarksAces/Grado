# ⚔️ Python RPG: Adventure & State Persistence
# ⚔️ RPG en Python: Aventura y Persistencia de Estado

## 📋 Description | Descripción

A classic text-based **RPG (Role-Playing Game)** developed in **Python**. This project demonstrates advanced use of JSON-based NoSQL persistence to manage game states, character attributes, inventory systems, and dynamic enemy encounters.

Un **RPG (Juego de Rol)** clásico basado en texto desarrollado en **Python**. Este proyecto demuestra el uso avanzado de persistencia NoSQL basada en JSON para gestionar estados de juego, atributos de personajes, sistemas de inventario y encuentros dinámicos con enemigos.

---

## ✨ Key Features | Características Clave

- **Dynamic Inventory System:** Specialized logic to handle item acquisition, storage, and usage through `inventario.json`. | *Sistema de Inventario Dinámico: Lógica para gestionar adquisición y uso de objetos.*
- **NoSQL State Management:** Uses multiple JSON files (`enemigos.json`, `progreso.json`) as a lightweight database for character data and progress. | *Gestión de Estado NoSQL: Usa archivos JSON como base de datos ligera para datos de personajes.*
- **Game Progression:** An integrated system to save and load sessions, ensuring a continuous user experience. | *Progresión del Juego: Sistema integrado para guardar y cargar sesiones.*
- **Combat & Trade Logic:** Robust game engine implementing turn-based combat and a specialized store (`tienda.json`) for equipment upgrades. | *Lógica de Combate y Comercio: Motor de juego que implementa combate por turnos y una tienda especializada.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Python 3.9+
- **Persistence:** JSON (JavaScript Object Notation).
- **Concepts:** Procedural programming, file I/O, game state management.

---

## 📂 Project Structure | Estructura del Proyecto

- **`juego.py`**: The core game engine and user interaction loop. | *El motor de juego central y bucle de interacción de usuario.*
- **`personajes.json`**: Definition of hero classes and base statistics. | *Definición de clases de héroes y estadísticas base.*
- **`tienda.json`**: Catalog of items available for purchase with in-game currency. | *Catálogo de objetos disponibles para su compra.*
- **`progreso.json`**: The active save-file for persistent gameplay. | *El archivo de guardado activo para la jugabilidad persistente.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Open a terminal in the project directory. | *Abre una terminal en el directorio del proyecto.*
2.  Run the game: `python juego.py`. | *Ejecuta el juego: `python juego.py`.*
3.  Follow the on-screen prompts to explore the adventure! | *¡Sigue las instrucciones en pantalla para explorar la aventura!*
