import random

escala_cromatica = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]
nota_random  = random.choice(escala_cromatica)


jugador =input("Escribe una nota de la escala cromatica ")
while jugador != nota_random:
    if jugador < nota_random:
        print("La nota en la que he pensado es mayor a la que has escrito ")
        
   
        jugador = input("Escribe una nota de la escala cromatica ")

    elif jugador > nota_random:
        print("La nota en la que estoy pensando es menor a la que has escrito ")
      
        jugador = input("Escribe una nota de la escala cromatica ")
    
if jugador == nota_random:

    print ("Felicidades has hacertado la nota") 
   