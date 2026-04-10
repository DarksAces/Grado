# 🎮 StarStorm: High-Performance Android Arcader
# 🎮 StarStorm: Arcade de Alto Rendimiento para Android

## 📋 Description | Descripción

A specialized **Android Native** game engine developed in **2DAM** focusing on real-time rendering and complex entity management. **StarStorm** demonstrates the use of a custom **SurfaceView** loop to achieve smooth animations, collision detection, and a hierarchical entity system (Players, Enemies, Bullets, Explosions) for an immersive arcade experience.

Un motor de juego especializado en **Android Nativo** desarrollado en **2DAM** enfocado en renderizado en tiempo real y gestión de entidades compleja. **StarStorm** demuestra el uso de un **SurfaceView** personalizado para lograr animaciones fluidas, detección de colisiones y un sistema de entidades jerárquico.

---

## ✨ Key Features | Características Clave

- **Real-Time Rendering Engine:** Custom `GameView.java` (10KB+) implementing the Game Loop pattern for synchronized frame updates and drawing. | *Motor de Renderizado en Tiempo Real: GameView personalizado implementando el patrón Game Loop.*
- **Hierarchical Entity System:** Clean Object-Oriented architecture using an abstract `Entity.java` class to orchestrate bullets, enemies, and player logic. | *Sistema de Entidades Jerárquico: Arquitectura POO limpia.*
- **Particle & Effect Logic:** Integrated explosion mechanics and visual feedback using specialized classes (`Explosion.java`). | *Lógica de Partículas y Efectos: Mecánicas de explosión integradas.*
- **Persistent High Scores:** Automated score tracking using `HighScoreManager.java` for competitive local gameplay. | *Puntuaciones Altas Persistentes: Seguimiento de puntuación automatizado.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 8+.
- **Graphics API:** Canvas / SurfaceView (Native Android UI).
- **Patterns:** State Pattern, Composite (Entities), Game Loop.
- **Concepts:** Collision Detection, Frame independence, Threading (Game thread).

---

## 📂 Project Structure | Estructura del Proyecto

- **`GameView.java`**: The core rendering and logic engine. | *El motor de renderizado y lógica central.*
- **`Player.java` / `Enemy.java` / `Bullet.java`**: Specialized game entities. | *Entidades de juego especializadas.*
- **`GameOverActivity.java`**: Integrated session termination and score reporting. | *Finalización de sesión integrada.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Import the project into **Android Studio**. | *Importa el proyecto en **Android Studio**.*
2.  Deploy to an AVD or physical Android device (**Shift+F10**). | *Despliega en un emulador o dispositivo.*
3.  Tap the screen to control your ship and survive the onslaught! | *¡Toca la pantalla para controlar tu nave y sobrevivir!*
