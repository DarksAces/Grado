from pymongo import MongoClient
from config.settings import MONGODB_URI, DATABASE_NAME
from models.session import Session
from models.volume_event import VolumeEvent
import threading

class MongoDAO:
    _instance = None
    _lock = threading.Lock()

    def __new__(cls):
        with cls._lock:
            if cls._instance is None:
                cls._instance = super(MongoDAO, cls).__new__(cls)
                cls._instance._initialized = False
        return cls._instance

    def __init__(self):
        if self._initialized:
            return
        try:
            self.client = MongoClient(MONGODB_URI, serverSelectionTimeoutMS=5000)
            self.db = self.client[DATABASE_NAME]
            # Test connection
            self.client.server_info()
            self.connected = True
        except Exception as e:
            print(f"Error connecting to MongoDB: {e}")
            self.connected = False
        self._initialized = True

    def insert_session(self, session: Session):
        if not self.connected: return None
        result = self.db.sessions.insert_one(session.to_dict())
        return str(result.inserted_id)

    def update_session(self, session_id: str, end_time, duration):
        if not self.connected: return
        from bson.objectid import ObjectId
        self.db.sessions.update_one(
            {"_id": ObjectId(session_id)},
            {"$set": {"end_time": end_time, "duration_seconds": duration}}
        )

    def insert_volume_event(self, event: VolumeEvent):
        if not self.connected: return
        self.db.volume_events.insert_one(event.to_dict())
