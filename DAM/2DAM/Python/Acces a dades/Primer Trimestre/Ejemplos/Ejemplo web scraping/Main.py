import requests
from bs4 import BeautifulSoup
import csv

URL = "https://books.toscrape.com/catalogue/page-{}.html"
data = []

for page in range(1, 4):  # solo 3 páginas por ejemplo
    response = requests.get(URL.format(page))
    soup = BeautifulSoup(response.text, "html.parser")

    books = soup.find_all("article", class_="product_pod")
    for book in books:
        title = book.h3.a["title"]
        price = book.find("p", class_="price_color").text.strip()
        rating = book.p["class"][1]
        data.append([title, price, rating])

# Guardar en CSV
with open("data.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["Título", "Precio", "Valoración"])
    writer.writerows(data)

print("Datos guardados en data.csv ✅")
