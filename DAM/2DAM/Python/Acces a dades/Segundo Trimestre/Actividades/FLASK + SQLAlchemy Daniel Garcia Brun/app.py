from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
import os

app = Flask(__name__)
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'db.sqlite')
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)
ma = Marshmallow(app)

class Book(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(100), unique=True, nullable=False)
    author = db.Column(db.String(100), nullable=False)
    year = db.Column(db.Integer)

with app.app_context():
    db.create_all()

class BookSchema(ma.SQLAlchemyAutoSchema):
    class Meta:
        model = Book

book_schema = BookSchema()
books_schema = BookSchema(many=True)

@app.route('/book', methods=['POST'])
def add_book():
    new_book = Book(**request.get_json(force=True))
    db.session.add(new_book)
    db.session.commit()
    return book_schema.jsonify(new_book), 201

@app.route('/books', methods=['GET'])
def get_books():
    return books_schema.jsonify(Book.query.all())

@app.route('/book/<int:id>', methods=['GET'])
def get_book(id):
    return book_schema.jsonify(Book.query.get_or_404(id))

@app.route('/book/<int:id>', methods=['PUT'])
def update_book(id):
    book = Book.query.get_or_404(id)
    for key, value in request.get_json(force=True).items():
        setattr(book, key, value)
    db.session.commit()
    return book_schema.jsonify(book)

@app.route('/book/<int:id>', methods=['DELETE'])
def delete_book(id):
    book = Book.query.get_or_404(id)
    db.session.delete(book)
    db.session.commit()
    return jsonify({"message": "Eliminado"}), 200

if __name__ == '__main__':
    app.run(debug=True)