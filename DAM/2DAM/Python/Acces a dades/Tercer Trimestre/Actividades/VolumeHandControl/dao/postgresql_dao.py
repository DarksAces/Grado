import psycopg2
from psycopg2 import sql
from config.settings import DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD
from models.session import Session
from models.volume_event import VolumeEvent
import threading

class PostgreSQLDAO:
    _instance = None
    _lock = threading.Lock()

    def __new__(cls):
        with cls._lock:
            if cls._instance is None:
                cls._instance = super(PostgreSQLDAO, cls).__new__(cls)
                cls._instance._initialized = False
        return cls._instance

    def __init__(self):
        if self._initialized:
            return
        try:
            self.conn = psycopg2.connect(
                host=DB_HOST,
                port=DB_PORT,
                database=DB_NAME,
                user=DB_USER,
                password=DB_PASSWORD
            )
            self.conn.autocommit = True
            self.connected = True
            self._create_tables()
        except Exception as e:
            print(f"❌ Error connecting to PostgreSQL: {e}")
            self.connected = False
        self._initialized = True

    def _create_tables(self):
        if not self.connected: return
        with self.conn.cursor() as cur:
            # Create sessions table
            cur.execute("""
                CREATE TABLE IF NOT EXISTS sessions (
                    id SERIAL PRIMARY KEY,
                    start_time TIMESTAMP NOT NULL,
                    end_time TIMESTAMP,
                    duration_seconds FLOAT DEFAULT 0.0
                );
            """)
            # Create volume_events table
            cur.execute("""
                CREATE TABLE IF NOT EXISTS volume_events (
                    id SERIAL PRIMARY KEY,
                    timestamp TIMESTAMP NOT NULL,
                    previous_volume FLOAT NOT NULL,
                    new_volume FLOAT NOT NULL,
                    finger_distance FLOAT NOT NULL,
                    session_id INTEGER REFERENCES sessions(id)
                );
            """)

    def insert_session(self, session: Session):
        if not self.connected: return None
        try:
            with self.conn.cursor() as cur:
                cur.execute(
                    "INSERT INTO sessions (start_time) VALUES (%s) RETURNING id;",
                    (session.start_time,)
                )
                session_id = cur.fetchone()[0]
                return session_id
        except Exception as e:
            print(f"Error inserting session: {e}")
            return None

    def update_session(self, session_id: int, end_time, duration):
        if not self.connected or session_id is None: return
        try:
            with self.conn.cursor() as cur:
                cur.execute(
                    "UPDATE sessions SET end_time = %s, duration_seconds = %s WHERE id = %s;",
                    (end_time, duration, session_id)
                )
        except Exception as e:
            print(f"Error updating session: {e}")

    def insert_volume_event(self, event: VolumeEvent):
        if not self.connected: return
        try:
            with self.conn.cursor() as cur:
                cur.execute(
                    """INSERT INTO volume_events 
                       (timestamp, previous_volume, new_volume, finger_distance, session_id) 
                       VALUES (%s, %s, %s, %s, %s);""",
                    (event.timestamp, event.previous_volume, event.new_volume, event.finger_distance, event.session_id)
                )
        except Exception as e:
            print(f"Error inserting volume event: {e}")
