import psycopg2
from src.config import Config

class DBConnection:
    """
    Singleton class to manage database connection.
    Ensures only one connection is active.
    """
    _connection = None

    @classmethod
    def get_connection(cls):
        """
        Returns the active database connection.
        If no connection exists or it's closed, creates a new one.
        """
        try:
            if cls._connection is None or cls._connection.closed != 0:
                print("Connecting to database...")
                cls._connection = psycopg2.connect(
                    host=Config.DB_HOST,
                    database=Config.DB_NAME,
                    user=Config.DB_USER,
                    password=Config.DB_PASSWORD,
                    port=Config.DB_PORT
                )
                print("Connection established.")
            return cls._connection
        except psycopg2.Error as e:
            print(f"Error connecting to database: {e}")
            raise e

    @classmethod
    def close_connection(cls):
        """Closes the active connection if it exists."""
        if cls._connection and cls._connection.closed == 0:
            cls._connection.close()
            print("Database connection closed.")

if __name__ == "__main__":
    try:
        conn = DBConnection.get_connection()
        # Optionally execute a query to verify
        with conn.cursor() as cursor:
            cursor.execute("SELECT 1;")
            print("Query executed successfully: SELECT 1")
    except Exception as e:
        print(f"Failed to connect: {e}")
    finally:
        DBConnection.close_connection()
