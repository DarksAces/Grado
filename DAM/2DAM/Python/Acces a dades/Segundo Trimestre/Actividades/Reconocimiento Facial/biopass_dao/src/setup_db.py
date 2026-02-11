import psycopg2
from psycopg2.extensions import ISOLATION_LEVEL_AUTOCOMMIT
from src.config import Config
import os

def setup_database():
    print("--- BioPass DAO Setup ---")
    
    # 1. Conectar a la base de datos por defecto 'postgres' para verificar/crear la BD objetivo
    print(f"1. Conectando al servidor PostgreSQL (usuario={Config.DB_USER})...")
    try:
        con = psycopg2.connect(
            host=Config.DB_HOST,
            user=Config.DB_USER,
            password=Config.DB_PASSWORD,
            port=Config.DB_PORT,
            database='postgres' # Conectar a la BD por defecto primero
        )
        con.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
        cursor = con.cursor()
    except psycopg2.Error as e:
        print(f"CRITICAL ERROR: Could not connect to PostgreSQL. Check your password in .env")
        print(f"Error details: {e}")
        return

    # 2. Verificar si la base de datos existe
    target_db = Config.DB_NAME
    print(f"2. Verificando si la base de datos '{target_db}' existe...")
    cursor.execute(f"SELECT 1 FROM pg_catalog.pg_database WHERE datname = '{target_db}'")
    exists = cursor.fetchone()
    
    if not exists:
        print(f"   Database '{target_db}' not found. Creating it...")
        try:
            cursor.execute(f"CREATE DATABASE {target_db}")
            print(f"   Database '{target_db}' created successfully!")
        except psycopg2.Error as e:
            print(f"   Error creating database: {e}")
            return
    else:
        print(f"   Database '{target_db}' already exists.")
    
    cursor.close()
    con.close()

    # 3. Crear Tablas en la base de datos objetivo
    print(f"3. Creando tablas en '{target_db}'...")
    try:
        # Conectar a la NUEVA base de datos
        con = psycopg2.connect(
            host=Config.DB_HOST,
            user=Config.DB_USER,
            password=Config.DB_PASSWORD,
            port=Config.DB_PORT,
            database=target_db
        )
        cursor = con.cursor()
        
        # Leer archivo SQL
        sql_file_path = os.path.join(os.path.dirname(__file__), '..', 'db', 'create_tables.sql')
        with open(sql_file_path, 'r') as f:
            sql_script = f.read()
            
        cursor.execute(sql_script)
        con.commit()
        print("   Tables initialized successfully.")
        
    except psycopg2.Error as e:
        print(f"   Error creating tables: {e}")
    finally:
        if con:
            con.close()

    print("\n--- Setup Complete ---")
    print("You can now run the application: python -m src.biopass_app")

if __name__ == "__main__":
    setup_database()
