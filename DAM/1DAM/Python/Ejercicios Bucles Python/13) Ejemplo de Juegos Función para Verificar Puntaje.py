# Función que verifica si el puntaje supera un límite
def verificar_puntaje(puntaje):
    if puntaje > 100: # Si el puntaje es mayor a 100
        return "¡Nivel superado!"
    else:
        return "Sigue intentando."
# Llamadas a la función con diferentes puntajes
print(verificar_puntaje(150)) # Imprime: ¡Nivel superado!
print(verificar_puntaje(50)) # Imprime: Sigue intentando.