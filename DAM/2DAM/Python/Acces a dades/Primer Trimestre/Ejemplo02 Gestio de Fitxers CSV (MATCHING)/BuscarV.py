import csv

# Archivos de entrada y salida
archivo_uf1 = "Notas_Alumnos_UF1.csv"
archivo_uf2 = "Notas_Alumnos_UF2.csv"
archivo_salida = "notas_alumnos.csv"

# --- FUNCIÓN PARA LEER UN CSV CON DICTREADER ---
def leer_notas(path, uf):

    alumnos = {}

    with open(path, newline='', encoding='latin-1') as f:
        lector = csv.DictReader(f, delimiter=';')
        for fila in lector:
            id_alumno = fila['Id']
            nombre = fila['Nombre']
            apellidos = fila['Apellidos']
            nota = fila[uf]  # Ejemplo: 'UF1' o 'UF2'

            # Creamos un registro base del alumno si no existe
            if id_alumno not in alumnos:
                alumnos[id_alumno] = {
                    'Id': id_alumno,
                    'Nombre': nombre,
                    'Apellidos': apellidos,
                    'Nota_UF1': '',
                    'Nota_UF2': ''
                }

            # Guardamos la nota de la UF correspondiente
            alumnos[id_alumno][f'Nota_{uf}'] = nota

    return alumnos


# --- LEEMOS LOS DOS CSV ---
alumnos_uf1 = leer_notas(archivo_uf1, 'UF1')
alumnos_uf2 = leer_notas(archivo_uf2, 'UF2')

# --- COMBINAMOS LOS DATOS ---
# Unificamos los diccionarios (si un alumno está en los dos, se mezclan sus notas)
alumnos_totales = alumnos_uf1.copy()
for id_alumno, datos in alumnos_uf2.items():
    if id_alumno in alumnos_totales:
        alumnos_totales[id_alumno]['Nota_UF2'] = datos['Nota_UF2']
    else:
        alumnos_totales[id_alumno] = datos

# --- ESCRIBIMOS EL NUEVO CSV ---
with open(archivo_salida, 'w', newline='', encoding='utf-8') as f:
    campos = ['Id', 'Nombre', 'Apellidos', 'Nota_UF1', 'Nota_UF2']
    escritor = csv.DictWriter(f, fieldnames=campos)
    escritor.writeheader()  # Escribimos la cabecera

    # Escribimos cada alumno en una línea
    for alumno in alumnos_totales.values():
        escritor.writerow(alumno)

print(f" Archivo '{archivo_salida}' generado correctamente.")
