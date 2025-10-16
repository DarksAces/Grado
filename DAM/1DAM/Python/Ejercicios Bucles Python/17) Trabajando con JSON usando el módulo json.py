import json # Importamos el módulo JSON
# Creamos un diccionario que representa una canción
cancion = {
"titulo": "Imagine",
"artista": "John Lennon",
"Year": 1971
}
# Convertimos el diccionario a formato JSON
json_cancion = json.dumps(cancion, indent=4) # indent=4 mejora la legibilidad
print(json_cancion) # Imprime la cadena JSON formateada