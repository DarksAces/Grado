import json
import os

# Obtenemos el directorio actual
directorio_actual = os.path.dirname(os.path.abspath(__file__))
ruta_archivo = os.path.join(directorio_actual, "biblioteca.json")

# Leemos el archivo JSON
with open(ruta_archivo, "r") as archivo:
    datos = json.load(archivo)  # Cargamos los datos en formato Python

# Mostramos los datos cargados
for cancion in datos:
    print(f"Título: {cancion['titulo']}, Artista: {cancion['artista']}, Year: {cancion['Year']}")
