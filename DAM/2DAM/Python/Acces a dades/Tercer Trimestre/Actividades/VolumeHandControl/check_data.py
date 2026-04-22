import os
from pymongo import MongoClient
from dotenv import load_dotenv
import time

load_dotenv()
uri = os.getenv("MONGODB_URI")
db_name = os.getenv("DATABASE_NAME", "hand_tracking_db")

print(f"Trying to connect to: {uri.split('@')[-1]}...")
try:
    client = MongoClient(uri, serverSelectionTimeoutMS=5000)
    client.server_info()
    db = client[db_name]
    print(f"Sessions: {db.sessions.count_documents({})}")
    print(f"Events: {db.volume_events.count_documents({})}")
    print("Latest 5 events:")
    for doc in db.volume_events.find().sort("timestamp", -1).limit(5):
        print(f" - {doc.get('timestamp')}: {doc.get('new_volume')}")
except Exception as e:
    print(f"Error: {e}")
