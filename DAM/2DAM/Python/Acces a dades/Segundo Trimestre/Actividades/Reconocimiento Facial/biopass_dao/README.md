# BioPass DAO

Sistema de control de accesos biométrico utilizando Python, PostgreSQL y OpenCV.
Este proyecto implementa los patrones de diseño **DAO** (Data Access Object) y **Singleton**.

## Requisitos Previos

1.  **PostgreSQL**: Debe estar instalado y ejecutándose.
2.  **Base de Datos**: Crea una base de datos llamada `biopass_db` (o el nombre que prefieras).
3.  **Python 3.9+**.

## Configuración

1.  **Entorno Virtual (Opcional pero recomendado)**:
    ```bash
    python -m venv venv
    .\venv\Scripts\activate
    ```

2.  **Variables de Entorno**:
    - Abre el archivo `.env` en esta carpeta.
    - Modifica `DB_PASSWORD` con tu contraseña de PostgreSQL.
    - Asegúrate de que `DB_USER` y `DB_NAME` sean correctos.

3.  **Instalar Dependencias**:
    ```bash
    pip install -r requirements.txt
    ```

4.  **Inicializar Base de Datos**:
    Ejecuta este script para crear las tablas necesarias en PostgreSQL:
    ```bash
    python -m src.init_db
    ```

## Ejecución

Para iniciar la aplicación principal:

```bash
python -m src.biopass_app
```

## Arquitectura

-   **DAO (`src/usuario_dao.py`)**: Maneja todas las consultas SQL.
-   **Singleton (`src/conexion_db.py`)**: Asegura una única conexión a la base de datos.
-   **Config (`src/config.py`)**: Carga variables de entorno de forma segura.
-   **Utils (`src/utils/camera_utils.py`)**: Lógica de OpenCV y conversión de imágenes.

## Uso

1.  **Registro**: Escribe un nombre y pulsa "Registrar". La imagen se guardará como BLOB en la BD.
2.  **Login**: Pulsa "Entrar". El sistema descargará las imágenes de la BD, entrenará el modelo en tiempo real y verificará tu identidad.
