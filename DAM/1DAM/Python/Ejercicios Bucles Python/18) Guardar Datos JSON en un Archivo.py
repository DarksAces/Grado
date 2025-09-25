import json
import os

# Creamos una lista de canciones
biblioteca = [
    {"titulo": "Imagine", "artista": "John Lennon", "Year": 1971},
    {"titulo": "Bohemian Rhapsody", "artista": "Queen", "Year": 1975}
]

# Obtenemos el directorio actual
directorio_actual = os.path.dirname(os.path.realpath(__file__))
ruta_archivo = os.path.join(directorio_actual, "biblioteca.json")

# Guardamos los datos en un archivo JSON en el directorio actual
with open(ruta_archivo, "w") as archivo:
    json.dump(biblioteca, archivo, indent=4)

print(f"Biblioteca musical guardada en '{ruta_archivo}'")
