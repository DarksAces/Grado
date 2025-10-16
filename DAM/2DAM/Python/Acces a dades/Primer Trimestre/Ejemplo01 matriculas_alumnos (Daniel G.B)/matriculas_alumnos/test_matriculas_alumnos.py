from dominio.alumno import Alumno
from servicio.alumnos_matriculados import AlumnosMatriculados

def menu():
    while True:
        print("\n===== MENU DE MATRICULAS =====")
        print("1. Matricular alumno")
        print("2. Listar alumnos")
        print("3. Eliminar archivo de alumnos")
        print("4. Salir")

        opcion = input("Selecciona una opcion: ")

        if opcion == "1":
            nombre = input("Introduce el nombre del alumno: ")
            alumno = Alumno(nombre)
            AlumnosMatriculados.matricular_alumno(alumno)

        elif opcion == "2":
            AlumnosMatriculados.listar_alumnos()

        elif opcion == "3":
            AlumnosMatriculados.eliminar_alumnos()

        elif opcion == "4":
            print("Saliendo del programa...")
            break

        else:
            print("Opcion no valida. Intenta de nuevo.")

if __name__ == "__main__":
    menu()
