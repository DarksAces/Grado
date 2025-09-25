import xml.etree.ElementTree as ET

# Cargamos el archivo XML
arbol = ET.parse("biblioteca.xml")
raiz = arbol.getroot()

# Mostramos la información de cada canción
for cancion in raiz.findall("cancion"):
    titulo = cancion.find("titulo").text
    artista = cancion.find("artista").text
    año = cancion.find("Year").text
    print(f"Título: {titulo}, Artista: {artista}, Año: {año}")
