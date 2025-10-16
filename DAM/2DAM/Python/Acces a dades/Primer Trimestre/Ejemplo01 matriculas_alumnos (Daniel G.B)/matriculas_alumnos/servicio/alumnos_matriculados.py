import os
from dominio.alumno import Alumno

class AlumnosMatriculados:
    ruta_archivo = "alumnos.txt"

    @staticmethod
    def matricular_alumno(alumno: Alumno):
        """Agrega un alumno al archivo"""
        with open(AlumnosMatriculados.ruta_archivo, "a", encoding="utf-8") as f:
            f.write(alumno.nombre + "\n")
        print(f"Alumno '{alumno.nombre}' matriculado correctamente.")

    @staticmethod
    def listar_alumnos():
        """Muestra todos los alumnos matriculados"""
        if not os.path.exists(AlumnosMatriculados.ruta_archivo):
            print("No hay alumnos matriculados aun.")
            return

        with open(AlumnosMatriculados.ruta_archivo, "r", encoding="utf-8") as f:
            alumnos = f.readlines()

        print("\nLista de alumnos matriculados:")
        for alumno in alumnos:
            print("-", alumno.strip())

    @staticmethod
    def eliminar_alumnos():
        """Elimina el archivo de alumnos"""
        if os.path.exists(AlumnosMatriculados.ruta_archivo):
            os.remove(AlumnosMatriculados.ruta_archivo)
            print("Archivo de alumnos eliminado.")
        else:
            print("No existe ningun archivo de alumnos.")
