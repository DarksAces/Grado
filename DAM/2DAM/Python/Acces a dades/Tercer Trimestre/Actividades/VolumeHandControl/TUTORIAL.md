# Tutorial: Control de Volumen con Detección de Manos y MongoDB

Este proyecto permite controlar el volumen de Windows mediante gestos de la mano y registrar la actividad en una base de datos MongoDB Atlas.

## Requisitos Previos

1. **Python 3.10+** instalado.
2. **Cámara web** funcional.
3. **Cuenta en MongoDB Atlas**.
4. Instalar las dependencias:
   ```bash
   pip install -r requirements.txt
   ```

## Configuración de la Base de Datos

1. Crea un Cluster gratuito en [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
2. Crea una base de datos llamada `hand_tracking_db`.
3. Obtén tu cadena de conexión (URI) y asegúrate de permitir el acceso desde tu dirección IP.
4. Crea un archivo `.env` en la raíz del proyecto con el siguiente formato:
   ```env
   MONGODB_URI=tu_uri_de_mongodb_atlas
   DATABASE_NAME=hand_tracking_db
   ```

## Estructura del Proyecto (MVC + DAO)

- **`main.py`**: El Controlador. Gestiona el bucle principal, la cámara y la lógica de negocio.
- **`HandTrackingModule.py`**: El Modelo/Módulo de detección. Encapsula MediaPipe para detectar puntos de la mano.
- **`VolumeHandControl.py`**: Lógica de mapeo. Convierte la distancia entre dedos en niveles de volumen.
- **`dao/mongodb_dao.py`**: Capa de acceso a datos. Implementa el patrón Singleton y DAO para MongoDB.
- **`models/`**: Definición de objetos `Session` y `VolumeEvent`.

## Cómo Usar

1. Ejecuta el script principal:
   ```bash
   python main.py
   ```
2. **Control de Volumen**: Junta o separa los dedos pulgar e índice.
3. **Activar/Desactivar**: El volumen solo cambiará si el dedo meñique está **bajado**. Si levantas el meñique, el ajuste se detiene (gesto de seguridad).
4. **Salir**: Presiona la tecla `q` para cerrar la aplicación y finalizar la sesión en la DB.

## Indicadores Visuales

- **Barra de volumen**: Muestra el nivel actual en el lateral izquierdo.
- **DB OK / DB --**: Indica si la conexión con MongoDB Atlas está activa.
- **FPS**: Muestra el rendimiento en tiempo real.
