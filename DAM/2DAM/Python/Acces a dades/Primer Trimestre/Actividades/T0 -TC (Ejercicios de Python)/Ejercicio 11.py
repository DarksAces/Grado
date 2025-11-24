def calcular_precio_final():
    col_width = 20
    # Pedir base imponible
    while True:
        numero = input("Introduzca la base imponible: ")
        try:
            numero = float(numero)
            break
        except ValueError:
            print("Eso no es un número válido. Inténtalo de nuevo.")

    tipo_iva = input("Introduzca el tipo de IVA (general, reducido, superreducido): ")
    while tipo_iva.lower() not in ["general", "reducido", "superreducido"]:
        tipo_iva = input("Tipo de IVA no válido. Introduzca 'general', 'reducido' o 'superreducido': ")

    cod_prom = input("Introduzca el código promocional (Sinpromo, Mitad, DescFijo, Porcentaje): ")
    while cod_prom.lower() not in ["sinpromo", "mitad", "descfijo", "porcentaje"]:
        cod_prom = input("Código promocional no válido. Introduzca 'Sinpromo', 'Mitad', 'DescFijo' o 'Porcentaje': ")

    if tipo_iva.lower() == "general":
        porcentaje_iva = 21
        iva_texto = "IVA (21%)"
    elif tipo_iva.lower() == "reducido":
        porcentaje_iva = 10
        iva_texto = "IVA (10%)"
    elif tipo_iva.lower() == "superreducido":
        porcentaje_iva = 4
        iva_texto = "IVA (4%)"

    iva = numero * porcentaje_iva / 100
    total = numero + iva

    if cod_prom.lower() == "sinpromo":
        descuento = 0
    elif cod_prom.lower() == "mitad":
        descuento = -total / 2
    elif cod_prom.lower() == "descfijo":
        descuento = -5
    elif cod_prom.lower() == "porcentaje":
        descuento = -total * 0.05

    total_final = total + descuento

    print(("Base imponible").ljust(col_width) + f"{numero:.2f}")
    print(iva_texto.ljust(col_width) + f"{iva:.2f}")
    print(("Cód.promo.").ljust(col_width) + f"{descuento:.2f}")
    print(("Total").ljust(col_width) + f"{total_final:.2f}\n")


def palabras_colores():
    palabras = []
    colores = ["verde", "rojo", "azul", "amarillo", "naranja", "rosa", "negro", "blanco", "morado"]

    print("Introduce 8 palabras (solo letras):")

    while len(palabras) < 8:
        palabra = input(f"Palabra {len(palabras)+1}: ").lower()
        if palabra.isalpha():
            if palabra in colores:
                palabras.insert(0, palabra)
            else:
                palabras.append(palabra)
        else:
            print("Error: solo se permiten letras.")

    print("\nArray final:", palabras)
    print("Número total de palabras:", len(palabras), "\n")


def main():
    while True:
        print("=== MENÚ ===")
        print("1. Calcular precio final (Ejercicio 7)")
        print("2. Reordenar palabras por colores (Ejercicio 10)")
        print("3. Salir")

        opcion = input("Elige una opción: ")

        if opcion == "1":
            calcular_precio_final()
        elif opcion == "2":
            palabras_colores()
        elif opcion == "3":
            print("Saliendo del programa...")
            break
        else:
            print("Opción no válida. Intenta de nuevo.\n")


if __name__ == "__main__":
    main()
