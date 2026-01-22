from src.conexion_db import DBConnection
import os

def init_db():
    print("Initializing Database...")
    conn = DBConnection.get_connection()
    if conn:
        try:
            cursor = conn.cursor()
            
            # Read SQL file
            sql_file_path = os.path.join(os.path.dirname(__file__), '..', 'db', 'create_tables.sql')
            with open(sql_file_path, 'r') as f:
                sql_script = f.read()
            
            print("Executing SQL script...")
            cursor.execute(sql_script)
            conn.commit()
            print("Tables created successfully.")
            
        except Exception as e:
            print(f"Error initializing database: {e}")
            conn.rollback()
        finally:
            cursor.close()
            DBConnection.close_connection()

if __name__ == "__main__":
    init_db()
