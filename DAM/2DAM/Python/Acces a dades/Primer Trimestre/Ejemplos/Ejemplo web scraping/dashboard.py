import streamlit as st
import pandas as pd

st.set_page_config(page_title="Dashboard de Libros", layout="wide")

st.title("📚 Dashboard de libros")
st.markdown("Visualización de datos extraídos de [books.toscrape.com](https://books.toscrape.com/)")

try:
    df = pd.read_csv("data_limpia.csv", encoding="utf-8")
    st.success("✅ Datos cargados correctamente")
except FileNotFoundError:
    st.error("❌ No se encontró el archivo `data_limpia.csv`. Ejecuta primero `analisis.py`.")
    st.stop()
except Exception as e:
    st.error(f"⚠️ Error al cargar el archivo: {e}")
    st.stop()

if df.empty:
    st.warning("⚠️ El archivo está vacío o no tiene datos.")
    st.stop()

# Filtro
valoracion = st.slider("Filtrar por valoración mínima", 1, 5, 3)
df_filtrado = df[df["Valoración_num"] >= valoracion]

# Mostrar tabla
st.subheader("📋 Libros filtrados")
st.dataframe(df_filtrado)

# Métricas
st.subheader("📈 Estadísticas")
col1, col2 = st.columns(2)
col1.metric("Precio medio (£)", round(df_filtrado["Precio"].mean(), 2))
col2.metric("Total de libros", len(df_filtrado))

# Gráfico
st.subheader("💰 Distribución de precios")
st.bar_chart(df_filtrado["Precio"])
