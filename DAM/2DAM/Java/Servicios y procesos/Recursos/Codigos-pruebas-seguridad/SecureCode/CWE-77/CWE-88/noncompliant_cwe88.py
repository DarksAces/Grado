# SecureCode/CWE-88/noncompliant01.py
import subprocess
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/cwe88_process', methods=['GET'])
def cwe88_vulnerable():
    # El usuario debería proveer un ID de documento
    doc_id = request.args.get('doc_id', '123')
    
    # 1. Entrada controlada por el atacante
    user_options = request.args.get('options', '')
    
    # 2. VULNERABLE: Se construye un solo argumento que incluye la opción del usuario.
    # El comando legítimo es 'rm' con el argumento '-f'
    # La inyección cambia el significado del comando.
    command_list = ["rm", "-f" + user_options, f"/tmp/doc_{doc_id}"]
    
    try:
        # Se ejecuta el comando como una lista, pero la construcción del argumento es insegura.
        # El atacante puede inyectar otro argumento ('--version') al final de '-f'.
        subprocess.run(command_list, check=True, capture_output=True)
        return jsonify({"mensaje": "Documento procesado (borrado)."}), 200
    except subprocess.CalledProcessError as e:
        return jsonify({"error": f"Error: {e.stderr.strip()}"}), 500

if __name__ == '__main__':
    app.run(debug=True)

# PRUEBA DE ATAQUE:
# GET /cwe88_process?options=--version
# Si la herramienta 'rm' soporta '--version', el atacante fuerza a que el servidor muestre su versión, 
# en lugar de borrar el archivo.