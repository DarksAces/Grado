# SecureCode/CWE-79/noncompliant01.py
from flask import Flask, request

app = Flask(__name__)

@app.route('/welcome', methods=['GET'])
def cwe79_vulnerable():
    # 1. Entrada controlada por el atacante
    user_name = request.args.get('nombre', 'Invitado')
    
    # 2. VULNERABLE: La entrada se inyecta directamente en la respuesta HTML.
    # El atacante puede inyectar: <script>alert('XSS');</script>
    html_response = f"""
    <html>
        <head><title>Bienvenido</title></head>
        <body>
            <h1>Hola, {user_name}!</h1>
            <p>Tu sesión puede ser robada.</p>
        </body>
    </html>
    """
    return html_response

if __name__ == '__main__':
    app.run(debug=True)

# PRUEBA DE ATAQUE (para inyectar un script en el navegador del usuario):
# GET /welcome?nombre=<script>alert('XSS');</script>