import shutil
import os

# Ruta de la carpeta a eliminar
folder_path = '/content/test_keystores_extreme'

# Verificar si la carpeta existe
if os.path.exists(folder_path):
    shutil.rmtree(folder_path)
    print(f"✅ Carpeta '{folder_path}' y todo su contenido han sido eliminados.")
else:
    print(f"⚠️ La carpeta '{folder_path}' no existe.")
