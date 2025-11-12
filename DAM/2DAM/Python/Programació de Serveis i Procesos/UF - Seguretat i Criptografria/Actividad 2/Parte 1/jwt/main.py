from fastapi import FastAPI, HTTPException, Depends, status
from fastapi.security import OAuth2PasswordBearer
from jose import JWTError, jwt
from datetime import datetime, timedelta, timezone
from fastapi.middleware.cors import CORSMiddleware
import os

# --- Configuración JWT ---
SECRET_KEY = os.environ.get("JWT_SECRET_KEY", "mi_secreto_para_la_actividad_2_muy_seguro")
ALGORITHM = "HS256"
ACCESS_TOKEN_EXPIRE_MINUTES = 10

app = FastAPI(title="JWT FastAPI Example")

# --- Configuración CORS para Acceso Local (file://) ---
# Al abrir un archivo HTML localmente, el navegador a menudo usa el origen "null"
# o requiere que se incluya el esquema "file://".
allowed_origins = [
    "http://localhost:8001",
    "http://127.0.0.1:8001",
    "null",
    "file://"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=allowed_origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

# --- Función para crear el JWT ---
def create_jwt(data: dict, expires_delta: timedelta):
    to_encode = data.copy()
    expire = datetime.now(timezone.utc) + expires_delta
    to_encode.update({"exp": expire.timestamp()})
    to_encode.update({"iat": datetime.now(timezone.utc).timestamp()})
    to_encode.update({"nbf": datetime.now(timezone.utc).timestamp()})
    return jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)

# --- Dependencia para decodificar y validar el JWT ---
async def get_current_user(token: str = Depends(oauth2_scheme)):
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Token inválido o expirado",
        headers={"WWW-Authenticate": "Bearer"},
    )
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        username: str = payload.get("sub")
        if username is None:
            raise credentials_exception
        return {"sub": username}
    except JWTError:
        raise credentials_exception

# --- Endpoint de login simulado ---
@app.post("/token")
async def login():
    user_data = {"sub": "usuario123"}
    token = create_jwt(user_data, timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES))
    return {"access_token": token, "token_type": "bearer"}

# --- Endpoint protegido ---
@app.get("/protected")
async def protected(user: dict = Depends(get_current_user)):
    return {"message": "Acceso permitido", "user": user["sub"], "info": "Esta es una ruta protegida"}