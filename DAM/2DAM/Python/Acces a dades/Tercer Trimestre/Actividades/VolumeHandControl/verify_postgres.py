import sys
import os
from datetime import datetime

# Add the project directory to sys.path
sys.path.append(os.getcwd())

from config.settings import DB_HOST, DB_NAME, DB_USER
from dao.postgresql_dao import PostgreSQLDAO
from models.session import Session
from models.volume_event import VolumeEvent

def test_connection():
    print(f"Testing connection to {DB_USER}@{DB_HOST}/{DB_NAME}...")
    dao = PostgreSQLDAO()
    
    if dao.connected:
        print("✅ Connection successful!")
        
        # Test Session Insertion
        start_time = datetime.now()
        session = Session(start_time=start_time)
        session_id = dao.insert_session(session)
        
        if session_id:
            print(f"✅ Session inserted with ID: {session_id}")
            
            # Test Volume Event Insertion
            event = VolumeEvent(
                timestamp=datetime.now(),
                previous_volume=50.0,
                new_volume=60.0,
                finger_distance=150.0,
                session_id=session_id
            )
            dao.insert_volume_event(event)
            print("✅ Volume event inserted successfully!")
            
            # Test Session Update
            end_time = datetime.now()
            duration = (end_time - start_time).total_seconds()
            dao.update_session(session_id, end_time, duration)
            print("✅ Session updated successfully!")
            
            print("\nMigration verified! You can now check your data in pgAdmin.")
        else:
            print("❌ Failed to insert session.")
    else:
        print("❌ Connection failed. Check your .env file and ensure PostgreSQL is running.")

if __name__ == "__main__":
    test_connection()
