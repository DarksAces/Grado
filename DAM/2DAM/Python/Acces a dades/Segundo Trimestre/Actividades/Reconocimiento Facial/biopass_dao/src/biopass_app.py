import tkinter as tk
from tkinter import messagebox
import cv2
from PIL import Image, ImageTk
from src.usuario_dao import UsuarioDAO
from src.utils.camera_utils import CameraUtils
import numpy as np

class BioPassApp:
    def __init__(self, root):
        self.root = root
        self.root.title("BioPass DAO - Sistema de Control de Acceso")
        self.root.geometry("800x600")

        # Configuración de la cámara
        self.cap = cv2.VideoCapture(0)
        
        # Elementos de la IU
        self.setup_ui()
        
        # Iniciar bucle de cámara
        self.update_camera()

    def setup_ui(self):
        # Marco de Video
        self.video_label = tk.Label(self.root)
        self.video_label.pack(pady=20)

        # Marco de Controles
        controls_frame = tk.Frame(self.root)
        controls_frame.pack(pady=10)

        # Entrada para Nombre
        tk.Label(controls_frame, text="Nombre:").grid(row=0, column=0, padx=5)
        self.name_entry = tk.Entry(controls_frame)
        self.name_entry.grid(row=0, column=1, padx=5)

        # Botón de Registro
        self.btn_register = tk.Button(controls_frame, text="Registrar Usuario (DAO)", command=self.register_user, bg="#4CAF50", fg="white")
        self.btn_register.grid(row=0, column=2, padx=10)

        # Botón de Inicio de Sesión
        self.btn_login = tk.Button(controls_frame, text="Entrar / Login (Singleton)", command=self.login_user, bg="#2196F3", fg="white")
        self.btn_login.grid(row=0, column=3, padx=10)
        
        # Etiqueta de Estado
        self.status_label = tk.Label(self.root, text="Listo", font=("Arial", 12))
        self.status_label.pack(pady=10)

    def update_camera(self):
        ret, frame = self.cap.read()
        if ret:
            # Voltear el marco horizontalmente (efecto espejo)
            frame = cv2.flip(frame, 1)
            self.current_frame = frame
            # Convertir a RGB para Tkinter
            rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            
            # Dibujar rectángulo alrededor del rostro si se detecta (solo retroalimentación visual)
            faces = CameraUtils.face_cascade.detectMultiScale(
                cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY), 1.1, 5
            )
            for (x, y, w, h) in faces:
                cv2.rectangle(rgb_frame, (x, y), (x+w, y+h), (0, 255, 0), 2)
            
            img = Image.fromarray(rgb_frame)
            imgtk = ImageTk.PhotoImage(image=img)
            self.video_label.imgtk = imgtk
            self.video_label.configure(image=imgtk)
        
        self.root.after(10, self.update_camera)

    def register_user(self):
        name = self.name_entry.get()
        if not name:
            messagebox.showwarning("Advertencia", "Por favor ingrese un nombre.")
            return

        if not hasattr(self, 'current_frame'):
            return

        # 1. Detectar Rostro
        print("Detectando rostro para registro...")
        face_roi = CameraUtils.detect_face(self.current_frame)
        
        if face_roi is None:
            messagebox.showerror("Error", "No se detectó ningún rostro. Intente de nuevo.")
            return

        # 2. Convertir a Bytes
        print("Convirtiendo imagen a bytes...")
        image_bytes = CameraUtils.image_to_bytes(face_roi)
        
        if image_bytes:
            # 3. Guardar vía DAO
            print("Guardando en base de datos vía DAO...")
            if UsuarioDAO.registrar_usuario(name, image_bytes):
                messagebox.showinfo("Éxito", f"Usuario {name} registrado correctamente en la BD.")
                self.name_entry.delete(0, tk.END)
            else:
                messagebox.showerror("Error", "Fallo al guardar en la base de datos.")
        else:
            messagebox.showerror("Error", "Fallo al procesar la imagen.")

    def login_user(self):
        if not hasattr(self, 'current_frame'):
            return

        # 1. Detectar Rostro
        print("Detectando rostro para inicio de sesión...")
        face_roi = CameraUtils.detect_face(self.current_frame)
        if face_roi is None:
            self.status_label.config(text="No se detecta rostro", fg="red")
            return

        # 2. Obtener Datos del DAO y Entrenar
        print("Obteniendo usuarios del DAO...")
        users = UsuarioDAO.obtener_todos()
        if not users:
            messagebox.showwarning("Aviso", "No hay usuarios registrados en la base de datos.")
            return

        print(f"Training with {len(users)} users...")
        faces = []
        labels = []
        names = {} # Mapear ID a Nombre

        for user in users:
            # usuario: (id, nombre, imagen_facial)
            user_id = user[0]
            name = user[1]
            img_bytes = user[2]
            
            # Convertir bytes de vuelta a imagen
            face_img = CameraUtils.bytes_to_image(img_bytes)
            if face_img is not None:
                faces.append(face_img)
                labels.append(user_id)
                names[user_id] = name
        
        if not faces:
             messagebox.showerror("Error", "Error al procesar las imágenes de la BD.")
             return

        # Entrenar Reconocedor LBPH
        recognizer = cv2.face.LBPHFaceRecognizer_create()
        recognizer.train(faces, np.array(labels))
        
        # 3. Predecir
        print("Prediciendo...")
        label_id, confidence = recognizer.predict(face_roi)
        
        print(f"Prediction: ID={label_id}, Confidence={confidence}")
        
        # Lógica de confianza (menor es mejor para LBPH, usualmente < 50 es buen match, < 100 es ok)
        if confidence < 70: # Umbral de ajuste
            detected_name = names.get(label_id, "Desconocido")
            self.status_label.config(text=f"Bienvenido/a {detected_name} (Conf: {round(confidence)})", fg="green")
            messagebox.showinfo("Acceso Permitido", f"Bienvenido, {detected_name}!")
        else:
            self.status_label.config(text=f"Rostro No Reconocido (Conf: {round(confidence)})", fg="red")

    def on_closing(self):
        if self.cap.isOpened():
            self.cap.release()
        self.root.destroy()

if __name__ == "__main__":
    try:
        root = tk.Tk()
        app = BioPassApp(root)
        root.protocol("WM_DELETE_WINDOW", app.on_closing)
        root.mainloop()

    except Exception as e:
        print(f"Critical Error: {e}")
        input("Press Enter to exit...")
