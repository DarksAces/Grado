# 🍽️ Bar Ajardinados Virtual Assistant: AI-Powered Customer Support
# 🍽️ Asistente Virtual Bar Ajardinados: Soporte al Cliente con IA

## 📋 Description | Descripción

A desktop virtual assistant for the **Bar Ajardinados** restaurant. Built with **Python and Google Gemini AI**, this application provides customers with instant information about the menu, location, and reservations using a natural language interface.

Un asistente virtual de escritorio para el restaurante **Bar Ajardinados**. Desarrollado con **Python y la IA Google Gemini**, esta aplicación proporciona a los clientes información instantánea sobre el menú, la ubicación y las reservas mediante una interfaz de lenguaje natural.

---

## ✨ Key Features | Características Clave

- **AI Concierge:** Powered by `gemini-2.0-flash` to handle complex customer queries with professional, waiter-like behavior. | *Conserje de IA: Impulsado por `gemini-2.0-flash` para manejar consultas complejas de clientes con un comportamiento profesional similar al de un camarero.*
- **Custom Knowledge Base:** Uses `menu_ajardinados.txt` as a specialized context window for accurate information about specific dishes and policies. | *Base de conocimientos personalizada: Usa `menu_ajardinados.txt` como una ventana de contexto especializada para obtener información precisa sobre platos específicos y políticas.*
- **Intuitive GUI:** Built with **Tkinter**, featuring a clean chat interface with styled message bubbles and asynchronous API calls. | *GUI Intuitiva: Construida con **Tkinter**, con una interfaz de chat limpia con burbujas de mensaje estilizadas y llamadas a la API asíncronas.*
- **Multi-threading:** Ensures the UI remains responsive while the AI processes responses. | *Multi-hilo: Asegura que la interfaz siga respondiendo mientras la IA procesa las respuestas.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Python
- **AI Engine:** Google GenAI (Gemini)
- **Interface:** Tkinter (GUI)
- **Threading:** Python `threading` library
- **Environment:** Secure API Key management via environment variables.

---

## 📂 Project Structure | Estructura del Proyecto

- **`Restaurante.py`**: Main application code, UI logic, and API integration. | *Código principal de la aplicación, lógica de la interfaz e integración de la API.*
- **`menu_ajardinados.txt`**: The source of truth containing restaurant data (menu, hours, contact). | *La fuente de verdad que contiene los datos del restaurante (menú, horarios, contacto).*
- **`Requeriments.txt`**: Dependency list for easy environment setup. | *Lista de dependencias para una configuración fácil del entorno.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Set your Gemini API Key as an environment variable: `Gemini_api`. | *Configura tu clave de API de Gemini como una variable de entorno: `Gemini_api`.*
2.  Install dependencies: `pip install -r Requeriments.txt`. | *Instala las dependencias: `pip install -r Requeriments.txt`.*
3.  Run the application: `python Restaurante.py`. | *Ejecuta la aplicación: `python Restaurante.py`.*
