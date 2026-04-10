# 💰 Currency Converter: XML Data Parsing & GUI
# 💰 Conversor de Divisas: Parseo de XML y GUI

## 📋 Description | Descripción

A functional currency converter application that parses exchange rate data from a local **XML file** (sourced from the European Central Bank). It features a clean **Tkinter GUI** for real-time calculations between dozens of global currencies.

Una aplicación funcional de conversión de divisas que parsea datos de tipos de cambio de un **archivo XML** local (obtenido del Banco Central Europeo). Cuenta con una interfaz **GUI en Tkinter** limpia para realizar cálculos en tiempo real entre decenas de monedas globales.

---

## ✨ Key Features | Características Clave

- **XML Data Ingestion:** Uses `xml.etree.ElementTree` to navigate and extract data from the ECB's currency reference files. | *Ingesta de datos XML: Usa `xml.etree.ElementTree` para navegar y extraer datos de los archivos de referencia de divisas del BCE.*
- **Historical Context:** Dynamically extracts the last update timestamp from the XML metadata. | *Contexto histórico: Extrae dinámicamente la marca de tiempo de la última actualización de los metadatos del XML.*
- **Smart Conversion Logic:** Uses Euro (EUR) as a base pivot to calculate rates between any two selected currencies accurately. | *Lógica de conversión inteligente: Usa el Euro (EUR) como pivote base para calcular los tipos de cambio entre dos monedas seleccionadas con precisión.*
- **Bilingual GUI:** Friendly interface with error handling for non-numeric inputs and missing files. | *GUI Bilingüe: Interfaz amigable con manejo de errores para entradas no numéricas y archivos faltantes.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Python
- **GUI:** Tkinter / ttk
- **Data Format:** XML (eXtensible Markup Language)
- **Library:** `xml.etree.ElementTree`

---

## 📂 Project Structure | Estructura del Proyecto

- **`conversor_divisas.py`**: The main script containing the business logic and UI layout. | *El script principal que contiene la lógica de negocio y el diseño de la interfaz.*
- **`conversor.xml`**: Local XML database containing the currency rates. | *Base de datos XML local que contiene los tipos de cambio.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Ensure you have **Python 3.x** installed. | *Asegúrate de tener **Python 3.x** instalado.*
2.  Place `conversor_divisas.py` and `conversor.xml` in the same directory. | *Coloca `conversor_divisas.py` y `conversor.xml` en el mismo directorio.*
3.  Run the application: `python conversor_divisas.py`. | *Ejecuta la aplicación: `python conversor_divisas.py`.*
