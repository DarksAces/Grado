import json

def verificar_json(ruta_archivo):
    try:
        # Abrimos y cargamos el archivo JSON
        with open(ruta_archivo, "r") as archivo:
            datos = json.load(archivo)
            print(f"✅ El archivo '{ruta_archivo}' está bien estructurado (JSON).")
    except json.JSONDecodeError as e:
        print(f"❌ Error en el archivo JSON '{ruta_archivo}': {e}")
    except FileNotFoundError:
        print(f"❌ El archivo '{ruta_archivo}' no existe.")
    except Exception as e:
        print(f"❌ Ocurrió un error inesperado: {e}")

# Ejemplo de uso
verificar_json("biblioteca.json")
