from flask import Flask, request, jsonify

app = Flask(__name__)
API_KEY = "123456"

@app.route("/data", methods=["POST"])
def get_data():
    key = request.headers.get("x-api-key")  # Leer API Key desde el header
    if key == API_KEY:
        return jsonify({"message": "Acceso permitido", "data": [1, 2, 3]})
    return jsonify({"error": "Acceso denegado"}), 403

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
