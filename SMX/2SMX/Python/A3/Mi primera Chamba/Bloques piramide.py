bloques = int(input("Ingrese el número de bloques: "))
altura = 0
capas = 1
sobras = 0
while capas <= bloques:
    altura += 1
    bloques -= capas
    capas += 1
else:
    sobras += bloques
print("La altura es:", altura)
if sobras == 1:
    print ("Sobra", bloques, "bloque")
else:
    print ("Sobran", bloques, "bloques")
