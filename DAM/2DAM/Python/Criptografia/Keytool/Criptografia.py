import subprocess
import os


def verificar_alias_existe(alias, keystore, password):
    """Verifica si un alias ya existe en el keystore"""
    if not os.path.exists(keystore):
        return False
    
    try:
        comando = [
            "keytool",
            "-list",
            "-alias", alias,
            "-keystore", keystore,
            "-storepass", password
        ]
        resultado = subprocess.run(comando, capture_output=True, text=True)
        return resultado.returncode == 0
    except Exception:
        return False


def listar_alias_keystore(keystore, password):
    """Lista todos los alias en un keystore"""
    try:
        comando = [
            "keytool",
            "-list",
            "-keystore", keystore,
            "-storepass", password
        ]
        resultado = subprocess.run(comando, capture_output=True, text=True, check=True)
        return True
    except subprocess.CalledProcessError:
        return False


def generar_keystore(alias, keystore, password, keysize=2048):
    """Genera un nuevo keystore con un par de claves"""
    try:
        # Verificar si el keystore existe y si el alias ya está en uso
        if os.path.exists(keystore):
            if verificar_alias_existe(alias, keystore, password):
                print(f"[ERROR] El alias '{alias}' ya existe en el keystore '{keystore}'.")
                return False
        
        comando = [
            "keytool",
            "-genkeypair",
            "-keyalg", "RSA",
            "-alias", alias,
            "-keystore", keystore,
            "-storepass", password,
            "-keypass", password,
            "-keysize", str(keysize),
            "-dname", "CN=Ejemplo, OU=Org, O=Empresa, L=Ciudad, S=Provincia, C=ES"
        ]
        subprocess.run(comando, check=True, capture_output=True)
        print(f"[OK] Keystore '{keystore}' generado correctamente con alias '{alias}'.")
        return True
    except subprocess.CalledProcessError as e:
        print("[ERROR] Error al ejecutar keytool:", e.stderr.decode() if e.stderr else str(e))
        return False
    except FileNotFoundError:
        print("[ERROR] keytool no encontrado. Asegúrate de tener Java JDK instalado.")
        return False


def exportar_certificado(alias, keystore, password, archivo_cert):
    """Exporta el certificado desde el keystore"""
    try:
        # Verificar si el alias existe
        if not verificar_alias_existe(alias, keystore, password):
            print(f"[ERROR] El alias '{alias}' no existe en el keystore '{keystore}'.")
            print("[INFO] Verifica que el alias y la contraseña sean correctos.")
            return False
        
        # Verificar si el archivo de certificado ya existe
        if os.path.exists(archivo_cert):
            respuesta = input(f"[AVISO] El archivo '{archivo_cert}' ya existe. ¿Deseas sobrescribirlo? (s/n): ").strip().lower()
            if respuesta != 's':
                print("[INFO] Operación cancelada.")
                return False
        
        comando = [
            "keytool",
            "-export",
            "-alias", alias,
            "-file", archivo_cert,
            "-keystore", keystore,
            "-storepass", password
        ]
        subprocess.run(comando, check=True, capture_output=True)
        print(f"[OK] Certificado exportado correctamente a '{archivo_cert}'.")
        return True
    except subprocess.CalledProcessError as e:
        print("[ERROR] Error al exportar certificado:", e.stderr.decode() if e.stderr else str(e))
        return False
    except FileNotFoundError:
        print("[ERROR] keytool no encontrado. Asegúrate de tener Java JDK instalado.")
        return False


def pedir_password(confirmar=False):
    """Solicita y valida la contraseña"""
    while True:
        pwd = input("Escribe la contraseña (mínimo 6 caracteres, sin espacios): ")
        if " " in pwd:
            print("[ERROR] La contraseña no puede contener espacios.")
            continue
        if len(pwd) < 6:
            print("[ERROR] La contraseña debe tener al menos 6 caracteres.")
            continue
        
        # Si se requiere confirmación
        if confirmar:
            pwd_confirm = input("Confirma la contraseña: ")
            if pwd != pwd_confirm:
                print("[ERROR] Las contraseñas no coinciden. Inténtalo de nuevo.")
                continue
        
        return pwd


