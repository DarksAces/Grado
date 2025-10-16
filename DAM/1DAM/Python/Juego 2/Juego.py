import json
import random

# ---------------------------
# Paso 1: Cargar los Archivos JSON
# ---------------------------

def cargar_personajes():
    try:
        with open('personajes.json', 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        return []

def cargar_enemigos():
    try:
        with open('enemigos.json', 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        return []

def cargar_eventos():
    try:
        with open('eventos.json', 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        return []

def cargar_tienda():
    try:
        with open('tienda.json', 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        return []

# ---------------------------
# Paso 2: Implementar la Bola Magica
# ---------------------------

def bola_magica():
    respuestas = [
        "Exito total! Continuas sin problemas.",
        "Fallaste. Pierdes algo de vida.",
        "Tal vez, pero con consecuencias inesperadas."
    ]
    return random.choice(respuestas)

# ---------------------------
# Paso 3: Guardar y Cargar Progreso
# ---------------------------

def guardar_progreso(progreso_juego):
    with open('progreso.json', 'w') as f:
        json.dump(progreso_juego, f)

def cargar_progreso():
    try:
        with open('progreso.json', 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        return None

# ---------------------------
# Paso 4: Logica del Juego
# ---------------------------

def inicializar_stats_personaje(personaje_seleccionado):
    # Inicializar estadisticas base para cualquier personaje
    stats = {
        "vida": 100,  # Vida base para todos los personajes
        "energia": personaje_seleccionado.get("energia", 50),
        "monedas": 0,
        "xp": 0,
        "nivel": 1
    }
    
    # Agregar estadistica especifica segun el tipo de personaje
    if personaje_seleccionado["nombre"] == "Streamer":
        stats["seguidores"] = personaje_seleccionado.get("seguidores", 0)
    elif personaje_seleccionado["nombre"] == "Hacker":
        stats["anonimato"] = personaje_seleccionado.get("anonimato", 100)
    elif personaje_seleccionado["nombre"] == "Deportista":
        stats["resistencia"] = personaje_seleccionado.get("resistencia", 90)
    elif personaje_seleccionado["nombre"] == "Desarrollador":
        stats["dinero"] = personaje_seleccionado.get("dinero", 5000)
    elif personaje_seleccionado["nombre"] == "Musico":
        stats["fama"] = personaje_seleccionado.get("fama", 8000)
    elif personaje_seleccionado["nombre"] == "Gamer":
        stats["habilidad_gamer"] = personaje_seleccionado.get("habilidad_gamer", 95)
    
    return stats

def jugar():
    personajes = cargar_personajes()
    enemigos = cargar_enemigos()
    eventos = cargar_eventos()
    tienda = cargar_tienda()

    if not personajes:
        print("No se han encontrado personajes. Asegurate de tener el archivo 'personajes.json'.")
        return

    # Seleccion de personaje
    print("Elige tu personaje:")
    for i, personaje in enumerate(personajes):
        print(f"{i + 1}. {personaje['nombre']}")

    try:
        eleccion = int(input()) - 1
        if eleccion < 0 or eleccion >= len(personajes):
            print("Seleccion invalida. Por favor elige un numero valido.")
            return
        personaje_seleccionado = personajes[eleccion]
    except ValueError:
        print("Por favor ingresa un numero valido.")
        return

    print(f"Has elegido: {personaje_seleccionado['nombre']}")

    # Inicializar estadisticas del personaje
    stats = inicializar_stats_personaje(personaje_seleccionado)
    vida = stats["vida"]
    energia = stats["energia"]
    monedas = stats["monedas"]
    xp = stats["xp"]
    nivel = stats["nivel"]
    inventario = []

    # Definir la meta segun el tipo de personaje
    metas = {
        "Streamer": 1000,  # seguidores
        "Hacker": 3,      # infiltraciones exitosas
        "Deportista": 5,  # carreras ganadas
        "Desarrollador": 10000,  # dinero
        "Musico": 10000,  # fama
        "Gamer": 100      # habilidad_gamer
    }
    meta = metas.get(personaje_seleccionado['nombre'], 1000)

    while vida > 0 and energia > 0:
        print("\nQue deseas hacer?")
        print("1. Enfrentar evento o enemigo")
        print("2. Ir a la tienda")
        print("3. Usar un objeto de tu inventario")
        print("4. Ver progreso")
        print("5. Salir del juego")
        
        try:
            opcion = int(input())
        except ValueError:
            print("Por favor ingresa un numero valido.")
            continue

        if opcion == 1:
            # Simular evento o enemigo
            evento = random.choice(eventos.get(personaje_seleccionado['nombre'].lower(), []))
            if evento:
                print(f"\nEvento: {evento['descripcion']}")
                
                # Aplicar efecto del evento
                if evento['afecta'] in stats:
                    stats[evento['afecta']] += evento['cantidad']
                    print(f"Tu {evento['afecta']} ha cambiado en {evento['cantidad']}")

                # Accion de la Bola Magica
                respuesta_bola = bola_magica()
                print(f"Bola Magica: {respuesta_bola}")

                if "Fallaste" in respuesta_bola:
                    vida -= 10
                elif "Tal vez" in respuesta_bola:
                    energia -= 5

                # Ganar monedas y XP
                monedas += random.randint(5, 20)
                xp += random.randint(10, 30)

                # Subir de nivel
                if xp >= nivel * 100:
                    nivel += 1
                    print(f"Has subido al nivel {nivel}!")
                
                # Mostrar vida actual después de la pelea
                print(f"\nVida actual: {vida}")

        elif opcion == 2:
            # Mostrar tienda
            print("\nTienda:")
            for idx, item in enumerate(tienda):
                print(f"{idx + 1}. {item['nombre']} - Costo: {item['costo']} monedas")
            print(f"Tienes {monedas} monedas.")
            
            try:
                eleccion_tienda = int(input("Quieres comprar algo? (Ingresa el numero del objeto o 0 para continuar): "))
                if 0 < eleccion_tienda <= len(tienda):
                    item = tienda[eleccion_tienda - 1]
                    if monedas >= item['costo']:
                        monedas -= item['costo']
                        inventario.append(item)
                        print(f"Has comprado {item['nombre']}")
                    else:
                        print("No tienes suficientes monedas.")
                elif eleccion_tienda != 0:
                    print("Opcion invalida.")
            except ValueError:
                print("Por favor ingresa un numero valido.")

        elif opcion == 3:
            if inventario:
                print("\nTu inventario:")
                for i, item in enumerate(inventario):
                    print(f"{i + 1}. {item['nombre']}")
                
                try:
                    eleccion_objeto = int(input("Que objeto deseas usar? (Ingresa el numero del objeto o 0 para cancelar): "))
                    if 0 < eleccion_objeto <= len(inventario):
                        objeto = inventario[eleccion_objeto - 1]
                        for stat, valor in objeto['efecto'].items():
                            if stat in stats:
                                stats[stat] += valor
                        print(f"Has usado {objeto['nombre']}")
                        inventario.pop(eleccion_objeto - 1)
                    elif eleccion_objeto != 0:
                        print("Opcion invalida.")
                except ValueError:
                    print("Por favor ingresa un numero valido.")
            else:
                print("Tu inventario esta vacio.")

        elif opcion == 4:
            print("\nProgreso actual:")
            print(f"Vida: {vida}")
            print(f"Energia: {energia}")
            print(f"Monedas: {monedas}")
            print(f"XP: {xp}")
            print(f"Nivel: {nivel}")
            for stat, valor in stats.items():
                if stat not in ['vida', 'energia', 'monedas', 'xp', 'nivel']:
                    print(f"{stat.capitalize()}: {valor}")

        elif opcion == 5:
            print("Gracias por jugar!")
            break

        else:
            print("Opcion no valida. Intenta de nuevo.")

        if vida <= 0 or energia <= 0:
            print("Te has quedado sin vida o energia! Has perdido!")
            break

if __name__ == "__main__":
    jugar()