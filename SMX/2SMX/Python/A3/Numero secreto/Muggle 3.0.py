secret_number = 777

intentos = 0
numb = 0
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

while numb != secret_number and intentos <10:
 intentos += 1
 print ("Ha ha! You're stuck in my loop!. \n")
 numb = int(input("Introduce el numero secreto: "))

if numb == secret_number:
    print ("Well done, muggle! You are free now \n")
    print("Has hecho", intentos, "intentos \n")
    print("Tu nota es:",11 - intentos)
    

else:
     print ("Ha ha! You're lose the game. \n")
     print("Has hecho", intentos, "intentos \n")
     print("Tu nota es:",10 - intentos)

