print("\033[2J\033[1;1f") # Borrar. pantalla y situar cursor
hora = int(input(chr(27) + "[1;34m" +("Starting time (hours): ")))
minutos = int(input("\x1b[0;32m\x1b[3;37m" +"Starting time (minutes): "))
duracion = int(input("\x1b[1;32m""Event duration (minutes): "))

Clok=60
horas=24

reloj=((hora+((minutos+duracion)//Clok))%horas)

minute=(minutos+duracion)%Clok

print("\033[4;36m" + "Endtime:",reloj,":",minute)