# Tutorial: Control de Volumen con Gestos de Mano

Este proyecto permite controlar el volumen de Windows mediante la cámara web y gestos de la mano, utilizando MediaPipe para la detección y MongoDB Atlas para el registro de datos.

## 🚀 Requisitos Previos

- Python 3.8 o superior.
- Una cámara web funcional.
- Una cuenta en [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
- Un cluster de MongoDB Atlas con una base de datos llamada `hand_tracking_db`.

## 🛠️ Instalación

1. Clona o descarga este repositorio.
2. Abre una terminal en la carpeta del proyecto.
3. Instala las dependencias:
   ```bash
   pip install -r requirements.txt
   ```

## ⚙️ Configuración

1. Renombra el archivo `.env.template` a `.env`.
2. Edita el archivo `.env` y añade tu URI de MongoDB Atlas:
   ```env
   MONGODB_URI=mongodb+srv://tu_usuario:tu_password@clusterX.mongodb.net/
   DATABASE_NAME=hand_tracking_db
   ```

## 🎮 Uso

1. Ejecuta la aplicación principal:
   ```bash
   python main.py
   ```
2. Aparecerá una ventana con el feed de la cámara.
3. **Control de Volumen:**
   - Usa los dedos **pulgar e índice** para ajustar el volumen (la distancia entre ellos determina el nivel).
   - El cambio **solo se aplica** si el **dedo meñique está bajado**. Si el meñique está levantado, el volumen no cambiará (útil para mover la mano sin afectar el audio).
4. **Cerrar:** Presiona la tecla `q` para salir.

## 📁 Estructura del Proyecto

- `main.py`: Controlador principal.
- `HandTrackingModule.py`: Lógica de detección de manos.
- `VolumeHandControl.py`: Mapeo de distancia a volumen del sistema.
- `dao/mongodb_dao.py`: Acceso a la base de datos (Patrón DAO y Singleton).
- `models/`: Clases de datos para Sesiones y Eventos.
- `config/settings.py`: Carga de variables de entorno.

## 📊 Datos en MongoDB

La aplicación registrará:
- **Sesiones:** Inicio, fin y duración de cada vez que uses la app.
- **Eventos de Volumen:** Cada ajuste realizado, incluyendo el volumen anterior, el nuevo y la distancia de los dedos.
