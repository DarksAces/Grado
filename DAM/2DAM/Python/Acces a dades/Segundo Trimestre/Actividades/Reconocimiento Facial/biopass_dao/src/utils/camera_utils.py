import cv2
import numpy as np

class CameraUtils:
    """
    Utilidades para operaciones de cámara y procesamiento de imágenes.
    """
    
    # Cargar el clasificador Haar Cascade pre-entrenado para la detección de rostros
    # Usar cv2.data.haarcascades asegura que obtengamos la ruta correcta incluida con opencv-python
    FACE_CASCADE_PATH = cv2.data.haarcascades + 'haarcascade_frontalface_default.xml'
    face_cascade = cv2.CascadeClassifier(FACE_CASCADE_PATH)

    @staticmethod
    def detect_face(image):
        """
        Detecta un rostro en la imagen.
        Devuelve la imagen del rostro (ROI) si se encuentra, de lo contrario None.
        Convierte a escala de grises para la detección.
        """
        if image is None:
            return None
            
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Detectar rostros
        faces = CameraUtils.face_cascade.detectMultiScale(
            gray,
            scaleFactor=1.1,
            minNeighbors=5,
            minSize=(30, 30)
        )
        
        if len(faces) > 0:
            # Elegir el rostro más grande si se encuentran múltiples
            (x, y, w, h) = sorted(faces, key=lambda f: f[2] * f[3], reverse=True)[0]
            face_roi = gray[y:y+h, x:x+w]
            return face_roi
        
        return None

    @staticmethod
    def image_to_bytes(image, format='.jpg'):
        """
        Convierte una imagen CV2 (array numpy) a bytes para almacenamiento en la BD.
        """
        success, encoded_image = cv2.imencode(format, image)
        if success:
            return encoded_image.tobytes()
        return None

    @staticmethod
    def bytes_to_image(image_bytes):
        """
        Convierte bytes de la BD de vuelta a una imagen CV2 (array numpy).
        """
        if not image_bytes:
            return None
        nparr = np.frombuffer(image_bytes, np.uint8)
        return cv2.imdecode(nparr, cv2.IMREAD_GRAYSCALE)
