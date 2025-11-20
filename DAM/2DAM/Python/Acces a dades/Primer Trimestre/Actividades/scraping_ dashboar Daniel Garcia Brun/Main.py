import requests
from bs4 import BeautifulSoup
import csv
import pandas as pd

# URL base
URL = "https://books.toscrape.com/catalogue/page-{}.html"
BASE = "https://books.toscrape.com/catalogue/"
data = []

# Scraping
for page in range(1, 4):  # solo 3 páginas para no abusar
    response = requests.get(URL.format(page))
    soup = BeautifulSoup(response.text, "html.parser")
    books = soup.find_all("article", class_="product_pod")

    for book in books:
        title = book.h3.a["title"]
        price = book.find("p", class_="price_color").text.strip()
        rating = book.find("p", class_="star-rating")["class"][1]
        link = book.h3.a["href"]

        # Accedemos a la página del libro para obtener la "categoría" como genero
        detail_url = BASE + link
        detail_response = requests.get(detail_url)
        detail_soup = BeautifulSoup(detail_response.text, "html.parser")

        try:
            # Usamos la categoría como "generor"
            author = detail_soup.find("ul", class_="breadcrumb").find_all("a")[2].text.strip()
        except:
            author = "Desconocido"

        data.append([title, author, price, rating])

# Guardar en CSV
with open("data.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["Título", "Genero", "Precio", "Valoración"])
    writer.writerows(data)

print("✅ Datos guardados en data.csv")

# -------------------------------------------------------
# Limpieza
df = pd.read_csv("data.csv", encoding="utf-8")

# Limpieza de precios
df["Precio"] = (
    df["Precio"]
    .str.replace("Â", "", regex=False)
    .str.replace("£", "", regex=False)
    .str.strip()
    .astype(float)
)

# Convertir valoraciones a números
mapa_valoraciones = {"One": 1, "Two": 2, "Three": 3, "Four": 4, "Five": 5}
df["Valoración_num"] = df["Valoración"].map(mapa_valoraciones)

# Guardar CSV limpio
df.to_csv("data_limpia.csv", index=False, encoding="utf-8")

print("✅ Datos limpios guardados en data_limpia.csv")
print(df.head())
