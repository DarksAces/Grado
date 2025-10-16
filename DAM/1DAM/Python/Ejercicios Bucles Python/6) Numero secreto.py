import random

secret_number = random.randint(1, 10)
intentos = 0

print(
"""
+================================+
| Welcome to my game, muggle!    |
| Enter an integer number        |
| and guess what number I've     |
| picked for you.                |
| So, what is the secret number? |
| a little clue since my kindness|
| is infinite the number is      |
| between  1 and 10              |
| you have 3 attempts            |        
+================================+
""")

while intentos < 3:
    numb = int(input("Introduce el numero secreto: "))
    intentos += 1

    if numb == secret_number:
        print("Well done, muggle! You are free now.")
        print("Has hecho", intentos, "intentos.")
        break
    else:
        print("Ha ha! You're stuck in my loop!.")
        
if numb != secret_number:
    print("Has superado tu numero de intentos.")
    print("Ha ha! You lose the game.")
    print("Has hecho", intentos, "intentos.")
    print("El numero era:", secret_number)
