# Lista inicial de grupos
lista_evento = ["Kiss", "Ado"]

print("Debes organizar un evento musical, por favor añade o elimina grupos.")

# Añadir grupos a la lista
grupo_nuevo = input("Introduce un grupo para añadir: ")
lista_evento.append(grupo_nuevo)

print("Lista actual de grupos:", lista_evento)

# Preguntar si desea eliminar algún grupo
while input("¿Quieres quitar algún grupo? (si/no): ").lower() == "si":
    grupo_a_eliminar = input("Introduce el nombre del grupo a eliminar: ")
    if grupo_a_eliminar in lista_evento:
        lista_evento.remove(grupo_a_eliminar)
        print(f"Grupo '{grupo_a_eliminar}' eliminado.")
    else:
        print(f"El grupo '{grupo_a_eliminar}' no está en la lista.")

print("Lista final de grupos:", lista_evento)
