import subprocess
import os


def generar_keystore(alias, keystore, password, keysize=2048):
    """Genera un nuevo keystore con un par de claves"""
    try:
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
        subprocess.run(comando, check=True)
        print(f"[OK] Keystore '{keystore}' generado correctamente con alias '{alias}'.")
    except subprocess.CalledProcessError as e:
        print("[ERROR] Error al ejecutar keytool:", e)
    except FileNotFoundError:
        print("[ERROR] keytool no encontrado. Asegúrate de tener Java JDK instalado.")


def exportar_certificado(alias, keystore, password, archivo_cert):
    """Exporta el certificado desde el keystore"""
    try:
        comando = [
            "keytool",
            "-export",
            "-alias", alias,
            "-file", archivo_cert,
            "-keystore", keystore,
            "-storepass", password
        ]
        subprocess.run(comando, check=True)
        print(f"[OK] Certificado exportado correctamente a '{archivo_cert}'.")
    except subprocess.CalledProcessError as e:
        print("[ERROR] Error al exportar certificado:", e)
    except FileNotFoundError:
        print("[ERROR] keytool no encontrado. Asegúrate de tener Java JDK instalado.")


def pedir_password():
    """Solicita y valida la contraseña"""
    while True:
        pwd = input("Escribe la contraseña (mínimo 6 caracteres, sin espacios): ")
        if " " in pwd:
            print("[ERROR] La contraseña no puede contener espacios.")
            continue
        if len(pwd) < 6:
            print("[ERROR] La contraseña debe tener al menos 6 caracteres.")
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
    
    # Pedir alias
    while True:
        alias = input("Escribe el alias: ").strip()
        if alias:
            break
        print("[ERROR] El alias no puede estar vacío.")

    # Pedir nombre del fichero
    while True:
        keystore = input("Nombre del fichero .jks (ej: miClave.jks): ").strip()
        if keystore:
            if not keystore.endswith('.jks'):
                keystore += '.jks'
            break
        print("[ERROR] El nombre del fichero no puede estar vacío.")

    # Pedir password con validación
    password = pedir_password()

    # Pedir tamaño de clave
    keysize = input("Tamaño de clave (2048 o 4096) [default: 2048]: ").strip()
    if not keysize:
        keysize = 2048
    elif not keysize.isdigit():
        print("[AVISO] Tamaño inválido, usando 2048 por defecto.")
        keysize = 2048
    else:
        keysize = int(keysize)
        if keysize not in (2048, 4096):
            print("[AVISO] Tamaño inválido, usando 2048 por defecto.")
            keysize = 2048

    generar_keystore(alias, keystore, password, keysize)


def opcion_exportar():
    """Maneja la opción de exportar certificado"""
    print("\nEXPORTAR CERTIFICADO")
    print("-" * 50)
    
    # Pedir alias
    while True:
        alias = input("Escribe el alias: ").strip()
        if alias:
            break
        print("[ERROR] El alias no puede estar vacío.")

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

    # Pedir nombre del certificado a exportar
    while True:
        archivo_cert = input("Nombre del archivo certificado .crt (ej: miCertificado.crt): ").strip()
        if archivo_cert:
            if not archivo_cert.endswith('.crt'):
                archivo_cert += '.crt'
            break
        print("[ERROR] El nombre del archivo no puede estar vacío.")

    # Pedir password
    password = input("Contraseña del keystore: ")

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