import json

# Función para cargar la biblioteca desde un archivo JSON
def cargar_biblioteca():
    try:
        with open("biblioteca.json", "r") as archivo:
            return json.load(archivo)
    except FileNotFoundError:
        return []

# Función para guardar la biblioteca en un archivo JSON
def guardar_biblioteca(biblioteca):
    with open("biblioteca.json", "w") as archivo:
        json.dump(biblioteca, archivo, indent=4)

# Función para agregar una canción
def agregar_cancion(biblioteca):
    titulo = input("Título de la canción: ")
    artista = input("Artista: ")
    year = input("Year: ")
    biblioteca.append({"titulo": titulo, "artista": artista, "year": year})
    print("Canción añadida correctamente.")

# Función principal
def main():
    biblioteca = cargar_biblioteca()
    while True:
        print("\n1. Añadir canción\n2. Mostrar biblioteca\n3. Salir")
        opcion = input("Selecciona una opción: ")
        
        if opcion == "1":
            agregar_cancion(biblioteca)
            guardar_biblioteca(biblioteca)
        elif opcion == "2":
            for cancion in biblioteca:
                # Manejar la falta de la clave 'year'
                year = cancion.get('year', 'Desconocido')
                print(f"{cancion['titulo']} - {cancion['artista']} ({year})")
        elif opcion == "3":
            print("¡Hasta la próxima!")
            break
        else:
            print("Opción no válida.")

if __name__ == "__main__":
    main()
