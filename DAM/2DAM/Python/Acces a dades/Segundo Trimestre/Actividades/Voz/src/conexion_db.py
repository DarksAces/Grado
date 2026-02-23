import psycopg2
from src.config import Config

class ConexionDB:
    _instancia = None

    def __new__(cls):
        if cls._instancia is None:
            cls._instancia = super(ConexionDB, cls).__new__(cls)
            cls._instancia.conexion = None
        return cls._instancia

    def get_conexion(self):
        if self.conexion is None or self.conexion.closed:
            try:
                self.conexion = psycopg2.connect(
                    host=Config.DB_HOST,
                    database=Config.DB_NAME,
                    user=Config.DB_USER,
                    password=Config.DB_PASSWORD,
                    port=Config.DB_PORT
                )
                print("Conexión exitosa a la base de datos.")
            except Exception as e:
                print(f"Error al conectar a la base de datos: {e}")
                raise e
        return self.conexion

    def cerrar_conexion(self):
        if self.conexion:
            self.conexion.close()
            print("Conexión cerrada.")
