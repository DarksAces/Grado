import os
from dotenv import load_dotenv

load_dotenv()

class Config:
    DB_HOST = os.getenv("DB_HOST", "localhost")
    DB_NAME = os.getenv("DB_NAME", "voice_audit")
    DB_USER = os.getenv("DB_USER", "postgres")
    DB_PASSWORD = os.getenv("DB_PASSWORD", "")
    DB_PORT = os.getenv("DB_PORT", "5432")
    
    # Índice del micrófono (opcional, puede ser uno o varios separados por coma)
    raw_mic = os.getenv("MIC_INDEX")
    if raw_mic:
        # Convertir "32,42" en [32, 42]
        MIC_INDICES = [int(x.strip()) for x in raw_mic.split(",") if x.strip().isdigit()]
    else:
        MIC_INDICES = [None] # Por defecto
