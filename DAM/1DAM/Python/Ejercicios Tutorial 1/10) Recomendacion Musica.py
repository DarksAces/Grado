estado_animo = input("¿Cómo te sientes hoy? (feliz, triste, energético):")
if estado_animo == "feliz":
    print("Te recomendamos escuchar música Rock.")
elif estado_animo == "triste":
    print("¿Qué tal algo de Blues?")
elif estado_animo == "energético":
    print("¡Música Funky sería perfecta para ti!")
else:
    print("Lo siento, no tengo recomendaciones para ese estado de ánimo.")