from src.conexion_db import DBConnection
import psycopg2

class UsuarioDAO:
    """
    Data Access Object for User operations.
    Decouples business logic from data access.
    """

    @staticmethod
    def registrar_usuario(nombre, image_bytes):
        """
        Registers a new user with their face image (BLOB).
        """
        connection = DBConnection.get_connection()
        cursor = None
        try:
            cursor = connection.cursor()
            query = "INSERT INTO usuarios (nombre, imagen_facial) VALUES (%s, %s)"
            # psycopg2.Binary is used to safely pass binary data
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
        Retrieves all users and their face images for training.
        Returns a list of tuples (id, nombre, imagen_facial_bytes).
        """
        connection = DBConnection.get_connection()
        cursor = None
        try:
            cursor = connection.cursor()
            query = "SELECT id, nombre, imagen_facial FROM usuarios"
            cursor.execute(query)
            # Fetch all results
            results = cursor.fetchall()
            return results
        except psycopg2.Error as e:
            print(f"Error retrieving users: {e}")
            return []
        finally:
            if cursor:
                cursor.close()
