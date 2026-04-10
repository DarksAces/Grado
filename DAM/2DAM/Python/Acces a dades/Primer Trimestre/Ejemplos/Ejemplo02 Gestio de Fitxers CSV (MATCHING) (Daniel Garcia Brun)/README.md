# 📊 CSV Matcher: VLOOKUP-style Logic in Python
# 📊 CSV Matcher: Lógica tipo BuscarV en Python

## 📋 Description | Descripción

A data processing utility designed to synchronize and merge multiple **CSV** files. It implements a Python-based alternative to the **Excel VLOOKUP (BuscarV)** function, allowing for automated data matching between different academic units (UF1, UF2) using unique student identifiers.

Una utilidad de procesamiento de datos diseñada para sincronizar y fusionar múltiples archivos **CSV**. Implementa una alternativa basada en Python a la función **BuscarV de Excel**, permitiendo el emparejamiento automatizado de datos entre diferentes unidades académicas (UF1, UF2) usando identificadores únicos de alumnos.

---

## ✨ Key Features | Características Clave

- **Automated Data Merging:** Logic to read multiple CSV sources and combine them into a single consolidated report. | *Fusión Automatizada: Lógica para leer múltiples fuentes CSV y combinarlas en un único informe consolidado.*
- **Identifier Matching:** Specialized search algorithm (`BuscarV.py`) that handles row alignment based on primary keys (Student IDs). | *Emparejamiento por Identificador: Algoritmo de búsqueda especializado que gestiona la alineación de filas.*
- **Grade Management:** Specifically tailored for academic grade tracking across different terms and subjects. | *Gestión de Notas: Adaptado específicamente para el seguimiento de notas académicas.*
- **CSV Handling:** Robust use of Python's built-in `csv` module for reading and writing structured data. | *Manejo de CSV: Uso robusto del módulo nativo `csv` de Python.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Python 3.9+
- **Modules:** `csv`, `os`.
- **Data Format:** CSV (Comma Separated Values).

---

## 📂 Project Structure | Estructura del Proyecto

- **`BuscarV.py`**: The core logic for data matching and file generation. | *La lógica central para el emparejamiento de datos y generación de archivos.*
- **`Notas_Alumnos_UF1.csv` / `UF2.csv`**: Raw data sources from different terms. | *Fuentes de datos de diferentes trimestres.*
- **`notas_alumnos.csv`**: The final consolidated output file. | *El archivo consolidado final.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Ensure the raw `.csv` files are in the same directory as the script. | *Asegúrate de que los archivos CSV estén en el mismo directorio.*
2.  Run the matcher: `python BuscarV.py`. | *Ejecuta el matcher: `python BuscarV.py`.*
3.  Check the generated `notas_alumnos.csv` for the consolidated results. | *Verifica los resultados en el archivo generado.*
