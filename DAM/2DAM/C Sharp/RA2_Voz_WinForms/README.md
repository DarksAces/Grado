# 🎙️ Voice Commander: WinForms Speech Recognition
# 🎙️ Comandante por Voz: Reconocimiento de Voz WinForms

## 📋 Description | Descripción

An advanced **C# Desktop application** that leverages the `System.Speech` library to control the operating system and the application itself through voice commands. It supports both command-driven interactions and free-form dictation.

Una aplicación de escritorio avanzada en **C#** que aprovecha la librería `System.Speech` para controlar el sistema operativo y la propia aplicación mediante comandos de voz. Soporta tanto interacciones basadas en comandos como dictado libre.

---

## ✨ Key Features | Características Clave

- **Smart Command System:** Over 40 built-in commands to open apps (Chrome, Calculator, Paint, Teams), control windows (minimize/maximize), and change UI colors. | *Sistema de comandos inteligentes: Más de 40 comandos integrados para abrir aplicaciones, controlar ventanas y cambiar colores de la interfaz.*
- **Dual Modes:** Switch dynamically between **Command Mode** (predefined choices) and **Dictation Mode** (free text writing). | *Modos duales: Cambia dinámicamente entre el **Modo Comando** (opciones predefinidas) y el **Modo Dictado** (escritura de texto libre).*
- **OS Integration:** Uses `SendKeys` to simulate keystrokes, allowing voice-controlled copy, paste, undo, and search across Windows. | *Integración con el SO: Usa `SendKeys` para simular pulsaciones de teclas, permitiendo copiar, pegar, deshacer y buscar en Windows mediante voz.*
- **Confidence Scoring:** Real-time feedback on recognition confidence to ensure high accuracy. | *Puntuación de confianza: Información en tiempo real sobre la confianza del reconocimiento para asegurar una alta precisión.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** C#
- **Framework:** .NET Framework / WinForms
- **Library:** `System.Speech.Recognition`
- **Automation:** Windows `SendKeys` & `Process.Start`

---

## 📂 Project Structure | Estructura del Proyecto

- **`Form1.cs`**: The heart of the application, managing the speech engine, grammar loading, and command execution logic. | *El núcleo de la aplicación, gestionando el motor de voz, la carga de gramáticas y la lógica de ejecución de comandos.*
- **`0488_Practica5_Reconocimiento_Voz_Confluence.txt`**: Technical documentation and requirements for the implementation. | *Documentación técnica y requisitos para la implementación.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Connect a microphone to your PC. | *Conecta un micrófono a tu PC.*
2.  Open the project in **Visual Studio**. | *Abre el proyecto en **Visual Studio**.*
3.  Ensure the **Windows Speech Recognition** feature is enabled in your OS settings. | *Asegúrate de que la función de **Reconocimiento de Voz de Windows** esté activada en la configuración del SO.*
4.  Run the app and click **"Start"** to begin listening. | *Ejecuta la app y haz clic en **"Start"** para empezar a escuchar.*
