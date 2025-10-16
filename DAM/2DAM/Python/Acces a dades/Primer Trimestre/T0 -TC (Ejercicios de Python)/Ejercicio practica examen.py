def ejemplo_sep():
    print('Hola', 'mundo', sep='-')

def ejemplo_for_pass():
    for i in range(3):
        pass
    print("Bucle completado con pass")

def ejemplo_multiplicacion_cadena():
    print(3 * 'ab')

def ejemplo_diccionario():
    persona = {'nombre': 'Ana', 'edad': 30, 'ciudad': 'Madrid'}
    print(persona.get('nombre'))
    print(persona.get('edad'))
    print(persona.get('ciudad'))

def ejemplo_end():
    print('Linea1', end=' ')
    print('Linea2')

def ejemplo_continue():
    for x in range(3):
        if x == 1:
            continue
        print(x)

def ejemplo_range():
    for i in range(1, 6):
        print(i)

def ejemplo_input():
    nombre = input("Introduce tu nombre: ")
    print(f'Hola {nombre}!')

def ejemplo_clase():
    class Persona:
        def __init__(self, nombre):
            self.nombre = nombre

        def saludar(self):
            return f'Hola, me llamo {self.nombre}!'
    
    persona = Persona(input("Introduce tu nombre: "))
    print(persona.saludar())

def ejemplo_match():
    x = int(input("Introduce un numero: "))
    match x:
        case 1:
            print("uno")
        case 2:
            print("dos")
        case _:
            print("otro")


# Menu principal
def menu():
    opciones = {
        '1': ('Separador', ejemplo_sep),
        '2': ('Bucle con pass', ejemplo_for_pass),
        '3': ('Multiplicacion de cadena', ejemplo_multiplicacion_cadena),
        '4': ('Diccionario', ejemplo_diccionario),
        '5': ('Uso de end en print', ejemplo_end),
        '6': ('Uso de continue', ejemplo_continue),
        '7': ('Uso de range', ejemplo_range),
        '8': ('Input de usuario', ejemplo_input),
        '9': ('Clase Persona', ejemplo_clase),
        '10': ('Match-case', ejemplo_match),
        '0': ('Salir', None)
    }

    while True:
        print("\n--- MENU ---")
        for clave, (desc, _) in opciones.items():
            print(f"{clave}: {desc}")
        
        eleccion = input("Elige una opcion: ")
        if eleccion == '0':
            print("Saliendo del programa...")
            break
        elif eleccion in opciones:
            opciones[eleccion][1]()
        else:
            print("Opcion no valida, prueba otra vez.")

# Ejecutar menu
menu()
