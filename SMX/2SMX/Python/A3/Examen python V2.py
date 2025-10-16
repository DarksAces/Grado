listavalor= []

valores = int(input("Escribe un numero entero"))
listavalor.append (valores)

while valores !=-10:
    print("Continuar?")
    valores= int(input("Escribe un numero entero"))
    print (valores)
    if valores != -10:
        listavalor.append (valores)
        print (listavalor)
        print(len(listavalor))

sumaval = 0

for i in range (len(listavalor)):
    sumaval += listavalor [i]

media = sumaval  / len (listavalor)
print("La media es: " , media)
