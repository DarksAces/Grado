# Colores ANSI
color_titulo   = "\033[36m"   # Amarillo brillante
color_producto = "\033[96m"   # Cian claro
color_precio   = "\033[91m"   # Rojo claro
reset          = "\033[0m"    # Reset

Producto = ["Manzana", "Plátano", "Naranja", "Pera", "Mango"]
Precio   = ["0.50", "0.30", "0.40", "0.60", "1.20"]

# Ancho de columnas
col_width = 10

# Títulos con color
print(color_titulo + "Producto".ljust(col_width) + "|" + "Precio (€)".ljust(col_width) + reset)

# Línea separadora
print("-" * (col_width * 2 + 1))

# Filas alineadas a la izquierda con colores
for i in range(len(Producto)):
    print(color_producto + Producto[i].ljust(col_width) + reset + "|" +
          color_precio + Precio[i].ljust(col_width) + reset)
