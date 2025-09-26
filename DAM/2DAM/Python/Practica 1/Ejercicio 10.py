palabras = []
colores = ["verde", "rojo", "azul", "amarillo", "naranja", "rosa", "negro", "blanco", "morado"]

print("Introduce 8 palabras para añadir a la lista")

while len(palabras) < 8:
    palabra = input("Introduce una palabra: ").lower()
    if palabra.isalpha():
        if palabra in colores:
            palabras.insert(0, palabra)
        else:
            palabras.append(palabra)
    else:
        print("Error: debes escribir solo letras. Intenta de nuevo.")

print("\nHas terminado. El array final es:", palabras)
print("Número total de palabras:", len(palabras))
