# ✋ Volume Hand Control: AI-Powered Computer Vision
# ✋ Control de Volumen Manual: Visión Artificial con IA

## 📋 Description | Descripción

An innovative Computer Vision project that allows users to control their system's volume using hand gestures through a webcam. It leverages **MediaPipe** for high-fidelity hand tracking and **OpenCV** for real-time image processing.

Un proyecto innovador de Visión Artificial que permite a los usuarios controlar el volumen de su sistema mediante gestos manuales a través de una cámara web. Aprovecha **MediaPipe** para el seguimiento de manos de alta fidelidad y **OpenCV** para el procesamiento de imágenes en tiempo real.

---

## ✨ Key Features | Características Clave

- **Real-time Hand Tracking:** Uses a specialized `HandTrackingModule` to detect 21 hand landmarks with low latency. | *Seguimiento de manos en tiempo real: Usa un `HandTrackingModule` especializado para detectar 21 puntos de referencia de la mano con baja latencia.*
- **Gesture Recognition:** Calculates the distance between the thumb and index finger to map physical movement to a precise volume level. | *Reconocimiento de gestos: Calcula la distancia entre el pulgar y el índice para mapear el movimiento físico a un nivel de volumen preciso.*
- **Visual Overlay:** Real-time feedback showing the detected hand skeleton and a reactive volume bar on the video feed. | *Superposición visual: Retroalimentación en tiempo real que muestra el esqueleto de la mano detectado y una barra de volumen reactiva en el video.*
- **Persistence Layer:** Integrated with **PostgreSQL** to log usage statistics and session configurations. | *Capa de Persistencia: Integrado con **PostgreSQL** para registrar estadísticas de uso y configuraciones de sesión.*

---

## 🛠️ Tech Stack | Tecnologías

- **Environment:** Python 3.9+
- **AI/ML Libraries:** MediaPipe (Hand Tracking)
- **Computer Vision:** OpenCV
- **Audio Control:** `PyCaw` (Python Computer Audio Utilities)
- **Database:** PostgreSQL

---

## 📂 Project Structure | Estructura del Proyecto

- **`main.py`**: The entry point that orchestrates hand tracking and system control. | *El punto de entrada que orquesta el seguimiento y el control del sistema.*
- **`HandTrackingModule.py`**: Encapsulated logic for MediaPipe hand detection. | *Lógica encapsulada para la detección de manos con MediaPipe.*
- **`VolumeHandControl.py`**: Specialized logic for distance-to-volume mapping. | *Lógica especializada para el mapeo de distancia a volumen.*
- **`/dao/` & `/models/`**: Database access and data structuring. | *Acceso a BD y estructuración de datos.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Install dependencies: `pip install -r requirements.txt`. | *Instala las dependencias: `pip install -r requirements.txt`.*
2.  Ensure your webcam is connected and recognized by the OS. | *Asegúrate de que tu cámara web esté conectada y sea reconocida por el SO.*
3.  Run the application: `python main.py`. | *Ejecuta la aplicación: `python main.py`.*
4.  Pinch your thumb and index finger to adjust the volume! | *¡Junta el pulgar y el índice para ajustar el volumen!*
