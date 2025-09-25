# Lista de notas de la escala cromática
notas_cromaticas = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]

# Patrones para escalas mayores y menores
patrones = {
    "Escala Mayor": [2, 2, 1, 2, 2, 2, 1],
    "Escala Menor Natural": [2, 1, 2, 2, 1, 2, 2]
}

# Función para generar la escala
def generar_escala(nota_inicial, tipo_escala):
    if nota_inicial not in notas_cromaticas or tipo_escala not in patrones:
        return "Entrada no válida."
    
    indice_inicial = notas_cromaticas.index(nota_inicial)
    patron = patrones[tipo_escala]
    
    return [notas_cromaticas[(indice_inicial + sum(patron[:i])) % len(notas_cromaticas)] for i in range(len(patron)+1)]

# Solicitar entrada del usuario y generar la escala
nota_inicial = input("Introduce la nota inicial (por ejemplo, C): ")
tipo_escala = input("Introduce el tipo de escala (Escala Mayor o Escala Menor Natural): ")

# Mostrar la escala generada
print("Notas de la escala generada:", generar_escala(nota_inicial, tipo_escala))
