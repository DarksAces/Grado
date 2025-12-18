# SecureCode/CWE-79/compliant01.py
from flask import Flask, request
from markupsafe import escape # Usado para codificar HTML

app = Flask(__name__)

@app.route('/welcome_safe', methods=['GET'])
def cwe79_compliant():
    user_name = request.args.get('nombre', 'Invitado')
    
    # 1. SEGURO: La entrada es escapada antes de ser inyectada en el HTML.
    # Si la entrada es <script>, la salida será &lt;script&gt;
    safe_user_name = escape(user_name)
    
    html_response = f"""
    <html>
        <head><title>Bienvenido Seguro</title></head>
        <body>
            <h1>Hola, {safe_user_name}!</h1>
            <p>Tu sesión está segura.</p>
        </body>
    </html>
    """
    return html_response

if __name__ == '__main__':
    app.run(debug=True)