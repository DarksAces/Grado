contador = 0

print("Bienvenido al quiz de WoW")
print("En qué año salió World of Warcraft?")
print("A) 2004")
print("B) 2008")
print("C) 2000")
opcion1 = input("¿Qué opción es? (Escribe la letra en mayúscula) ")
if opcion1 == "A":
    print("Muy bien, obtienes un punto")
    contador += 1
else:
    print("Incorrecto, suerte a la próxima")

print("¿Quién desarrolló WoW?")
print("A) Riot Games")
print("B) Epic Games")
print("C) Blizzard Entertainment")
opcion1 = input("¿Qué opción es? (Escribe la letra en mayúscula) ")
if opcion1 == "C":
    print("Muy bien, obtienes un punto")
    contador += 1
else:
    print("Incorrecto, suerte a la próxima")

print("¿Quién es Arthas?")
print("A) El rey Orco")
print("B) Rey de los humanos")
print("C) Rey Exánime")
opcion1 = input("¿Qué opción es? (Escribe la letra en mayúscula) ")
if opcion1 == "C":
    print("Muy bien, obtienes un punto")
    contador += 1
else:
    print("Incorrecto, suerte a la próxima")

nota = (contador / 3) * 100
print("Tu nota es: ",nota, "%")
