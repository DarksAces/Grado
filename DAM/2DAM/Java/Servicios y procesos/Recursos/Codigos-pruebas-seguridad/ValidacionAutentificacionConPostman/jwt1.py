from flask import Flask, request, jsonify
import jwt
import datetime

app = Flask(__name__)
SECRET_KEY = "supersecret"

@app.route("/token", methods=["POST"])
def get_token():
    username = request.json.get("username")
    password = request.json.get("password")

    if username == "admin" and password == "password123":
        payload = {
            "user": username,
            "exp": datetime.datetime.utcnow() + datetime.timedelta(minutes=10)
        }
        token = jwt.encode(payload, SECRET_KEY, algorithm="HS256")
        return jsonify({"token": token})
    
    return jsonify({"error": "Credenciales incorrectas"}), 403

@app.route("/protected", methods=["POST"])
def protected():
    token = request.headers.get("Authorization")
    if not token or "Bearer " not in token:
        return jsonify({"error": "Token requerido"}), 401

    token = token.split("Bearer ")[1]

    try:
        decoded = jwt.decode(token, SECRET_KEY, algorithms=["HS256"])
        return jsonify({"message": "Acceso permitido", "user": decoded["user"]})
    except jwt.ExpiredSignatureError:
        return jsonify({"error": "Token expirado"}), 403
    except jwt.InvalidTokenError:
        return jsonify({"error": "Token inválido"}), 403

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
