import tkinter as tk
from tkinter import ttk, messagebox
import xml.etree.ElementTree as ET
import os

# --- VARIABLES GLOBALES y CONFIGURACIÓN ---
NOMBRE_ARCHIVO_XML = "conversor.xml"
TASAS = {}
FECHA_ACTUALIZACION = "Desconocida"

# ==========================================
# 1. LÓGICA DE NEGOCIO (Funciones)
# ==========================================

def _parsear_xml(contenido_xml):
    """Procesa el árbol XML del BCE para llenar las tasas globales."""
    global TASAS, FECHA_ACTUALIZACION
    
    root = ET.fromstring(contenido_xml)
    namespaces = {'bce': 'http://www.ecb.int/vocabulary/2002-08-01/eurofxref'}
    
    # Extraer la fecha
    cubes = root.findall(".//bce:Cube[@time]", namespaces)
    if cubes:
        FECHA_ACTUALIZACION = cubes[0].attrib['time']

    # Añadir el Euro base
    TASAS['EUR'] = 1.0

    # Extraer monedas y tasas
    items = root.findall(".//bce:Cube[@currency]", namespaces)
    for item in items:
        moneda = item.attrib['currency']
        tasa = float(item.attrib['rate'])
        TASAS[moneda] = tasa

def cargar_datos_locales():
    """Carga y parsea las tasas desde un archivo XML local."""
    archivo_path = os.path.join(os.path.dirname(__file__), NOMBRE_ARCHIVO_XML)
    print(f"Buscando archivo: {archivo_path}...")
    
    if not os.path.exists(archivo_path):
        print(f"Error: No se encontró el archivo '{NOMBRE_ARCHIVO_XML}'.")
        return False

    try:
        with open(archivo_path, 'rb') as f:
            contenido_xml = f.read()
        _parsear_xml(contenido_xml)
        print("Datos cargados exitosamente.")
        return True
    except Exception as e:
        print(f"Error al leer o parsear el XML: {e}")
        return False

def convertir(cantidad, origen, destino):
    """Realiza la conversión usando las TASAS globales."""
    if origen not in TASAS or destino not in TASAS:
        return None

    tasa_origen = TASAS[origen]
    tasa_destino = TASAS[destino]

    cantidad_en_euros = cantidad / tasa_origen
    resultado_final = cantidad_en_euros * tasa_destino
    return resultado_final

# ==========================================
# 2. INTERFAZ GRÁFICA (Funciones)
# ==========================================

def crear_widgets(root, var_cantidad, var_resultado, var_fecha):
    """
    Define y coloca todos los elementos de la interfaz.
    IMPORTANTE: Todos los widgets que usan grid() son hijos del main_frame.
    """
    
    # 1. MAIN FRAME (Gestionado por PACK() en la ventana root)
    main_frame = ttk.Frame(root, padding="20")
    main_frame.pack(fill=tk.BOTH, expand=True)

    # Título y Fecha (Hijos de main_frame, usan grid)
    ttk.Label(main_frame, text="Conversor XML Local", font=("Arial", 16, "bold")).grid(row=0, column=0, columnspan=2, pady=10)
    ttk.Label(main_frame, textvariable=var_fecha, foreground="gray", font=("Arial", 9)).grid(row=1, column=0, columnspan=2, pady=(0, 15))

    # Entrada de Cantidad (Hijo de main_frame, usa grid)
    ttk.Label(main_frame, text="Cantidad:").grid(row=2, column=0, sticky=tk.W, pady=5)
    ttk.Entry(main_frame, textvariable=var_cantidad).grid(row=2, column=1, sticky=tk.EW, pady=5)

    # Selección Moneda Origen (Hijo de main_frame, usa grid)
    ttk.Label(main_frame, text="De:").grid(row=3, column=0, sticky=tk.W, pady=5)
    # <<-- EL CAMBIO CLAVE: combobox es hijo de main_frame -->>
    combo_origen = ttk.Combobox(main_frame, state="readonly") 
    combo_origen.grid(row=3, column=1, sticky=tk.EW, pady=5)

    # Selección Moneda Destino (Hijo de main_frame, usa grid)
    ttk.Label(main_frame, text="A:").grid(row=4, column=0, sticky=tk.W, pady=5)
    # <<-- EL CAMBIO CLAVE: combobox es hijo de main_frame -->>
    combo_destino = ttk.Combobox(main_frame, state="readonly")
    combo_destino.grid(row=4, column=1, sticky=tk.EW, pady=5)

    # Botón Calcular
    btn_calcular = ttk.Button(main_frame, text="Calcular Conversión", 
                              command=lambda: accion_calcular(var_cantidad, var_resultado, combo_origen, combo_destino))
    btn_calcular.grid(row=5, column=0, columnspan=2, pady=15, sticky=tk.EW)

    # Resultado
    ttk.Label(main_frame, text="Resultado:", font=("Arial", 10)).grid(row=6, column=0, sticky=tk.W, pady=(5, 0))
    ttk.Label(main_frame, textvariable=var_resultado, font=("Arial", 18, "bold"), 
              background="#ffffff", foreground="#007bff", anchor="center", relief="solid").grid(row=7, column=0, columnspan=2, sticky=tk.EW, ipady=10, pady=(0, 0))
    
    return combo_origen, combo_destino


def accion_calcular(var_cantidad, var_resultado, combo_origen, combo_destino):
    """Función que se llama al pulsar el botón de calcular."""
    global TASAS
    
    try:
        cantidad = float(var_cantidad.get())
    except ValueError:
        messagebox.showerror("Error", "Introduce un número válido en la cantidad.")
        return

    origen = combo_origen.get()
    destino = combo_destino.get()

    if not origen or not destino or not TASAS:
        var_resultado.set("---")
        return

    resultado = convertir(cantidad, origen, destino)

    if resultado is not None:
        var_resultado.set(f"{resultado:,.4f} {destino}")
    else:
        var_resultado.set("Error de datos")


def iniciar_aplicacion():
    """Punto de entrada principal para ejecutar la GUI."""
    global TASAS, FECHA_ACTUALIZACION
    
    root = tk.Tk()
    root.title("💰 Conversor XML Local")
    root.geometry("400x320")

    # Variables de control de Tkinter
    var_cantidad = tk.StringVar(value="100")
    var_resultado = tk.StringVar(value="---")
    var_fecha = tk.StringVar(value="Cargando datos...")
    
    # 1. Construir la interfaz (creando los comboboxes como hijos del frame)
    combo_origen, combo_destino = crear_widgets(root, var_cantidad, var_resultado, var_fecha)

    # 2. Cargar datos
    exito = cargar_datos_locales()
    
    if exito:
        monedas = sorted(list(TASAS.keys()))
        combo_origen['values'] = monedas
        combo_destino['values'] = monedas

        # Valores por defecto
        combo_origen.set('EUR')
        combo_destino.set('USD')
        var_fecha.set(f"Datos del BCE | Actualización: {FECHA_ACTUALIZACION}")
        
        # Cálculo inicial
        accion_calcular(var_cantidad, var_resultado, combo_origen, combo_destino)
    else:
        messagebox.showerror("Error de Carga", f"No se pudo cargar el archivo '{NOMBRE_ARCHIVO_XML}'.")
        var_fecha.set("❌ Error: Archivo no encontrado")
        
    root.mainloop()

# ==========================================
# 3. PUNTO DE ENTRADA
# ==========================================
if __name__ == "__main__":
    iniciar_aplicacion()