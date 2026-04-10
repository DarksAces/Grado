# 🎵 SpotiClone: Native Android Music Player
# 🎵 SpotiClone: Reproductor Musical Nativo para Android

## 📋 Description | Descripción

A high-fidelity **Android Native** application developed in **2DAM** that simulates a professional music streaming interface. This project focuses on managing complex media lifecycles, background audio playback, and dynamic list rendering using the **RecyclerView** component. it provides a robust exploration of Intent orchestration and multi-activity synchronization.

Una aplicación **Android Nativa** de alta fidelidad desarrollada en **2DAM** que simula una interfaz de streaming de música profesional. Este proyecto se centra en la gestión de ciclos de vida de medios complejos, reproducción de audio en segundo plano y renderizado dinámico de listas usando **RecyclerView**.

---

## ✨ Key Features | Características Clave

- **Multi-Activity Player:** Seamless transition between the main song library (`MainActivity`) and the detailed playback view (`PlayerActivity`). | *Reproductor Multi-Actividad: Transición fluida entre la biblioteca y la vista de reproducción.*
- **Custom Adapters:** Advanced implementation of `SongAdapter` to handle large datasets of musical tracks with high performance. | *Adapters Personalizados: Implementación avanzada de SongAdapter para manejar grandes conjuntos de datos.*
- **Dynamic Media Control:** Integrated logic for play, pause, skip, and seek operations using native Android media libraries. | *Control de Medios Dinámico: Lógica integrada para reproducir, pausar y saltar pistas.*
- **Object-Oriented Data Model:** Uses a specialized `Song.java` class for clean representation of musical metadata. | *Modelo de Datos POO: Usa una clase Song para la representación de metadatos.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 8+ / Kotlin (hybrid support).
- **Platform:** Android SDK.
- **UI Components:** RecyclerView, ConstraintLayout, FloatingActionButton.
- **Concepts:** Intent-based communication, Adapter pattern, Media Lifecycle.

---

## 📂 Project Structure | Estructura del Proyecto

- **`MainActivity.java`**: The primary dashboard and song selection engine. | *El dashboard principal y motor de selección.*
- **`PlayerActivity.java`**: Comprehensive playback controller with interactive waveforms/controls. | *Controlador de reproducción exhaustivo.*
- **`SongAdapter.java`**: The bridge between the raw musical data and the user interface. | *El puente entre los datos musicales y la interfaz.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Open the project in **Android Studio**. | *Abre el proyecto en **Android Studio**.*
2.  Ensure you have at least one Android emulator or physical device connected. | *Asegúrate de tener un emulador o dispositivo.*
3.  Deploy the application: Press **Shift+F10**. | *Despliega la aplicación.*
4.  Interact with the song list to start the immersive playback experience. | *Interactúa con la lista de canciones para iniciar la reproducción.*
