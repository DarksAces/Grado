secret_number = 777

intentos = 0
nota = 10
print(
"""
+================================+
| Welcome to my game, muggle!    |
| Enter an integer number        |
| and guess what number I've     |
| picked for you.                |
| So, what is the secret number? |
+================================+
""")
numb = int(input("Introduce el numero secreto: "))
intentos += 1

while numb != secret_number:
 intentos += 1
 print ("Ha ha! You're stuck in my loop!. \n")
 numb = int(input("Introduce el numero secreto: "))

if intentos == 1:
    print ("Well done, muggle! You are free now \n")
    print("Has hecho", intentos, "intentos")
    print("Tu nota es:", nota)

elif intentos <10:
    print ("Well done, muggle! You are free now \n")
    print("Has hecho", intentos, "intentos")
    nota -=1
    print("Tu nota es:", nota)
else:
     print ("Ha ha! You're lose the game. \n")
