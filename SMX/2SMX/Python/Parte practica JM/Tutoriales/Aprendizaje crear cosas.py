from tkinter import *

ventana_Raiz = Tk() #Crear ventana
ventana_Raiz.title("Monlau 2SMX") #nombre a la ventana
#ventana_Raiz.iconbitmap ("Monlau.ico")
#ventana_Raiz.geometry ("520x400+500+200")
ventana_Raiz.resizable(1,1) #espicificar si se puede ampliar 1,1 si 0,0 no
ventana_Raiz.config(bg = "red", padx=100, pady=50) #configuramos la ventana

marco_1=Frame() #marco de la vnetana
marco_1.config(bg="blue") #config marco
marco_1.config(width=400, height=300) #config altura/anchura

marco_1.pack() #funcion para que aparezca en la pantalla

etiqueta_1=Label() 
etiqueta_1.config(text="Etiqueta 1",bg="yellow", fg="black", font="black")
etiqueta_1.pack()

boton_1=Button(text="Boton")
boton_1.pack()

ventana_Raiz.mainloop()