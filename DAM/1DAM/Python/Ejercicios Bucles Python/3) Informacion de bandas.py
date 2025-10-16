# Definimos un diccionario con información sobre bandas
bandas = {
    "The Beatles": {"género": "Rock", "año": 1960},
    "Daft Punk": {"género": "Electrónica", "año": 1993},
    "Queen": {"género": "Rock", "año": 1970}
}

# Iteramos sobre cada clave (nombre de la banda) y valor (detalles)
for banda, detalles in bandas.items():
    # Mostramos la información de la banda
    print(f"{banda}: Género - {detalles['género']}, Año - {detalles['año']}")
