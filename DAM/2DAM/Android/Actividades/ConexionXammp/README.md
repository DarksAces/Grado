# 📱 Android Connect: XAMPP Server Integration
# 📱 Android Connect: Integración con Servidor XAMPP

## 📋 Description | Descripción

A practical **Android Native** application that demonstrates the integration between a mobile client and a local development server (**XAMPP**). It covers the full network lifecycle, including executing asynchronous HTTP requests, handling JSON responses, and managing connectivity errors in a local network environment.

Una aplicación práctica de **Android Nativo** que demuestra la integración entre un cliente móvil y un servidor de desarrollo local (**XAMPP**). Cubre todo el ciclo de vida de red, incluyendo la ejecución de peticiones HTTP asíncronas, el manejo de respuestas JSON y la gestión de errores de conectividad en un entorno de red local.

---

## ✨ Key Features | Características Clave

- **Network Middleware:** Use of native libraries or specialized HTTP clients to communicate with PHP scripts on the server. | *Middleware de Red: Uso de librerías nativas para comunicarse con scripts PHP en el servidor.*
- **Async Execution:** Heavy network tasks are offloaded from the main UI thread to ensure a smooth user experience. | *Ejecución Asíncrona: Las tareas pesadas de red se delegan fuera del hilo principal para asegurar fluidez.*
- **Backend Sync:** Automated data fetching from MySQL databases mediated by a PHP bridge. | *Sincronización de Backend: Obtención automatizada de datos desde MySQL mediada por un puente PHP.*
- **Dynamic UI:** Real-time interface updates based on the data retrieved from the remote server. | *UI Dinámica: Actualizaciones de la interfaz en tiempo real basadas en los datos obtenidos del servidor.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java
- **Platform:** Android SDK (API 24+)
- **Server:** XAMPP (Apache + PHP + MySQL)
- **Communication:** HTTP / JSON.

---

## 📂 Project Structure | Estructura del Proyecto

- **`MainActivity.java`**: Main engine for network requests and UI orchestration. | *Motor principal para peticiones de red y orquestación de UI.*
- **`NuevaActividad.java`**: Secondary view for detailed data presentation or input. | *Vista secundaria para presentación de datos detallados o entrada.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Start your **XAMPP** server and ensure your computer and Android device/emulator are on the same network. | *Inicia XAMPP y asegúrate de que el dispositivo y el PC estén en la misma red.*
2.  Deploy the PHP bridge files (if available) to the `htdocs` folder. | *Despliega los archivos PHP en la carpeta `htdocs`.*
3.  Open the project in **Android Studio** and update the target IP in the code. | *Abre en **Android Studio** y actualiza la IP en el código.*
4.  Launch the application and test the server connectivity. | *Lanza la aplicación y prueba la conectividad.*
