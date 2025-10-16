print("Te encuentras en una encrucijada, ¿vas a la izquierda o a la derecha?")
eleccion = input("Escribe 'izquierda' o 'derecha': ")
if eleccion == "izquierda":
    print("Encuentras un tesoro.")
elif eleccion == "derecha":
    print("Te encuentras con un dragón.")
else:
    print("Te has perdido en el bosque.")