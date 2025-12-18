from flask import Flask, request, jsonify
from flask_jwt_extended import create_access_token, jwt_required, JWTManager
from flask_cors import CORS

app = Flask(__name__)
app.config["JWT_SECRET_KEY"] = "supersecret"
jwt = JWTManager(app)
CORS(app)

# Simulación de base de datos
users = [
    {"id": 1, "name": "Alice", "email": "alice@example.com", "password": "password123", "role": "admin"},
    {"id": 2, "name": "Bob", "email": "bob@example.com", "password": "password123", "role": "user"}
]

# Endpoint de login
@app.route("/login", methods=["POST"])
def login():
    data = request.json
    user = next((u for u in users if u["email"] == data["email"] and u["password"] == data["password"]), None)

    if not user:
        return jsonify({"error": "Invalid credentials"}), 401

    token = create_access_token(identity={"id": user["id"], "role": user["role"]})
    return jsonify({"token": token})

#  Ruta vulnerable: no valida que el usuario autenticado solo acceda a su propio perfil
@app.route("/user/<int:user_id>", methods=["GET"])
@jwt_required()
def get_user(user_id):
    user = next((u for u in users if u["id"] == user_id), None)
    if not user:
        return jsonify({"error": "User not found"}), 404

    return jsonify(user)  # No valida permisos

if __name__ == "__main__":
    app.run(debug=True, port=5000)
