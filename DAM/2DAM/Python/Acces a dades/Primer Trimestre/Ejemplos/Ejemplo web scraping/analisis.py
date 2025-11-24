import pandas as pd

# Cargar datos con manejo de codificación
df = pd.read_csv("data.csv", encoding="utf-8")

# Limpiar símbolos extraños y convertir precios
df["Precio"] = (
    df["Precio"]
    .str.replace("Â", "", regex=False)   # quita caracteres basura
    .str.replace("£", "", regex=False)   # quita símbolo libra
    .str.strip()
    .astype(float)
)

# Convertir valoraciones a números
mapa_valoraciones = {"One": 1, "Two": 2, "Three": 3, "Four": 4, "Five": 5}
df["Valoración_num"] = df["Valoración"].map(mapa_valoraciones)

# Mostrar resumen
print("Resumen de datos:\n", df.describe())

# Guardar limpio
df.to_csv("data_limpia.csv", index=False, encoding="utf-8")
print("\nDatos limpios guardados en data_limpia.csv ✅")
