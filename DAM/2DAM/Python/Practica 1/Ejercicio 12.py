from datetime import datetime

class Coche:
    def __init__(self, marca, modelo, año, velocidad=0):
        self.marca = marca
        self.modelo = modelo
        self.año = año
        self.velocidad = velocidad

    def acelerar(self, incremento):
        self.velocidad += incremento
        return f"{self.marca} {self.modelo} ha acelerado a {self.velocidad} km/h."

    def frenar(self, decremento):
        self.velocidad -= decremento
        return f"{self.marca} {self.modelo} ha frenado a {self.velocidad} km/h."

    def descripcion(self):
        return f"{self.marca} {self.modelo} ({self.año}), velocidad actual: {self.velocidad} km/h."


class CocheElectrico(Coche):
    def __init__(self, marca, modelo, año, bateria=100):
        super().__init__(marca, modelo, año)
        self.bateria = bateria

    def cargar(self, cantidad):
        self.bateria += cantidad
        self.bateria = min(100, self.bateria)
        return f"{self.marca} {self.modelo} ha cargado {cantidad}%, batería actual: {self.bateria}%."

    def acelerar(self, incremento=5):
        if self.bateria <= 0:
            return f"{self.marca} {self.modelo} no puede acelerar, batería agotada."
        self.velocidad += incremento
        self.bateria -= incremento * 0.5
        self.bateria = max(0, self.bateria)
        return f"{self.marca} {self.modelo} acelera a {self.velocidad} km/h, batería restante: {self.bateria}%."


anio_actual = datetime.now().year

# Datos coche convencional
print("=== Datos del coche convencional ===")
marca = input("Introduce la marca: ")
modelo = input("Introduce el modelo: ")

while True:
    try:
        año = int(input("Introduce el año: "))
        if 1886 <= año <= anio_actual:
            break
        else:
            print(f"Introduce un año entre 1886 y {anio_actual}.")
    except ValueError:
        print("Debes introducir un número entero para el año.")

mi_coche = Coche(marca, modelo, año)

# Datos coche eléctrico
print("\n=== Datos del coche eléctrico ===")
marca_e = input("Introduce la marca: ")
modelo_e = input("Introduce el modelo: ")

while True:
    try:
        año_e = int(input("Introduce el año: "))
        if año_e > año and año_e <= anio_actual:
            break
        else:
            print(f"El año debe ser mayor que {año} y no mayor que {anio_actual}.")
    except ValueError:
        print("Debes introducir un número entero para el año.")

mi_coche_electrico = CocheElectrico(marca_e, modelo_e, año_e)

# Información y simulación
print("\n--- Información de los coches ---")
print(mi_coche.descripcion())
print(mi_coche_electrico.descripcion())

print("\n--- Simulación ---")
print(mi_coche.acelerar(20))
print(mi_coche.frenar(10))
print(mi_coche_electrico.acelerar(60))
print(mi_coche_electrico.cargar(25))
