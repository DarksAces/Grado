Estadisticas = ("Agilidad = 10", "Fuerza = 10",  "Inteligencia = 7")
opcion = input("Quieres ver tus estadisticas, si o no? ").lower()
if (opcion == "si"):
    print(Estadisticas)
elif(opcion == "no"):
    print("Espero que cambies de idea ")
else:
    print("Algo a salido extremadamente mal")