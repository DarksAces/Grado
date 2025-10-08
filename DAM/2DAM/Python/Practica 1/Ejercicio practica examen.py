print('Hola', 'mundo', sep='-')


for i in range(3): 
   pass


print(3 * 'ab')


persona = {'nombre': 'Ana', 'edad': 30, 'ciudad': 'Madrid'}

print (persona.get('nombre'))
print (persona.get('edad'))
print (persona.get('ciudad'))


print('Línea1', end=' ')
print('Línea2')


for x in range(3):
    if x == 1:
        continue
    print(x)



for i in range(1, 6):
    print(i)


nombre = input("Introduce tu nombre: ")
print(f'Hola {nombre}!')





class Persona:
    def __init__(self, nombre):
        self.nombre = nombre

    def saludar(self):
        return f'Hola, me llamo {self.nombre}!'



x = int(input("Introduce un número: "))

match x:
    case 1:
        print("uno")
    case 2:
        print("dos")
    case _:
        print("otro")
