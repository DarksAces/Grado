secret_number = 777
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
while numb != secret_number:
    print ("Ha ha! You're stuck in my loop!. \n")
    numb = int(input("Introduce el numero secreto: "))
print ("Well done, muggle! You are free now \n")
