import subprocess
import os
import argparse
import sys

# ===================== Funciones =====================

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

def generar_keystore(alias, keystore, password, dname, keysize=2048):
    """Genera un nuevo keystore con un par de claves"""
    try:
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
            "-dname", dname
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
        if not verificar_alias_existe(alias, keystore, password):
            print(f"[ERROR] El alias '{alias}' no existe en el keystore '{keystore}'.")
            return False
        if os.path.exists(archivo_cert):
            print(f"[INFO] El archivo '{archivo_cert}' ya existe. Será sobrescrito.")
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

# ===================== CLI con argparse =====================

def main():
    parser = argparse.ArgumentParser(description="Herramienta para gestionar keystores y certificados.")
    subparsers = parser.add_subparsers(dest="comando", required=True)

    # Subcomando: generar
    gen_parser = subparsers.add_parser("generar", help="Generar un keystore con un alias")
    gen_parser.add_argument("--keystore", required=True, help="Nombre del keystore .jks")
    gen_parser.add_argument("--alias", required=True, help="Alias para la clave")
    gen_parser.add_argument("--password", required=True, help="Contraseña del keystore y clave")
    gen_parser.add_argument("--keysize", type=int, default=2048, help="Tamaño de la clave (default: 2048)")
    gen_parser.add_argument("--cn", default="Desconocido", help="Nombre común CN")
    gen_parser.add_argument("--ou", default="Desconocido", help="Unidad organizativa OU")
    gen_parser.add_argument("--o", default="Desconocido", help="Organización O")
    gen_parser.add_argument("--l", default="Desconocido", help="Localidad L")
    gen_parser.add_argument("--s", default="Desconocido", help="Estado S")
    gen_parser.add_argument("--c", default="ES", help="Código de país C (2 letras)")

    # Subcomando: exportar
    exp_parser = subparsers.add_parser("exportar", help="Exportar certificado de un alias")
    exp_parser.add_argument("--keystore", required=True, help="Nombre del keystore .jks")
    exp_parser.add_argument("--alias", required=True, help="Alias de la clave")
    exp_parser.add_argument("--password", required=True, help="Contraseña del keystore")
    exp_parser.add_argument("--archivo", required=True, help="Archivo de salida .crt")

    args = parser.parse_args()

    if args.comando == "generar":
        dname = f"CN={args.cn}, OU={args.ou}, O={args.o}, L={args.l}, S={args.s}, C={args.c}"
        if os.path.exists(args.keystore) and verificar_alias_existe(args.alias, args.keystore, args.password):
            print(f"[ERROR] El alias '{args.alias}' ya existe en el keystore '{args.keystore}'.")
            sys.exit(1)
        generar_keystore(args.alias, args.keystore, args.password, dname, args.keysize)

    elif args.comando == "exportar":
        if not verificar_alias_existe(args.alias, args.keystore, args.password):
            print(f"[ERROR] El alias '{args.alias}' no existe en '{args.keystore}'.")
            sys.exit(1)
        exportar_certificado(args.alias, args.keystore, args.password, args.archivo)

if __name__ == "__main__":
    main()
