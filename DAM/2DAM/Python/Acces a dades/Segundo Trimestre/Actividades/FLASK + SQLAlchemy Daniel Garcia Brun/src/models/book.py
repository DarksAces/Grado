from src.config.db import db, ma

class Book(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(100), unique=True, nullable=False)
    author = db.Column(db.String(100), nullable=False)
    year = db.Column(db.Integer)

class BookSchema(ma.SQLAlchemyAutoSchema):
    class Meta:
        model = Book

book_schema = BookSchema()
books_schema = BookSchema(many=True)