def menu_principal():
    """Muestra el menú principal y maneja las opciones"""
    print("\n" + "="*50)
    print("HERRAMIENTA KEYTOOL - GESTION DE CERTIFICADOS")
    print("="*50)
    print("1. Generar keystore (generar par de claves)")
    print("2. Exportar certificado")
    print("0. Salir")
    print("="*50)
    
    opcion = input("Selecciona una opción: ").strip()
    return opcion


def opcion_generar():
    """Maneja la opción de generar keystore"""
    print("\nGENERAR KEYSTORE")
    print("-" * 50)
    
    # Pedir nombre del fichero primero
    while True:
        keystore = input("Nombre del fichero .jks (ej: miClave.jks): ").strip()
        if keystore:
            if not keystore.endswith('.jks'):
                keystore += '.jks'
            break
        print("[ERROR] El nombre del fichero no puede estar vacío.")
    
    # Si el keystore existe, pedir contraseña para verificar alias
    password = None
    keystore_nuevo = not os.path.exists(keystore)
    
    if not keystore_nuevo:
        print(f"[INFO] El keystore '{keystore}' ya existe. Se agregará un nuevo alias.")
        password = input("Contraseña del keystore existente: ")
    
    # Pedir alias
    while True:
        alias = input("Escribe el alias: ").strip()
        if not alias:
            print("[ERROR] El alias no puede estar vacío.")
            continue
        
        # Si el keystore existe, verificar que el alias no exista
        if not keystore_nuevo and password:
            if verificar_alias_existe(alias, keystore, password):
                print(f"[ERROR] El alias '{alias}' ya existe en este keystore.")
                respuesta = input("¿Deseas intentar con otro alias? (s/n): ").strip().lower()
                if respuesta != 's':
                    print("[INFO] Operación cancelada.")
                    return
                continue
        break

    # Pedir password si no se pidió antes
    if password is None:
        # Si es keystore nuevo, pedir confirmación
        password = pedir_password(confirmar=True)

    # Usar 2048 por defecto
    keysize = 2048
    print(f"[INFO] Usando tamaño de clave: {keysize} bits")

    generar_keystore(alias, keystore, password, keysize)


def opcion_exportar():
    """Maneja la opción de exportar certificado"""
    print("\nEXPORTAR CERTIFICADO")
    print("-" * 50)
    
    # Pedir nombre del keystore
    while True:
        keystore = input("Nombre del fichero .jks: ").strip()
        if keystore:
            if not keystore.endswith('.jks'):
                keystore += '.jks'
            if not os.path.exists(keystore):
                print(f"[ERROR] El fichero '{keystore}' no existe.")
                continue
            break
        print("[ERROR] El nombre del fichero no puede estar vacío.")
    
    # Pedir password
    password = input("Contraseña del keystore: ")
    
    # Verificar que la contraseña sea correcta listando el keystore
    if not listar_alias_keystore(keystore, password):
        print("[ERROR] Contraseña incorrecta o keystore corrupto.")
        return
    
    # Pedir alias
    while True:
        alias = input("Escribe el alias: ").strip()
        if not alias:
            print("[ERROR] El alias no puede estar vacío.")
            continue
        
        # Verificar que el alias exista
        if not verificar_alias_existe(alias, keystore, password):
            print(f"[ERROR] El alias '{alias}' no existe en el keystore.")
            respuesta = input("¿Deseas intentar con otro alias? (s/n): ").strip().lower()
            if respuesta != 's':
                print("[INFO] Operación cancelada.")
                return
            continue
        break

    # Pedir nombre del certificado a exportar
    while True:
        archivo_cert = input("Nombre del archivo certificado .crt (ej: miCertificado.crt): ").strip()
        if archivo_cert:
            if not archivo_cert.endswith('.crt'):
                archivo_cert += '.crt'
            break
        print("[ERROR] El nombre del archivo no puede estar vacío.")

    exportar_certificado(alias, keystore, password, archivo_cert)


if __name__ == "__main__":
    while True:
        opcion = menu_principal()
        
        if opcion == "1":
            opcion_generar()
        elif opcion == "2":
            opcion_exportar()
        elif opcion == "0":
            print("\nHasta luego!")
            break
        else:
            print("\n[ERROR] Opción no válida. Intenta de nuevo.")
        
        input("\nPresiona Enter para continuar...")