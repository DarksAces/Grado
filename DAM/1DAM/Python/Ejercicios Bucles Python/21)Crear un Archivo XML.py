import xml.etree.ElementTree as ET

# Creamos el elemento raíz
biblioteca = ET.Element("biblioteca")

# Añadimos canciones como subelementos
cancion1 = ET.SubElement(biblioteca, "cancion")
ET.SubElement(cancion1, "titulo").text = "Imagine"
ET.SubElement(cancion1, "artista").text = "John Lennon"
ET.SubElement(cancion1, "Year").text = "1971"

cancion2 = ET.SubElement(biblioteca, "cancion")
ET.SubElement(cancion2, "titulo").text = "Bohemian Rhapsody"
ET.SubElement(cancion2, "artista").text = "Queen"
ET.SubElement(cancion2, "Year").text = "1975"

cancion3 = ET.SubElement(biblioteca, "cancion")
ET.SubElement(cancion3, "titulo").text = "Hotel California"
ET.SubElement(cancion3, "artista").text = "Eagles"
ET.SubElement(cancion3, "Year").text = "1976"

# Convertimos el árbol XML en una cadena y lo guardamos en un archivo
arbol = ET.ElementTree(biblioteca)
arbol.write("biblioteca.xml")
print("Archivo XML creado: biblioteca.xml")

# Mostrar el contenido del archivo XML
ET.dump(biblioteca)
