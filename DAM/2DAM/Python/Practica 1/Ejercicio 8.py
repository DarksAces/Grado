contador = 0

while True:
    entrada = input("Introduce un número entero (negativo para salir): ")
    try:
        numero = int(entrada)
    except ValueError:
        print("¡Eso no es un número entero! Intenta de nuevo.")
        continue

    if numero < 0:
        print(f"Has introducido {contador} números enteros positivos.")
        break
    else:
        contador += 1
