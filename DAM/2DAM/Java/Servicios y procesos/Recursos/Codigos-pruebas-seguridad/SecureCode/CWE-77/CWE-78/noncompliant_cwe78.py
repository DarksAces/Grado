# SecureCode/CWE-78/noncompliant01.py
import os
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/cwe78_info', methods=['GET'])
def cwe78_vulnerable():
    # 1. Entrada controlada por el atacante
    user_input = request.args.get('nombre_archivo', '')
    
    # 2. VULNERABLE: La entrada se concatena directamente y se ejecuta a través del shell.
    # Un atacante puede usar: 'reporte.txt; whoami'
    command = "cat /var/log/app/" + user_input 
    
    try:
        # os.system() es insegura porque usa el shell para interpretar el comando
        os.system(command)
        return jsonify({"mensaje": f"Comando ejecutado: {command}"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)

# PRUEBA DE ATAQUE:
# GET /cwe78_info?nombre_archivo=archivo.log;%20whoami