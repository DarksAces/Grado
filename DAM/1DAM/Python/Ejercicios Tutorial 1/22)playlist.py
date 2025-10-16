# Playlist inicial
playlist = ["Bohemian Rhapsody", "Imagine", "One More Time", "Stairway to Heaven"]

while True:
    print("\n--- Playlist ---")
    print("1. Ver canciones")
    print("2. Añadir canción")
    print("3. Eliminar canción")
    print("4. Ordenar canciones")
    print("5. Salir")

    opcion = input("Elige una opción (1-5): ")

    if opcion == "1":
        print("\nCanciones en la playlist:")
        for cancion in playlist:
            print(cancion)
    elif opcion == "2":
        cancion = input("Introduce el nombre de la canción a añadir: ")
        playlist.append(cancion)
        print(f"Canción '{cancion}' añadida.")
    elif opcion == "3":
        cancion = input("Introduce el nombre de la canción a eliminar: ")
        if cancion in playlist:
            playlist.remove(cancion)
            print(f"Canción '{cancion}' eliminada.")
        else:
            print("La canción no está en la playlist.")
    elif opcion == "4":
        playlist.sort()
        print("Playlist ordenada.")
    elif opcion == "5":
        print("¡Hasta luego!")
        break
    else:
        print("Opción no válida, elige entre 1 y 5.")
