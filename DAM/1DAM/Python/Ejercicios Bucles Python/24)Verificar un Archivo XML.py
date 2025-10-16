import xml.etree.ElementTree as ET

def verificar_xml(ruta_archivo):
    try:
        # Intentamos parsear el archivo XML
        ET.parse(ruta_archivo)
        print(f"✅ El archivo '{ruta_archivo}' está bien estructurado (XML).")
    except ET.ParseError as e:
        print(f"❌ Error en el archivo XML '{ruta_archivo}': {e}")
    except FileNotFoundError:
        print(f"❌ El archivo '{ruta_archivo}' no existe.")
    except Exception as e:
        print(f"❌ Ocurrió un error inesperado: {e}")

# Ejemplo de uso
verificar_xml("biblioteca.xml")
