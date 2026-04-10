# 📚 Books Scraper & Dashboard: Web Scraping to Insights
# 📚 Scraper y Dashboard de Libros: De Web Scraping a Visualización

## 📋 Description | Descripción

A complete data pipeline project that demonstrates **Web Scraping**, **Data Cleaning**, and **Interactive Visualization**. It extracts book data from a live site, processes it with Pandas, and displays results in a modern Streamlit dashboard.

Un proyecto completo de pipeline de datos que demuestra **Web Scraping**, **Limpieza de Datos** y **Visualización Interactiva**. Extrae datos de libros de un sitio en vivo, los procesa con Pandas y muestra los resultados en un dashboard moderno de Streamlit.

---

## ✨ Key Features | Características Clave

- **Automated Scraping:** Uses `Requests` and `BeautifulSoup` to crawl multiple pages and extract titles, prices, ratings, and genres. | *Scraping Automatizado: Usa `Requests` y `BeautifulSoup` para rastrear múltiples páginas y extraer títulos, precios, valoraciones y géneros.*
- **Data Engineering:** Automated cleaning process using **Pandas** to handle currency symbols, text-to-numeric mapping for ratings, and CSV persistence. | *Ingeniería de Datos: Proceso de limpieza automatizado usando **Pandas** para manejar símbolos de moneda, mapeo de texto a numérico para valoraciones y persistencia en CSV.*
- **Interactive Dashboard:** built with **Streamlit**, allowing users to filter by minimum rating, genre, and view global metrics in real-time. | *Dashboard Interactivo: Construido con **Streamlit**, permitiendo a los usuarios filtrar por valoración mínima, género y ver métricas globales en tiempo real.*
- **Data Visuals:** Includes bar charts for price distributions and KPI metrics for average prices and stock counts. | *Visualizaciones de Datos: Incluye gráficos de barras para distribuciones de precios y métricas KPI para precios medios y conteo de existencias.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Python
- **Scraping:** BeautifulSoup4, Requests
- **Data Analysis:** Pandas
- **Visualization:** Streamlit
- **Format:** CSV

---

## 📂 Project Structure | Estructura del Proyecto

- **`Main.py`**: The scraper and data cleaning script. | *El script del scraper y la limpieza de datos.*
- **`dashboard.py`**: The Streamlit application for data visualization. | *La aplicación Streamlit para la visualización de datos.*
- **`data_limpia.csv`**: The processed dataset used by the dashboard. | *El conjunto de datos procesado utilizado por el dashboard.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Install dependencies: `pip install requests beautifulsoup4 pandas streamlit`. | *Instala las dependencias: `pip install requests beautifulsoup4 pandas streamlit`.*
2.  Run the scraper first: `python Main.py`. | *Ejecuta primero el scraper: `python Main.py`.*
3.  Launch the dashboard: `streamlit run dashboard.py`. | *Lanza el dashboard: `streamlit run dashboard.py`.*
