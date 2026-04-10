# ⚔️ PyQuest: JSON-Driven RPG Engine
# ⚔️ PyQuest: Motor RPG Basado en JSON

## 📋 Description | Descripción

A sophisticated **Python RPG (Role-Playing Game)** developed in **1DAM** that uses a data-driven architecture. **PyQuest** decouples game logic from content by utilizing external **JSON** files to define enemies, events, items, and character attributes. It features dynamic event handling, turn-based combat, and a persistent inventory system.

Un sofisticado **RPG (Juego de Rol) en Python** desarrollado en **1DAM** que utiliza una arquitectura basada en datos. **PyQuest** desacopla la lógica del juego del contenido utilizando archivos **JSON** externos para definir enemigos, eventos, objetos y atributos.

---

## ✨ Key Features | Características Clave

- **Data-Driven Ecosystem:** All game content (Enemies, Shop, Events) is stored in specialized JSON files for easy modding and scaling. | *Ecosistema Basado en Datos: Todo el contenido se almacena en archivos JSON.*
- **Dynamic Event Engine:** Randomly generated encounters and scenarios using `eventos.json`. | *Motor de Eventos Dinámico: Encuentros y escenarios generados aleatoriamente.*
- **Persistent Stats & Inventory:** Real-time tracking of player progression and item storage via `personajes.json` and `inventario.json`. | *Estadísticas e Inventario Persistentes: Seguimiento en tiempo real.*
- **Complex Combat Logic:** Implementation of damage calculation, critical hits, and level-up systems within `Juego.py`. | *Lógica de Combate Compleja: Implementación de cálculo de daño y críticos.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Python 3.9+.
- **Data Format:** JSON (Static DB).
- **Core Library:** `json`, `random`, `time`.
- **Patterns:** Data-Driven Design, State Machine (Combat).

---

## 📂 Project Structure | Estructura del Proyecto

- **`Juego.py`**: The main game loop and logic orchestrator. | *El bucle principal y orquestador de lógica.*
- **`enemigos.json`**: Definition of monster stats, abilities, and loot. | *Definición de estadísticas de monstruos.*
- **`eventos.json`**: Scripts for narrative encounters and world interactions. | *Scripts para encuentros narrativos.*
- **`tienda.json`**: Product catalog and pricing for the in-game economy. | *Catálogo de productos y precios.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Ensure all `.json` files are in the same directory as `Juego.py`. | *Asegúrate de que los archivos .json estén en el mismo directorio.*
2.  Launch the game: `python Juego.py`. | *Lanza el juego.*
3.  Embark on your quest, manage your inventory, and survive the trials of the JSON world! | *Embárcate en tu aventura y sobrevive.*
