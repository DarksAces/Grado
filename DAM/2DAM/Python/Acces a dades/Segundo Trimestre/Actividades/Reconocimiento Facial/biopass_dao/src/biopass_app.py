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

        # Camera setup
        self.cap = cv2.VideoCapture(0)
        
        # UI Elements
        self.setup_ui()
        
        # Start camera loop
        self.update_camera()

    def setup_ui(self):
        # Video Frame
        self.video_label = tk.Label(self.root)
        self.video_label.pack(pady=20)

        # Controls Frame
        controls_frame = tk.Frame(self.root)
        controls_frame.pack(pady=10)

        # Input for Name
        tk.Label(controls_frame, text="Nombre:").grid(row=0, column=0, padx=5)
        self.name_entry = tk.Entry(controls_frame)
        self.name_entry.grid(row=0, column=1, padx=5)

        # Register Button
        self.btn_register = tk.Button(controls_frame, text="Registrar Usuario (DAO)", command=self.register_user, bg="#4CAF50", fg="white")
        self.btn_register.grid(row=0, column=2, padx=10)

        # Login Button
        self.btn_login = tk.Button(controls_frame, text="Entrar / Login (Singleton)", command=self.login_user, bg="#2196F3", fg="white")
        self.btn_login.grid(row=0, column=3, padx=10)
        
        # Status Label
        self.status_label = tk.Label(self.root, text="Listo", font=("Arial", 12))
        self.status_label.pack(pady=10)

    def update_camera(self):
        ret, frame = self.cap.read()
        if ret:
            # Flip frame horizontally (mirror effect)
            frame = cv2.flip(frame, 1)
            self.current_frame = frame
            # Convert to RGB for Tkinter
            rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            
            # Draw rectangle around face if detected (visual feedback only)
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

        # 1. Detect Face
        print("Detecting face for registration...")
        face_roi = CameraUtils.detect_face(self.current_frame)
        
        if face_roi is None:
            messagebox.showerror("Error", "No se detectó ningún rostro. Intente de nuevo.")
            return

        # 2. Convert to Bytes
        print("Converting image to bytes...")
        image_bytes = CameraUtils.image_to_bytes(face_roi)
        
        if image_bytes:
            # 3. Save via DAO
            print("Saving to database via DAO...")
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

        # 1. Detect Face
        print("Detecting face for login...")
        face_roi = CameraUtils.detect_face(self.current_frame)
        if face_roi is None:
            self.status_label.config(text="No se detecta rostro", fg="red")
            return

        # 2. Get Data from DAO and Train
        print("Fetching users from DAO...")
        users = UsuarioDAO.obtener_todos()
        if not users:
            messagebox.showwarning("Aviso", "No hay usuarios registrados en la base de datos.")
            return

        print(f"Training with {len(users)} users...")
        faces = []
        labels = []
        names = {} # Map ID to Name

        for user in users:
            # user: (id, nombre, imagen_facial)
            user_id = user[0]
            name = user[1]
            img_bytes = user[2]
            
            # Convert bytes back to image
            face_img = CameraUtils.bytes_to_image(img_bytes)
            if face_img is not None:
                faces.append(face_img)
                labels.append(user_id)
                names[user_id] = name
        
        if not faces:
             messagebox.showerror("Error", "Error al procesar las imágenes de la BD.")
             return

        # Train LBPH Recognizer
        recognizer = cv2.face.LBPHFaceRecognizer_create()
        recognizer.train(faces, np.array(labels))
        
        # 3. Predict
        print("Predicting...")
        label_id, confidence = recognizer.predict(face_roi)
        
        print(f"Prediction: ID={label_id}, Confidence={confidence}")
        
        # Confidence logic (lower is better for LBPH, usually < 50 is a good match, < 100 is okish)
        if confidence < 70: # Tuning threshold
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
