import psycopg2
from psycopg2.extensions import ISOLATION_LEVEL_AUTOCOMMIT
from src.config import Config
import os

def setup_database():
    print("--- BioPass DAO Setup ---")
    
    # 1. Connect to default 'postgres' database to check/create target DB
    print(f"1. Connecting to PostgreSQL server (user={Config.DB_USER})...")
    try:
        con = psycopg2.connect(
            host=Config.DB_HOST,
            user=Config.DB_USER,
            password=Config.DB_PASSWORD,
            port=Config.DB_PORT,
            database='postgres' # Connect to default DB first
        )
        con.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
        cursor = con.cursor()
    except psycopg2.Error as e:
        print(f"CRITICAL ERROR: Could not connect to PostgreSQL. Check your password in .env")
        print(f"Error details: {e}")
        return

    # 2. Check if database exists
    target_db = Config.DB_NAME
    print(f"2. Checking if database '{target_db}' exists...")
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

    # 3. Create Tables in the target database
    print(f"3. Creating tables in '{target_db}'...")
    try:
        # Connect to the NEW database
        con = psycopg2.connect(
            host=Config.DB_HOST,
            user=Config.DB_USER,
            password=Config.DB_PASSWORD,
            port=Config.DB_PORT,
            database=target_db
        )
        cursor = con.cursor()
        
        # Read SQL file
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
