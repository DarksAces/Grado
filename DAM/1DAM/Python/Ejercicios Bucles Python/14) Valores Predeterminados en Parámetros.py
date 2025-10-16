# Definimos una función con un valor predeterminado para "clase"
def crear_personaje(nombre, clase="Guerrero"):
    print(f"Creaste un {clase} llamado {nombre}.")
# Llamadas a la función
crear_personaje("Aragorn") # Usa el valor predeterminado: Guerrero
crear_personaje("Legolas", "Arquero") # Especifica el valor: Arquero