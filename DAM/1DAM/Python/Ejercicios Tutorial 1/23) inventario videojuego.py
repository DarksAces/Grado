# Inventario inicial
inventario = {
    "Espada de Fuego": {"cantidad": 1, "categoría": "Arma"},
    "Poción Curativa": {"cantidad": 5, "categoría": "Consumible"},
    "Escudo de Hierro": {"cantidad": 1, "categoría": "Armadura"}
}

while True:
    print("\n--- Inventario ---")
    print("1. Ver inventario")
    print("2. Añadir objeto")
    print("3. Eliminar objeto")
    print("4. Salir")

    opcion = input("Elige una opción (1-4): ")

    if opcion == "1":
        print("\nInventario actual:")
        for nombre, detalles in inventario.items():
            print(f"{nombre} - Cantidad: {detalles['cantidad']}, Categoría: {detalles['categoría']}")
    elif opcion == "2":
        nombre = input("Introduce el nombre del objeto: ")
        cantidad = int(input("Introduce la cantidad: "))
        categoria = input("Introduce la categoría: ")
        inventario[nombre] = {"cantidad": cantidad, "categoría": categoria}
        print(f"Objeto '{nombre}' añadido al inventario.")
    elif opcion == "3":
        nombre = input("Introduce el nombre del objeto a eliminar: ")
        if nombre in inventario:
            del inventario[nombre]
            print(f"Objeto '{nombre}' eliminado.")
        else:
            print("El objeto no está en el inventario.")
    elif opcion == "4":
        print("¡Hasta luego!")
        break
    else:
        print("Opción no válida, elige entre 1 y 4.")
