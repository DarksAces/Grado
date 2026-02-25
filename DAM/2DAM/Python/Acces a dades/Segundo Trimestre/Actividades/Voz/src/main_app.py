import tkinter as tk
from tkinter import messagebox, scrolledtext
from src.voice_service import VoiceService
from src.auth_dao import AuthDAO
from datetime import datetime

class VoiceAuditApp:
    def __init__(self, root):
        self.root = root
        self.root.title("VoiceAudit - Sistema de Acceso Seguro")
        self.root.geometry("600x500")
        
        from src.config import Config
        self.voice_service = VoiceService(device_indices=Config.MIC_INDICES)
        self.dao = AuthDAO()
        
        self.create_widgets()

    def create_widgets(self):
        # Panel superior
        tk.Label(self.root, text="VoiceAudit Login", font=("Arial", 18, "bold")).pack(pady=10)
        
        # Formulario
        frame = tk.Frame(self.root)
        frame.pack(pady=10)
        
        tk.Label(frame, text="Usuario:").grid(row=0, column=0, padx=5, pady=5)
        self.ent_username = tk.Entry(frame)
        self.ent_username.grid(row=0, column=1, padx=5, pady=5)
        
        # Botones
        btn_frame = tk.Frame(self.root)
        btn_frame.pack(pady=10)
        
        tk.Button(btn_frame, text="Registrar (Enrolamiento)", command=self.registrar).grid(row=0, column=0, padx=10)
        tk.Button(btn_frame, text="Iniciar Sesión", command=self.login).grid(row=0, column=1, padx=10)
        tk.Button(btn_frame, text="Ver Auditoría Crítica", command=self.mostrar_auditoria).grid(row=0, column=2, padx=10)
        
        # Area de logs/consola
        tk.Label(self.root, text="Estado del Sistema:").pack()
        self.txt_console = scrolledtext.ScrolledText(self.root, width=70, height=15)
        self.txt_console.pack(pady=5)

    def log_message(self, msg):
        self.txt_console.insert(tk.END, f"[{datetime.now().strftime('%H:%M:%S')}] {msg}\n")
        self.txt_console.see(tk.END)

    def registrar(self):
        username = self.ent_username.get()
        if not username:
            messagebox.showwarning("Error", "Introduce un nombre de usuario")
            return
        
        import threading
        self.log_message(f"Iniciando registro para {username}...")
        threading.Thread(target=self._registrar_async, args=(username,), daemon=True).start()

    def _registrar_async(self, username):
        texto, confianza, latencia = self.voice_service.escuchar()
        # Volvemos al hilo principal para mostrar diálogos y actualizar UI
        self.root.after(0, lambda: self._registrar_post_escucha(username, texto, confianza, latencia))

    def _registrar_post_escucha(self, username, texto, confianza, latencia):
        if texto:
            confirm = messagebox.askyesno("Confirmar Frase", f"¿Es esta tu frase?\n\"{texto}\"")
            if confirm:
                if self.dao.registrar_usuario(username, texto):
                    self.log_message("Registro exitoso.")
                    messagebox.showinfo("Éxito", "Usuario registrado correctamente")
                else:
                    self.log_message("Error en base de datos.")
            else:
                self.log_message("Registro cancelado por el usuario.")
        else:
            self.log_message("No se pudo capturar la voz.")

    def login(self):
        username = self.ent_username.get()
        usuario = self.dao.obtener_usuario(username)
        
        if not usuario:
            messagebox.showerror("Error", "Usuario no existe")
            return
        
        u_id, u_name, u_passphrase, u_fallos, u_bloqueo = usuario
        
        if u_bloqueo and u_bloqueo > datetime.now():
            messagebox.showerror("Bloqueo", f"Cuenta bloqueada hasta {u_bloqueo}")
            return

        import threading
        self.log_message(f"Intentando login para {username}...")
        threading.Thread(target=self._login_async, args=(u_id, username, u_passphrase, u_fallos), daemon=True).start()

    def _login_async(self, u_id, username, u_passphrase, u_fallos):
        texto, confianza, latencia = self.voice_service.escuchar()
        # Volvemos al hilo principal para procesar el resultado
        self.root.after(0, lambda: self._login_post_escucha(u_id, username, u_passphrase, u_fallos, texto, confianza, latencia))

    def _login_post_escucha(self, u_id, username, u_passphrase, u_fallos, texto, confianza, latencia):
        if not texto:
            self.dao.registrar_intento_login(u_id, "ERROR", {"motivo": "no_voice"})
            self.log_message("Error: No se detectó voz.")
            return

        if texto.lower() == u_passphrase.lower():
            extra = {"confianza": confianza, "latencia": f"{latencia}s"}
            self.dao.registrar_intento_login(u_id, "OK", extra)
            self.log_message(f"Login OK (Confianza: {confianza})")
            messagebox.showinfo("Bienvenido", f"¡Hola {username}!\nAcceso concedido.")
        else:
            intentos_restantes = 2 - u_fallos
            extra = {"status": "FAIL", "frase_intentada": texto, "intentos_restantes": intentos_restantes}
            self.dao.registrar_intento_login(u_id, "FAIL", extra)
            self.log_message(f"Login fallido. Frase: {texto}")
            messagebox.showwarning("Fallo", f"Frase incorrecta. Intentos restantes: {max(0, intentos_restantes)}")

    def mostrar_auditoria(self):
        logs = self.dao.obtener_auditoria_critica()
        self.txt_console.delete('1.0', tk.END)
        self.log_message("--- REGISTROS CRÍTICOS (FAIL o BAJA CONFIANZA) ---")
        for log in logs:
            user, fecha, status, data = log
            self.log_message(f"USUARIO: {user} | STATUS: {status} | DATA: {data}")

if __name__ == "__main__":
    root = tk.Tk()
    app = VoiceAuditApp(root)
    root.mainloop()
