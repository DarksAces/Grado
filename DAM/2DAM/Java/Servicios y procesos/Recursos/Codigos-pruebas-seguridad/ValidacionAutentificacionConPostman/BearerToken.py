from flask import Flask, request, jsonify

app = Flask(__name__)

VALID_TOKEN = "abc123"

@app.route("/protected", methods=["POST"])
def protected():
    token = request.headers.get("Authorization")
    if token == f"Bearer {VALID_TOKEN}":
        return jsonify({"message": "Acceso permitido", "data": [42, 99]})
    return jsonify({"error": "Token inválido"}), 403

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
