from tkinter import Tk,Label,Button,Entry
#Creamos y definimos el tamaño de la ventana#
vent = Tk()
vent.title("Calculadora Version 3")
vent.geometry("465x200")
vent.resizable(0,0)

#Definimos las funciones#
def fSuma():
    n1=txtNum1.get()
    n2=txtNum2.get()
    r=float(n1)+float(n2)
    txtNum3.delete(0,'end')
    txtNum3.insert(0,r)
         
def fMultiplica():
    n1=txtNum1.get()
    n2=txtNum2.get()
    r=float(n1)*float(n2)
    txtNum3.delete(0,'end')
    txtNum3.insert(0,r)

def fRestar():
    n1=txtNum1.get()
    n2=txtNum2.get()
    r=float(n1)-float(n2)
    txtNum3.delete(0,'end')
    txtNum3.insert(0,r)    

def fDividir():
    n1=txtNum1.get()
    n2=txtNum2.get()
    r=float(n1)/float(n2)
    txtNum3.delete(0,'end')
    txtNum3.insert(0,r)

def fBorrar():
    txtNum1.delete(0, 'end')
    txtNum2.delete(0, 'end')
    txtNum3.delete(0, 'end')


#Creamos los labels y textos#
lblNum1 = Label(vent,text="Primer Número: ", bg="#f3a464", width= "15",  height= "2")
txtNum1 = Entry(vent,bg="#ffd700", justify="right")
lblNum2 = Label(vent,text="Segundo Número: ",bg="#f3a464", width= "15",  height= "2")
txtNum2 = Entry(vent,bg="#ffd700", justify="right")
lblNum3 = Label(vent,text="Resultado: ",bg="#9bfc9c" , width= "15",  height= "2")
txtNum3=Entry(vent,bg="#4682b4", justify="right")

#Creamos los botones#
btnSuma = Button(vent,text="Sumar" , command=fSuma ,bg="#eed5b7",fg="#008000", height= "2")
btnMultiplicar = Button(vent,text="Multiplicar", command=fMultiplica ,bg="#eed5b7",fg="#9b30f3", height= "2")
btnRestar = Button(vent,text="Restar", command=fRestar ,bg="#eed5b7",fg="#ff0000", height= "2")
btnDivivdir = Button(vent,text="Dividir", command=fDividir ,bg="#eed5b7",fg="#0000e7", height= "2")
btnBorrar = Button(vent,text="Borrar", command=fBorrar ,bg="#eed5b7",fg="#a52a2a", height= "2")

#Situamos los botones donde queremos#
lblNum1.place(x=5, y=20)
txtNum1.place(x=140, y=25,  height= "25") 
lblNum2.place(x=5, y=75)
txtNum2.place(x=140, y=80,  height= "25")
lblNum3.place(x=5, y=135)
txtNum3.place(x=140, y=135, height= "25")
btnSuma.place(x=275, y=15, width= "75")
btnMultiplicar.place(x=360, y=15, width= "100")
btnRestar.place(x=275, y=65, width= "75")
btnDivivdir.place(x=360, y=65, width= "100")
btnBorrar.place(x=310, y=125, width= "100")

vent.mainloop()