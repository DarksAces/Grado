# Lista de bandas
bandas = []

# Menú de opciones
while True:
    print("\n--- Organizador de Eventos Musicales ---")
    print("1. Añadir banda")
    print("2. Eliminar banda")
    print("3. Ver lista de bandas")
    print("4. Salir")
    
    opcion = input("Elige una opción (1-4): ")

    if opcion == "1":
        banda = input("Introduce el nombre de la banda: ")
        bandas.append(banda)
        print(f"Banda '{banda}' añadida.")
    elif opcion == "2":
        banda = input("Introduce el nombre de la banda a eliminar: ")
        if banda in bandas:
            bandas.remove(banda)
            print(f"Banda '{banda}' eliminada.")
        else:
            print(f"La banda '{banda}' no está en la lista.")
    elif opcion == "3":
        print("\nLista de bandas:")
        if bandas:
            for banda in bandas:
                print(f"- {banda}")
        else:
            print("No hay bandas en la lista.")
    elif opcion == "4":
        print("¡Hasta luego!")
        break
    else:
        print("Opción no válida. Elige entre 1 y 4.")
