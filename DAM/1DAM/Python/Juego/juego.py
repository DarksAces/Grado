import random  # Para generar valores aleatorios
import json  # Para manejar archivos JSON

# -----------------------------
# Cargar datos desde JSON
# -----------------------------
def cargar_datos(archivo):
    try:
        with open(archivo, "r") as file:
            return json.load(file)
    except FileNotFoundError:
        print(f"Error: El archivo '{archivo}' no fue encontrado.")
        return None
    except json.JSONDecodeError:
        print(f"Error: El archivo '{archivo}' no tiene un formato JSON válido.")
        return None

heroes = cargar_datos("personajes.json")
enemigos = cargar_datos("enemigos.json")["enemigos"]  # Lista de enemigos

tienda = cargar_datos("tienda.json")

def guardar_progreso(personaje, enemigos_restantes, archivo="progreso.json"):
    """Guarda el progreso del juego en un archivo JSON."""
    datos = {
        "personaje": personaje,  # Estadísticas del personaje
        "enemigos": enemigos_restantes  # Lista de enemigos restantes
    }
    with open(archivo, "w") as file:  # Abrimos el archivo en modo escritura
        json.dump(datos, file, indent=4)  # Guardamos los datos con formato legible
    print(f"Progreso guardado en '{archivo}'.")

def cargar_progreso(archivo="progreso.json"):
    """Carga el progreso del juego desde un archivo JSON si existe."""
    try:
        with open(archivo, "r") as file:  # Intentamos abrir el archivo
            datos = json.load(file)  # Cargamos los datos del archivo JSON
        print(f"Progreso cargado desde '{archivo}'.")
        return datos["personaje"], datos["enemigos"]  # Retornamos personaje y enemigos restantes
    except FileNotFoundError:
        print(f"No se encontró el archivo '{archivo}'. Comenzaremos un nuevo juego.")
        return None, None  # Si no existe, retornamos valores por defecto


def comprar_objeto(personaje, objeto):
    if objeto in tienda:
        costo = tienda[objeto]["costo"]
        if personaje["monedas"] >= costo:
            personaje["monedas"] -= costo
            personaje["inventario"].append(objeto)
            print(f"Has comprado {objeto} por {costo} monedas.")
        else:
            print("No tienes suficientes monedas.")
    else:
        print("Objeto no encontrado en la tienda.")

def usar_objeto(personaje, objeto):
    if objeto in personaje["inventario"]:
        if objeto == "pocion_vida":
            curacion = int(personaje["vida_maxima"] * 0.3)
            personaje["vida"] = min(personaje["vida"] + curacion, personaje["vida_maxima"])
            print(f"Has usado una Poción de Vida y recuperado {curacion} de vida.")
        elif objeto == "pocion_danyo":
            personaje["buff_danyo"] = 5
            print("Has usado una Poción de Aumento de Danyo. Efecto activo por 5 turnos.")
        elif objeto == "pocion_escudo":
            personaje["buff_escudo"] = 5
            print("Has usado una Poción de Escudo. Efecto activo por 5 turnos.")
        personaje["inventario"].remove(objeto)
    else:
        print("No tienes ese objeto en tu inventario.")

# -----------------------------
# Función para luchar
# -----------------------------
def luchar(personaje, enemigo):
    print(f"¡Un {enemigo['nombre']} te desafía!")
    print(f"Descripción: {enemigo['descripcion']}")
    print(f"Estadísticas del enemigo: Vida: {enemigo['vida']}, Ataque: {enemigo['ataque']}\n")

    while personaje["vida"] > 0 and enemigo["vida"] > 0:
        print("Es tu turno:")
        print("1. Atacar")
        print("2. Defender")
        print("3. Usar objeto")
        accion = int(input("¿Qué haces? (1-3): "))

        if accion == 1:
            daño = max(personaje["ataque"] - enemigo["ataque"] // 2, 1)
            if personaje.get("buff_danyo", 0) > 0:
                daño *= 1.5
                personaje["buff_danyo"] -= 1
            enemigo["vida"] -= daño
            print(f"¡Golpeas al {enemigo['nombre']} y le haces {daño} de danyo!")
        elif accion == 2:
            personaje["vida"] += personaje["defensa"] // 2
            print(f"Te defiendes y recuperas {personaje['defensa'] // 2} puntos de vida.")
        elif accion == 3:
            print("Inventario:", personaje["inventario"])
            objeto = input("¿Qué objeto quieres usar?: ")
            usar_objeto(personaje, objeto)

        if enemigo["vida"] > 0:
            daño = max(enemigo["ataque"] - personaje["defensa"] // 2, 1)
            personaje["vida"] -= daño
            print(f"El {enemigo['nombre']} te ataca y te hace {daño} de danyo.\n")

        print(f"Tu vida: {personaje['vida']} | Vida del enemigo: {enemigo['vida']}\n")

    if personaje["vida"] > 0:
        print(f"¡Has derrotado al {enemigo['nombre']}!")
        personaje["vida"] += 10
        monedas_ganadas = random.randint(20, 60)
        personaje["monedas"] += monedas_ganadas
        print(f"Recuperas 10 puntos de vida. Has ganado {monedas_ganadas} monedas.\n")
        return True
    else:
        print("¡Has sido derrotado! Fin del juego.")
        return False


def menu_tienda(personaje):
    print("Bienvenido a la tienda. Estos son los objetos disponibles:")
    for objeto, datos in tienda.items():
        print(f"- {objeto}: {datos['descripcion']} (Costo: {datos['costo']} monedas)")
    compra = input("¿Qué objeto deseas comprar? (o 'salir' para salir): ")
    if compra != "salir":
        comprar_objeto(personaje, compra)


def main():
    # Cargar progreso si existe
    personaje, enemigos_restantes = cargar_progreso()
    
    if personaje is None or enemigos_restantes is None:
        # Si no hay progreso, comenzamos un nuevo juego
        print("Elige tu personaje:")
        for i, personaje in enumerate(heroes.keys(), 1):
            print(f"{i}. {personaje}")
        eleccion = int(input("Selecciona un número: ")) - 1
        nombre_personaje = list(heroes.keys())[eleccion]
        personaje = heroes[nombre_personaje]
        personaje["vida_maxima"] = personaje["vida"]
        personaje["monedas"] = 100
        personaje["inventario"] = []
        enemigos_restantes = enemigos.copy()  # Copia de enemigos

    while True:
        enemigo = random.choice(enemigos_restantes)
        if not luchar(personaje, enemigo):
            break
        print("¿Qué quieres hacer?")
        print("1. Seguir luchando")
        print("2. Tienda")
        print("3. Salir sin guardar")
        print("4. Guardar y salir")
        opcion = int(input("Selecciona una opción (1-4): "))
        
        if opcion == 2:
            menu_tienda(personaje)
        elif opcion == 3:
            print("¡Gracias por jugar!")
            break
        
        elif opcion == 4:
            guardar_progreso(personaje, enemigos_restantes)  # Guardamos el progreso
            print("¡Gracias por jugar! Hasta la próxima.")
            break
        
        if not enemigos_restantes:
            print("¡Has derrotado a todos los enemigos! ¡Felicidades, has ganado!")
            break

if __name__ == "__main__":
    main()
