import streamlit as st  #IMPORTANTE NO  OLVIDARME para ejecutar el dashboard al usar streamlit necesito ejecutarlo: streamlit run dashboard.py
import pandas as pd

# --- Configuración general ---
st.set_page_config(page_title="Dashboard de Libros", layout="wide")

st.title("📚 Dashboard de Libros")
st.markdown("Visualización de datos extraídos de [books.toscrape.com](https://books.toscrape.com/)")

# --- Cargar datos ---
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

# --- Filtros generales ---
valoracion = st.slider("⭐ Filtrar por valoración mínima", 1, 5, 3)
df_filtrado = df[df["Valoración_num"] >= valoracion]

# --- Filtro por genero ---
generos = df_filtrado["Genero"].unique()
genero_seleccionado = st.selectbox(" Filtrar por Genero", ["Todos"] + list(generos))

if genero_seleccionado != "Todos":
    df_filtrado = df_filtrado[df_filtrado["Genero"] == genero_seleccionado]

# --- Filtro de "más vendidos" (simulado por valoración) ---
if st.checkbox("🔥 Mostrar solo los más vendidos (mayor valoración)"):
    df_filtrado = df_filtrado[df_filtrado["Valoración_num"] == df_filtrado["Valoración_num"].max()]

# --- Mostrar tabla filtrada ---
st.subheader("📋 Libros filtrados")
st.dataframe(df_filtrado)

# --- Métricas globales ---
st.subheader("📈 Estadísticas globales")
col1, col2, col3 = st.columns(3)
col1.metric("Precio medio (£)", round(df_filtrado["Precio"].mean(), 2))
col2.metric("Total de libros", len(df_filtrado))
col3.metric("Valoración media", round(df_filtrado["Valoración_num"].mean(), 2))

# --- Métricas por genero ---
st.subheader("Métricas por genero")
if genero_seleccionado != "Todos":
    col1, col2, col3 = st.columns(3)
    col1.metric("Libros del genero", len(df_filtrado))
    col2.metric("Precio medio del genero (£)", round(df_filtrado["Precio"].mean(), 2))
    col3.metric("Valoración media del genero", round(df_filtrado["Valoración_num"].mean(), 2))
else:
    st.info("Selecciona un genero para ver sus métricas específicas.")

# --- Gráfico ---
st.subheader("💰 Distribución de precios")
st.bar_chart(df_filtrado["Precio"])
