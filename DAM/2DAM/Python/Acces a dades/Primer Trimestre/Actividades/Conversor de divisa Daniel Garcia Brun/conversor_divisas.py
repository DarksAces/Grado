import tkinter as tk
from tkinter import ttk, messagebox
import requests
import xml.etree.ElementTree as ET


# ==========================================
# 1. LÓGICA DE NEGOCIO (BACKEND)
# ==========================================

class GestorDivisas:
    def __init__(self):
        self.url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"
        self.tasas = {}
        self.fecha_actualizacion = "Desconocida"

    def obtener_datos(self):
        """Descarga el XML y extrae las tasas de cambio."""
        print(f"Conectando a {self.url}...")
        try:
            response = requests.get(self.url)
            if response.status_code == 200:
                print("Conexión exitosa.")
                self._parsear_xml(response.content)
                return True
            else:
                print("Error en el servidor.")
                return False
        except Exception as e:
            print(f"Error de conexión: {e}")
            return False

    def _parsear_xml(self, contenido_xml):
        """Procesa el árbol XML para llenar el diccionario de tasas."""
        # Parseamos el XML
        root = ET.fromstring(contenido_xml)

        # Espacios de nombres (Namespace) del XML del BCE
        namespaces = {'gesmes': 'http://www.gesmes.org/xml/2002-08-01',
                      'bce': 'http://www.ecb.int/vocabulary/2002-08-01/eurofxref'}

        # 1. Extraer la fecha (está dentro de Cube > Cube)
        # Buscamos todos los elementos 'Cube' que tengan el atributo 'time'
        cubes = root.findall(".//bce:Cube[@time]", namespaces)
        if cubes:
            self.fecha_actualizacion = cubes[0].attrib['time']

        # 2. Extraer monedas y tasas
        # Iteramos sobre los hijos para encontrar currency y rate
        items = root.findall(".//bce:Cube[@currency]", namespaces)

        # IMPORTANTE: Añadir el Euro base manualmente (1 EUR = 1.0 EUR)
        # Requisito del enunciado
        self.tasas['EUR'] = 1.0

        for item in items:
            moneda = item.attrib['currency']
            tasa = float(item.attrib['rate'])
            self.tasas[moneda] = tasa

    def convertir(self, cantidad, origen, destino):
        """
        Realiza la conversión cruzada usando el Euro como puente.
        Fórmula: (Cantidad / TasaOrigen) * TasaDestino
        """
        if origen not in self.tasas or destino not in self.tasas:
            return None

        tasa_origen = self.tasas[origen]
        tasa_destino = self.tasas[destino]

        # Conversión a Euros (puente)
        cantidad_en_euros = cantidad / tasa_origen

        # Conversión a moneda final
        resultado_final = cantidad_en_euros * tasa_destino
        return resultado_final


# ==========================================
# 2. INTERFAZ GRÁFICA (GUI - TKINTER)
# ==========================================

class AplicacionGUI:
    def __init__(self, root):
        self.gestor = GestorDivisas()
        self.root = root
        self.root.title("Conversor Divisas BCE")
        self.root.geometry("400x350")

        # Variables de control
        self.var_cantidad = tk.StringVar(value="100")
        self.var_resultado = tk.StringVar(value="---")
        self.var_fecha = tk.StringVar(value="Cargando datos...")

        # Construir la interfaz
        self.crear_widgets()

        # Carga automática al iniciar [cite: 48]
        self.cargar_datos_iniciales()

    def crear_widgets(self):
        # Marco principal con padding
        main_frame = ttk.Frame(self.root, padding="20")
        main_frame.pack(fill=tk.BOTH, expand=True)

        # Título y Fecha
        ttk.Label(main_frame, text="Conversor Oficial BCE", font=("Arial", 16, "bold")).grid(row=0, column=0,
                                                                                             columnspan=2, pady=10)
        ttk.Label(main_frame, textvariable=self.var_fecha, foreground="gray").grid(row=1, column=0, columnspan=2,
                                                                                   pady=(0, 20))

        # Entrada de Cantidad
        ttk.Label(main_frame, text="Cantidad:").grid(row=2, column=0, sticky=tk.W)
        entry_cantidad = ttk.Entry(main_frame, textvariable=self.var_cantidad)
        entry_cantidad.grid(row=2, column=1, sticky=tk.EW, pady=5)

        # Selección Moneda Origen
        ttk.Label(main_frame, text="De:").grid(row=3, column=0, sticky=tk.W)
        self.combo_origen = ttk.Combobox(main_frame, state="readonly")
        self.combo_origen.grid(row=3, column=1, sticky=tk.EW, pady=5)

        # Selección Moneda Destino
        ttk.Label(main_frame, text="A:").grid(row=4, column=0, sticky=tk.W)
        self.combo_destino = ttk.Combobox(main_frame, state="readonly")
        self.combo_destino.grid(row=4, column=1, sticky=tk.EW, pady=5)

        # Botón Calcular
        btn_calcular = ttk.Button(main_frame, text="Calcular Conversión", command=self.accion_calcular)
        btn_calcular.grid(row=5, column=0, columnspan=2, pady=20, sticky=tk.EW)

        # Resultado
        lbl_res = ttk.Label(main_frame, textvariable=self.var_resultado, font=("Arial", 14, "bold"),
                            background="#e1e1e1", anchor="center")
        lbl_res.grid(row=6, column=0, columnspan=2, sticky=tk.EW, ipady=10)

    def cargar_datos_iniciales(self):
        exito = self.gestor.obtener_datos()
        if exito:
            monedas = sorted(list(self.gestor.tasas.keys()))
            self.combo_origen['values'] = monedas
            self.combo_destino['values'] = monedas

            # Valores por defecto
            self.combo_origen.set('EUR')
            self.combo_destino.set('USD')
            self.var_fecha.set(f"Datos del: {self.gestor.fecha_actualizacion}")
        else:
            messagebox.showerror("Error", "No se pudo conectar con el BCE.")
            self.var_fecha.set("Error de conexión")

    def accion_calcular(self):
        # Validación de entrada numérica [cite: 73]
        try:
            cantidad = float(self.var_cantidad.get())
        except ValueError:
            messagebox.showerror("Error", "Por favor, introduce un número válido.")
            return

        origen = self.combo_origen.get()
        destino = self.combo_destino.get()

        if not origen or not destino:
            messagebox.showwarning("Atención", "Selecciona ambas monedas.")
            return

        # Lógica matemática de conversión
        resultado = self.gestor.convertir(cantidad, origen, destino)

        # Mostrar resultado formateado
        self.var_resultado.set(f"{resultado:.2f} {destino}")


# ==========================================
# 3. PUNTO DE ENTRADA
# ==========================================
if __name__ == "__main__":
    root = tk.Tk()
    app = AplicacionGUI(root)
    root.mainloop()