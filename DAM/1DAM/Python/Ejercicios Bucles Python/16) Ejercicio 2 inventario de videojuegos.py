Inventario = {

}

def añadir_objeto(diccionario):
    tipo = input("Introduce el tipo de objeto (por ejemplo, Espadas, Escudo): ")
    nombre = input("Introduce el nombre del objeto: ")
    atributo = input("Introduce el atributo del objeto (por ejemplo, Ataque, Defensa, Curacion): ")
    valor = input("Introduce el valor del atributo: ")
    
    if tipo in diccionario:
        diccionario[tipo]["Nombre"] = nombre
        diccionario[tipo][atributo] = valor
    else:
        diccionario[tipo] = {"Nombre": nombre, atributo: valor}
    
    return diccionario

def menu():
    print("Bienvenido, si quisieses salir en cualquier momento pulsa Q")
    print("Para añadir más objetos pulsa A")

    while True:
        opcion = input("¿Qué quieres hacer? ").upper()
        
        if opcion == "Q":
            print(Inventario)
            break
        elif opcion == "A":
            añadir_objeto(Inventario)
            print(Inventario)
        else:
            print("Opción no válida, intenta de nuevo.")

# Ejecutar el menú
menu()

