import requests   # Requests obtiene la página
from bs4 import BeautifulSoup  # BeautifulSoup la analiza
import csv # csv guarda los datos
import pandas as pd # Para trabajar con datos tabulados

# Primero indicamos la página que queremos que busque
URL= "https://books.toscrape.com/catalogue/page-{}.html" # URL con {} sirve para recorrer múltiples páginas reemplazando {} por el número de página
data = []

# Hacemos un bucle diciendo que tantas páginas queremos
for page in range(1, 4):  # Para no petar mucho la página vamos a buscar solo las 3 primeras
    response = requests.get(URL.format(page)) # Lanzamos la solicitud a la web
    soup = BeautifulSoup(response.text, "html.parser") # Convertimos el HTML en un objeto python que podamos usar
    books = soup.find_all("article", class_="product_pod")  # Corregido: la clase correcta es "product_pod"
    for book in books:
        title = book.h3.a["title"] # Busca la etiqueta HTML
        price = book.find("p", class_="price_color").text.strip()
        rating = book.find("p", class_="star-rating")["class"][1]  # [1] → toma la segunda clase que indica el rating
        data.append([title, price, rating]) # Lo añadimos al diccionario

# Guardar en CSV
with open("data.csv", "w", newline="", encoding="utf-8") as f: # Creamos el data.csv en caso que no exista y plasmamos los datos
    writer = csv.writer(f)
    writer.writerow(["Título", "Precio", "Valoración"]) # elegimos que datos añadir
    writer.writerows(data)

print("Datos guardados en data.csv") # mensaje de confirmación

# ------------------------------------------------------- #
# Ahora limpiamos el csv

df = pd.read_csv("data.csv", encoding="utf-8")
df["Precio"] = (
    df["Precio"]
    .str.replace("Â", "", regex=False)   # quita caracteres basura
    .str.replace("£", "", regex=False)   # quita símbolo libra
    .str.strip()
    .astype(float)
)

# Convertir valoraciones a números
mapa_valoraciones = {"One": 1, "Two": 2, "Three": 3, "Four": 4, "Five": 5}  # Volvemos el texto en números
df["Valoración_num"] = df["Valoración"].map(mapa_valoraciones)

# Mostrar resumen
print("Resumen de datos:\n", df.describe())

# Guardar limpio
df.to_csv("data_limpia.csv", index=False, encoding="utf-8")
print("\nDatos limpios guardados en data_limpia.csv ")
