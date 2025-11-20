import os
import tkinter as tk
from tkinter import messagebox, scrolledtext
import threading
import sys
from google import genai

# === 1. CONFIGURACIÓN Y CONSTANTES ===

# Configuración de la API y el modelo
API_KEY = os.getenv('Gemini_api')
MODEL_NAME = 'gemini-2.5-flash'
MENU_FILE = 'menu_ajardinados.txt'
PHONE_NUMBER = '+34 933 00 58 16'
# 🎯 URL DE GOOGLE MAPS ACTUALIZADA
GOOGLE_MAPS_URL = 'https://maps.app.goo.gl/5CbZv5Yqg2Ck1DNQ8'

# Estilos de la interfaz
STYLE = {
    "bg": "#f8f9fa",
    "fg_label": "#333",
    "fg_title": "#2f4f4f",
    "font_title": ("Arial", 16, "bold"),
    "font_entry": ("Arial", 11),
    "font_chat": ("Consolas", 10),
    "color_user": "#1E90FF",
    "color_assistant": "#228B22",
    "color_send_btn": "#2f4f4f",
    "color_clear_btn": "#808080",
    "color_exit_btn": "#B22222",
}


# === 2. CLASE PRINCIPAL DEL CHAT ===

class ChatApp:
    def __init__(self, master):
        self.master = master
        master.title("Asistente Virtual - Bar Ajardinados")
        master.geometry("650x600")
        master.configure(bg=STYLE["bg"])

        # 2.1 Inicialización de la API
        self.client = self._inicializar_api()
        if not self.client:
            sys.exit(1)

        # 2.2 Creación de la UI
        self._crear_widgets()
        self.chat_box.insert(tk.END,
                             "[ASISTENTE]: ¡Hola! Soy tu asistente virtual del Bar Ajardinados. ¿En qué puedo ayudarte hoy?\n")

    def _inicializar_api(self):
        if not API_KEY:
            messagebox.showerror("Error de Configuración",
                                 "No se encontró la variable de entorno 'Gemini_api'.")
            return None
        try:
            return genai.Client(api_key=API_KEY)
        except Exception as e:
            messagebox.showerror("Error de Conexión",
                                 f"No se pudo inicializar el cliente Gemini.\nDetalles: {e}")
            return None

    def _crear_widgets(self):
        # Título
        tk.Label(self.master, text="🍽️ Asistente de Consulta - Bar Restaurante AJARDINADOS",
                 font=STYLE["font_title"], bg=STYLE["bg"], fg=STYLE["fg_title"]).pack(pady=15)

        # Chat Box (Salida)
        tk.Label(self.master, text="Historial de Chat:", bg=STYLE["bg"], fg=STYLE["fg_label"]).pack(anchor='w', padx=15)
        self.chat_box = scrolledtext.ScrolledText(self.master, wrap=tk.WORD, width=75, height=18,
                                                  state='disabled', bg="#ffffff", fg="#222", font=STYLE["font_chat"])
        self.chat_box.pack(padx=15, pady=5)
        self.chat_box.tag_configure("asistente", foreground=STYLE["color_assistant"], font=("Arial", 10, "italic"))
        self.chat_box.tag_configure("usuario", foreground=STYLE["color_user"], font=("Arial", 10, "bold"))

        # Entrada de Texto
        tk.Label(self.master, text="Escribe tu pregunta:", bg=STYLE["bg"], fg=STYLE["fg_label"]).pack(anchor='w',
                                                                                                      padx=15)
        self.entrada = tk.Entry(self.master, width=80, font=STYLE["font_entry"])
        self.entrada.pack(padx=15, pady=5)
        self.entrada.bind('<Return>', lambda event: self.enviar_consulta())

        # Botones
        botones = tk.Frame(self.master, bg=STYLE["bg"])
        botones.pack(pady=10)

        tk.Button(botones, text="Enviar Consulta", command=self.enviar_consulta,
                  bg=STYLE["color_send_btn"], fg="white", font=("Arial", 10, "bold"), width=15).pack(side='left',
                                                                                                     padx=5)
        tk.Button(botones, text="Limpiar Chat", command=self.limpiar_chat,
                  bg=STYLE["color_clear_btn"], fg="white", font=("Arial", 10, "bold"), width=12).pack(side='left',
                                                                                                      padx=5)
        tk.Button(botones, text="Salir", command=self.master.destroy,
                  bg=STYLE["color_exit_btn"], fg="white", font=("Arial", 10, "bold"), width=10).pack(side='left',
                                                                                                     padx=5)

    # === 3. MANEJO DE LÓGICA Y API ===

    def _obtener_prompt(self, pregunta_usuario):
        try:
            with open(MENU_FILE, 'r', encoding='utf-8') as f:
                contexto = f.read()
        except FileNotFoundError:
            return f"⚠️ Error: No se encontró el archivo '{MENU_FILE}'. Asegúrate de que exista en el directorio."

        # El prompt ahora incluye la URL de Maps en las instrucciones.
        return f"""
        Eres el asistente virtual del **Bar Restaurante AJARDINADOS**.
        - Responde de forma amable, clara y profesional, como un camarero.
        - Usa la información del CONTEXTO. La página de Google Maps del bar es: {GOOGLE_MAPS_URL}
        - Si te preguntan por el menú del día, indica que los platos y el precio varían diariamente
          y que deben llamar al {PHONE_NUMBER} para saber el menú de hoy.
        - Si no tienes la información en el contexto, responde con amabilidad indicando que no la tienes
          y sugiere contactar directamente con el bar.

        --- CONTEXTO ---
        {contexto}
        --- FIN DEL CONTEXTO ---

        Pregunta del cliente: {pregunta_usuario}
        """

    def _ejecutar_api_en_hilo(self, pregunta):
        prompt = self._obtener_prompt(pregunta)
        if prompt.startswith("⚠️ Error:"):
            respuesta = prompt
        else:
            try:
                response = self.client.models.generate_content(
                    model=MODEL_NAME,
                    contents=prompt
                )
                respuesta = response.text.strip()
            except Exception as e:
                respuesta = f"🚫 Error al consultar la API: {e}"

        self.master.after(0, self._mostrar_respuesta, respuesta)

    def _mostrar_respuesta(self, respuesta):
        self.chat_box.config(state='normal')

        # Borra la línea "Pensando..."
        self.chat_box.delete("end-2l", "end-1l")

        # Inserta respuesta con formato
        self.chat_box.insert(tk.END, f"[ASISTENTE]: {respuesta}\n", "asistente")

        self.chat_box.see(tk.END)
        self.chat_box.config(state='disabled')

    def enviar_consulta(self, event=None):
        pregunta = self.entrada.get().strip()
        if not pregunta:
            messagebox.showwarning("Advertencia", "Por favor, escribe una pregunta antes de enviar.")
            return

        self.chat_box.config(state='normal')
        self.chat_box.insert(tk.END, f"\n[TÚ]: {pregunta}\n", "usuario")
        self.chat_box.insert(tk.END, "[ASISTENTE]: Pensando...\n")
        self.chat_box.config(state='disabled')
        self.entrada.delete(0, tk.END)
        self.chat_box.see(tk.END)

        # Ejecutar API en un hilo separado
        threading.Thread(target=self._ejecutar_api_en_hilo, args=(pregunta,)).start()

    def limpiar_chat(self):
        self.chat_box.config(state='normal')
        self.chat_box.delete(1.0, tk.END)
        self.chat_box.insert(tk.END, "[ASISTENTE]: Chat reiniciado. ¿En qué puedo ayudarte hoy?\n", "asistente")
        self.chat_box.config(state='disabled')


# === 4. PUNTO DE ENTRADA ===
if __name__ == "__main__":
    root = tk.Tk()
    app = ChatApp(root)
    root.mainloop()