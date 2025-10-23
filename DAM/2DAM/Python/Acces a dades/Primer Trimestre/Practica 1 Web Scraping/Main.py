import requests   # Requests obtiene la página
from bs4 import BeautifulSoup  #BeautifulSoup la analiza
import csv #csv guarda los datos

#Primero indicamos la página que queremos que busque
URL= "https://books.toscrape.com/catalogue/page-{}.html" # URL con {} sirve para recorrer múltiples páginas reemplazando {} por el número de página
data = []

#Hacemos un bucle diciendo que tantas páginas queremos
for page in range(1, 4):  #Para no petar mucho la página vamos a buscar solo las 3 primeras
    response = requests.get(URL.format(page)) #Lancamos la solicitud a la web
    soup = BeautifulSoup(response.text, "html.parser") #Convertimos el HTML en un objeto python que podamos usar
    books = soup.find_all("article",  class_="product_prod")