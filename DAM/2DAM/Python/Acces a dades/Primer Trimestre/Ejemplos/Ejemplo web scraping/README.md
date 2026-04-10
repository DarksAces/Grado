# 🕸️ BookScraper: Web Scraping & Data Dashboard
# 🕸️ BookScraper: Web Scraping y Dashboard de Datos

## 📋 Description | Descripción

A streamlined Data Engineering pipeline that extracts information from the [Books to Scrape](https://books.toscrape.com/) sandbox. It demonstrates automated data collection using **Requests** and **BeautifulSoup**, data cleaning with **Pandas**, and reactive visualization through a **Streamlit** dashboard.

Un pipeline de Ingeniería de Datos optimizado que extrae información del sandbox [Books to Scrape](https://books.toscrape.com/). Demuestra la recolección automatizada de datos usando **Requests** y **BeautifulSoup**, limpieza de datos con **Pandas** y visualización reactiva a través de un dashboard de **Streamlit**.

---

## ✨ Key Features | Características Clave

- **Automated Scraping:** Crawls multiple pages to extract titles, prices, ratings, and stock availability. | *Scraping Automatizado: Rastrea múltiples páginas para extraer títulos, precios, valoraciones y disponibilidad.*
- **Data Pipeline:** Includes a transformation script (`analisis.py`) that converts raw text into structured numerical data for analysis. | *Pipeline de Datos: Incluye un script de transformación que convierte texto plano en datos numéricos estructurados.*
- **Interactive Dashboard:** A professional **Streamlit** interface with real-time filters (minimum rating), key metrics (average price), and distribution charts. | *Dashboard Interactivo: Una interfaz profesional con filtros en tiempo real, métricas clave y gráficos de distribución.*
- **Robustness:** Handles missing files and data inconsistencies gracefully with specialized error messages. | *Robustez: Maneja la ausencia de archivos e inconsistencias de datos con mensajes de error especializados.*

---

## 🛠️ Tech Stack | Tecnologías

- **Environment:** Python 3.9+
- **Scraping:** `BeautifulSoup4`, `Requests`.
- **Data Analysis:** `Pandas`.
- **Visualization:** `Streamlit`.

---

## 📂 Project Structure | Estructura del Proyecto

- **`Main.py`**: The web scraper orchestrator. | *El orquestador del web scraper.*
- **`analisis.py`**: Data cleaning and CSV export logic. | *Lógica de limpieza de datos y exportación a CSV.*
- **`dashboard.py`**: The interactive Streamlit UI. | *La interfaz de usuario interactiva de Streamlit.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Install dependencies: `pip install requests beautifulsoup4 pandas streamlit`. | *Instala las dependencias.*
2.  Run the scraper: `python Main.py`. | *Ejecuta el scraper.*
3.  Clean the data: `python analisis.py`. | *Limpia los datos.*
4.  Launch the dashboard: `streamlit run dashboard.py`. | *Lanza el dashboard.*
