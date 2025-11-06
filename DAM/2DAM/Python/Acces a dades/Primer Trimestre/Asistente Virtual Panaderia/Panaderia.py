import os
import tkinter as tk
from tkinter import messagebox, scrolledtext
import threading
import sys
from google import genai

# === 1. INICIALIZACIÓN Y SEGURIDAD ===

API_KEY = os.getenv('Gemini_api')
if not API_KEY:
    messagebox.showerror(
        "Error de Configuración",
        "No se encontró la variable de entorno 'Gemini_api'. El programa se cerrará."
    )
    sys.exit(1)

try:
    CLIENT = genai.Client(api_key=API_KEY)
except Exception as e:
    messagebox.showerror(
        "Error de Conexión",
        f"No se pudo inicializar el cliente Gemini.\nDetalles: {e}"
    )
    sys.exit(1)


# === 2. FUNCIÓN PRINCIPAL DE LA API ===

def obtener_respuesta_bar(pregunta_usuario):
    """Genera una respuesta contextual basada en el archivo 'menu_ajardinados.txt'."""

    try:
        with open('menu_ajardinados.txt', 'r', encoding='utf-8') as f:
            contexto = f.read()
    except FileNotFoundError:
        return "⚠️ Error: No se encontró el archivo 'menu_ajardinados.txt'. Asegúrate de que exista en el directorio."

    prompt = f"""
    Eres el asistente virtual del **Bar Restaurante AJARDINADOS**.
    - Responde de forma amable, clara y profesional, como un camarero.
    - Usa SOLO la información del CONTEXTO.
    - Si te preguntan por el menú del día, indica que los platos y el precio varían diariamente
      y que deben llamar al +34 933 00 58 16 para saber el menú de hoy.
    - Si no tienes la información en el contexto, responde con amabilidad indicando que no la tienes
      y sugiere contactar directamente con el bar.

    --- CONTEXTO ---
    {contexto}
    --- FIN DEL CONTEXTO ---

    Pregunta del cliente: {pregunta_usuario}
    """

    try:
        # ✅ Eliminamos request_options (causaba el error)
        response = CLIENT.models.generate_content(
            model='gemini-2.5-flash',
            contents=prompt
        )
        return response.text.strip()
    except Exception as e:
        return f"🚫 Error al consultar la API: {e}"


# === 3. MANEJO DE CHAT Y HILOS ===

def mostrar_respuesta(respuesta, salida_widget):
    salida_widget.config(state='normal')

    # Borra la línea "Pensando..."
    salida_widget.delete("end-2l", "end-1l")

    # Inserta respuesta con formato
    salida_widget.tag_configure("asistente", foreground="#228B22", font=("Arial", 10, "italic"))
    salida_widget.insert(tk.END, f"[ASISTENTE]: {respuesta}\n", "asistente")

    salida_widget.see(tk.END)
    salida_widget.config(state='disabled')


def ejecutar_api_en_hilo(pregunta, salida_widget):
    respuesta = obtener_respuesta_bar(pregunta)
    mostrar_respuesta(respuesta, salida_widget)


def enviar_consulta(entrada_widget, salida_widget):
    pregunta = entrada_widget.get().strip()
    if not pregunta:
        messagebox.showwarning("Advertencia", "Por favor, escribe una pregunta antes de enviar.")
        return

    salida_widget.config(state='normal')

    # Inserta pregunta del usuario con formato
    salida_widget.tag_configure("usuario", foreground="#1E90FF", font=("Arial", 10, "bold"))
    salida_widget.insert(tk.END, f"\n[TÚ]: {pregunta}\n", "usuario")
    salida_widget.insert(tk.END, "[ASISTENTE]: Pensando...\n")

    salida_widget.config(state='disabled')
    entrada_widget.delete(0, tk.END)
    salida_widget.see(tk.END)

    hilo = threading.Thread(target=ejecutar_api_en_hilo, args=(pregunta, salida_widget))
    hilo.start()


# === 4. FUNCIONES AUXILIARES ===

def limpiar_chat(widget):
    widget.config(state='normal')
    widget.delete(1.0, tk.END)
    widget.insert(tk.END, "[ASISTENTE]: Chat reiniciado. ¿En qué puedo ayudarte hoy?\n", "asistente")
    widget.config(state='disabled')


# === 5. INTERFAZ PRINCIPAL (TKINTER) ===

def crear_interfaz():
    root = tk.Tk()
    root.title("Asistente Virtual - Bar Ajardinados")
    root.geometry("650x600")
    root.configure(bg="#f8f9fa")

    tk.Label(
        root,
        text="🍽️ Asistente de Consulta - Bar Restaurante AJARDINADOS",
        font=("Arial", 16, "bold"),
        bg="#f8f9fa",
        fg="#2f4f4f"
    ).pack(pady=15)

    tk.Label(root, text="Historial de Chat:", bg="#f8f9fa", fg="#333").pack(anchor='w', padx=15)
    chat_box = scrolledtext.ScrolledText(
        root,
        wrap=tk.WORD,
        width=75,
        height=18,
        state='disabled',
        bg="#ffffff",
        fg="#222",
        font=("Consolas", 10)
    )
    chat_box.pack(padx=15, pady=5)
    chat_box.insert(tk.END, "[ASISTENTE]: ¡Hola! Soy tu asistente virtual del Bar Ajardinados. ¿En qué puedo ayudarte hoy?\n")

    tk.Label(root, text="Escribe tu pregunta:", bg="#f8f9fa", fg="#333").pack(anchor='w', padx=15)
    entrada = tk.Entry(root, width=80, font=("Arial", 11))
    entrada.pack(padx=15, pady=5)

    botones = tk.Frame(root, bg="#f8f9fa")
    botones.pack(pady=10)

    tk.Button(
        botones, text="Enviar Consulta",
        command=lambda: enviar_consulta(entrada, chat_box),
        bg="#2f4f4f", fg="white", font=("Arial", 10, "bold"), width=15
    ).pack(side='left', padx=5)

    tk.Button(
        botones, text="Limpiar Chat",
        command=lambda: limpiar_chat(chat_box),
        bg="#808080", fg="white", font=("Arial", 10, "bold"), width=12
    ).pack(side='left', padx=5)

    tk.Button(
        botones, text="Salir",
        command=root.destroy,
        bg="#B22222", fg="white", font=("Arial", 10, "bold"), width=10
    ).pack(side='left', padx=5)

    root.mainloop()


# === 6. PUNTO DE ENTRADA ===
if __name__ == "__main__":
    crear_interfaz()
