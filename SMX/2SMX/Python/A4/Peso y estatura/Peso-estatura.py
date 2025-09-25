def bmi(weight, height):
    if height < 1.0 or height > 2.5 or weight < 20 or weight > 200:
        return None
    return weight / height ** 2

weight = float(input("Ingresa tu peso: "))

height = float(input("Ingresa tu altura: "))

bmi_usuario = bmi(weight, height)

if bmi_usuario is not None:
    print("El índice de masa corporal es:", bmi_usuario)
else:
    print("Los datos pueden ser incorrectos")
