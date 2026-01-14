from flask import request, jsonify
from src.config.db import db
from src.models.book import Book, book_schema, books_schema

def init_book_routes(app):
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
