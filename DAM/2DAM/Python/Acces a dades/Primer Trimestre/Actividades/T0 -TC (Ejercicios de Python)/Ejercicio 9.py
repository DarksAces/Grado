secret_password = "python"
intentos = 0

print("""
+==========================================+
| Welcome to my game, code-muggle!         |
| Try to guess the secret word I've picked |
| for you.                                 |
| You have 3 attempts.                     |
+==========================================+
""")

password = input("Introduce la palabra secreta: ")
intentos += 1

while password != secret_password and intentos < 3:
    intentos += 1
    print("\nHa ha! You're trapped in my loop, code-muggle!\n")
    password = input("Introduce la palabra secreta: ")

if password == secret_password:
    print("\nWell done, code-muggle! You are free now!\n")
    print(f"Intentos realizados: {intentos}\n")
else:
    print("\nHa ha! You lost the game, code-muggle.\n")
    print(f"La palabra secreta era: {secret_password}")
