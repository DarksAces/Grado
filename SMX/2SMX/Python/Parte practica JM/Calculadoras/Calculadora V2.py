from tkinter import Tk,Label,Button,Entry

vent = Tk()
vent.title("Calculadora Version 2")
vent.geometry("400x200")


def fSuma():
    n1=txtNum1.get()
    n2=txtNum2.get()
    r=float(n1)+float(n2)
    txtNum3.delete(0,'end')
    txtNum3.insert(0,r)    

def fResta():
    n1=txtNum1.get()
    n2=txtNum2.get()
    r=float(n1)-float(n2)
    txtNum3.delete(0,'end')
    txtNum3.insert(0,r) 

def fMultiplica():
    n1=txtNum1.get()
    n2=txtNum2.get()
    r=float(n1)*float(n2)
    txtNum3.delete(0,'end')
    txtNum3.insert(0,r)

def fDivision():
    n1=txtNum1.get()
    n2=txtNum2.get()
    r=float(n1)/float(n2)
    txtNum3.delete(0,'end')
    txtNum3.insert(0,r)

lblNum1 = Label(vent,text="Primer Número: ",bg="yellow")
txtNum1=Entry(vent,bg="pink")
lblNum2 = Label(vent,text="Segundo Número: ",bg="yellow")
txtNum2=Entry(vent,bg="pink")
btn1=Button(vent,text="Sumar", command=fSuma)
lblNum3 = Label(vent,text="Resultado: ",bg="yellow")
txtNum3=Entry(vent,bg="cyan")




lblNum1.pack(pady=6)
txtNum1.pack(pady=6)
lblNum2.pack(pady=6)
txtNum2.pack(pady=6)
btn1.pack(pady=6)
lblNum3.pack(pady=6)
txtNum3.pack(pady=6)


vent.mainloop()
