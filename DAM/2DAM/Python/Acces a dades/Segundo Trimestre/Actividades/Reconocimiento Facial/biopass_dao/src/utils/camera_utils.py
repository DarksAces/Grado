import cv2
import numpy as np

class CameraUtils:
    """
    Utilities for camera operations and image processing.
    """
    
    # Load the pre-trained Haar Cascade classifier for face detection
    # Using cv2.data.haarcascades ensures we get the correct path included with opencv-python
    FACE_CASCADE_PATH = cv2.data.haarcascades + 'haarcascade_frontalface_default.xml'
    face_cascade = cv2.CascadeClassifier(FACE_CASCADE_PATH)

    @staticmethod
    def detect_face(image):
        """
        Detects a face in the image.
        Returns the straight face image (ROI) if found, else None.
        Converts to grayscale for detection.
        """
        if image is None:
            return None
            
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Detect faces
        faces = CameraUtils.face_cascade.detectMultiScale(
            gray,
            scaleFactor=1.1,
            minNeighbors=5,
            minSize=(30, 30)
        )
        
        if len(faces) > 0:
            # Pick the largest face if multiple are found
            (x, y, w, h) = sorted(faces, key=lambda f: f[2] * f[3], reverse=True)[0]
            face_roi = gray[y:y+h, x:x+w]
            return face_roi
        
        return None

    @staticmethod
    def image_to_bytes(image, format='.jpg'):
        """
        Converts a CV2 image (numpy array) to bytes for storage in DB.
        """
        success, encoded_image = cv2.imencode(format, image)
        if success:
            return encoded_image.tobytes()
        return None

    @staticmethod
    def bytes_to_image(image_bytes):
        """
        Converts bytes from DB back to a CV2 image (numpy array).
        """
        if not image_bytes:
            return None
        nparr = np.frombuffer(image_bytes, np.uint8)
        return cv2.imdecode(nparr, cv2.IMREAD_GRAYSCALE)
