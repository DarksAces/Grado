from flask import Flask, request, jsonify
import base64

app = Flask(__name__)

USERS = {
    "admin": "password123"
}

@app.route("/login", methods=["POST"])
def login():
    auth_header = request.headers.get("Authorization")
    if not auth_header or "Basic " not in auth_header:
        return jsonify({"error": "Credenciales requeridas"}), 401

    auth_encoded = auth_header.split("Basic ")[1]
    user_pass = base64.b64decode(auth_encoded).decode("utf-8")  # Decodifica Base64
    username, password = user_pass.split(":")

    if USERS.get(username) == password:
        return jsonify({"message": "Login exitoso"})
    return jsonify({"error": "Credenciales incorrectas"}), 403

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
