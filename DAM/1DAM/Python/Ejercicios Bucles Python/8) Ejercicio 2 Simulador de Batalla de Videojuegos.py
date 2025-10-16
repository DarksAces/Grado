import random

# Definimos los profesores con sus atributos
Profesores = {
    "Profesor1": {"Nombre": "Roberto", "Vida": 99, "Ataque": 98},
    "Profesor2": {"Nombre": "Javi", "Vida": 99, "Ataque": 98},
}

# Función para mostrar el estado de vida de los profesores
def mostrar_estado():
    for profesor in Profesores.values():
        print(f"{profesor['Nombre']} tiene {profesor['Vida']} de vida.")

# Simular el ataque hasta que uno quede sin vida
while Profesores["Profesor1"]["Vida"] > 0 and Profesores["Profesor2"]["Vida"] > 0:
    orden = random.randint(1, 2)
    
    if orden == 1:
        atacante = Profesores["Profesor1"]
        defensor = Profesores["Profesor2"]
    else:
        atacante = Profesores["Profesor2"]
        defensor = Profesores["Profesor1"]
    
    print(f"{atacante['Nombre']} ataca a {defensor['Nombre']} y le hace {atacante['Ataque']}")
    defensor["Vida"] -= atacante["Ataque"]
    
    if defensor["Vida"] <= 0:
        defensor["Vida"] = 0
        print(f"{defensor['Nombre']} ha sido derrotado.")
        break
    
    mostrar_estado()
    input("Presiona Enter para continuar...")

# Mostrar el estado final
print("\nEstado final:")
mostrar_estado()
