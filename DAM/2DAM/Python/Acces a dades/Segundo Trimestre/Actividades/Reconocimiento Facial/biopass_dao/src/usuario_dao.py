from src.conexion_db import DBConnection
import psycopg2

class UsuarioDAO:
    """
    Objeto de Acceso a Datos (DAO) para operaciones de Usuario.
    Desacopla la lógica de negocio del acceso a datos.
    """

    @staticmethod
    def registrar_usuario(nombre, image_bytes):
        """
        Registra un nuevo usuario con su imagen facial (BLOB).
        """
        connection = DBConnection.get_connection()
        cursor = None
        try:
            cursor = connection.cursor()
            query = "INSERT INTO usuarios (nombre, imagen_facial) VALUES (%s, %s)"
            # psycopg2.Binary se usa para pasar datos binarios de forma segura
            cursor.execute(query, (nombre, psycopg2.Binary(image_bytes)))
            connection.commit()
            print(f"User {nombre} registered successfully.")
            return True
        except psycopg2.Error as e:
            print(f"Error registering user: {e}")
            connection.rollback()
            return False
        finally:
            if cursor:
                cursor.close()

    @staticmethod
    def obtener_todos():
        """
        Recupera todos los usuarios y sus imágenes faciales para entrenamiento.
        Devuelve una lista de tuplas (id, nombre, imagen_facial_bytes).
        """
        connection = DBConnection.get_connection()
        cursor = None
        try:
            cursor = connection.cursor()
            query = "SELECT id, nombre, imagen_facial FROM usuarios"
            cursor.execute(query)
            # Obtener todos los resultados
            results = cursor.fetchall()
            return results
        except psycopg2.Error as e:
            print(f"Error retrieving users: {e}")
            return []
        finally:
            if cursor:
                cursor.close()
