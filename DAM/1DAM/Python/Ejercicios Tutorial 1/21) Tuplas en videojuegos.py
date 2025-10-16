# Tupla para las estadísticas del personaje
estadisticas_personaje = (10, 15, 12)  # (fuerza, agilidad, inteligencia)

# Acceso a las estadísticas usando índices
fuerza = estadisticas_personaje[0]
agilidad = estadisticas_personaje[1]
inteligencia = estadisticas_personaje[2]

# Mostrar las estadísticas
print("Estadísticas del personaje:")
print("Fuerza:", fuerza)
print("Agilidad:", agilidad)
print("Inteligencia:", inteligencia)

# Otra forma de acceder a las estadísticas, utilizando los índices directamente
print("\nAcceso directo a las estadísticas desde la tupla:")
print("Fuerza:", estadisticas_personaje[0])
print("Agilidad:", estadisticas_personaje[1])
print("Inteligencia:", estadisticas_personaje[2])

# Intentar modificar una tupla (esto generará un error, ya que las tuplas son inmutables)
# estadisticas_personaje[0] = 20  # Esto generará un error si lo descomentamos
