from flask import Flask
from src.config.config import Config
from src.config.db import db, ma
from src.routes.book_routes import init_book_routes

app = Flask(__name__)
app.config.from_object(Config)

db.init_app(app)
ma.init_app(app)

with app.app_context():
    # Import models here to ensure they are registered with SQLAlchemy before create_all
    from src.models.book import Book
    db.create_all()

init_book_routes(app)

if __name__ == '__main__':
    app.run(debug=True)