import speech_recognition as sr
import time

class VoiceService:
    """
    Patrón Facade para la librería SpeechRecognition.
    Oculta la complejidad de la captura y procesamiento de audio.
    """
    def __init__(self):
        self.recognizer = sr.Recognizer()
        self.microphone = sr.Microphone()

    def escuchar(self):
        """
        Captura audio del micrófono y lo convierte a texto.
        Retorna (texto_reconocido, confianza, latencia) o (None, None, None) si falla.
        """
        start_time = time.time()
        try:
            with self.microphone as source:
                print("Ajustando ruido de fondo...")
                self.recognizer.adjust_for_ambient_noise(source, duration=1)
                print("Escuchando...")
                audio = self.recognizer.listen(source, timeout=5, phrase_time_limit=5)
            
            end_time = time.time()
            latencia = round(end_time - start_time, 2)
            
            # Usamos Google Web Speech API (requiere internet)
            # Para una app profesional se podría usar una API key o un motor local
            result = self.recognizer.recognize_google(audio, language="es-ES", show_all=True)
            
            if not result or len(result.get('alternative', [])) == 0:
                return None, 0, latencia

            best_match = result['alternative'][0]
            texto = best_match.get('transcript')
            confianza = best_match.get('confidence', 0.8) # Google no siempre devuelve confianza

            return texto, confianza, latencia

        except sr.UnknownValueError:
            print("No se pudo entender el audio")
            return None, 0, (time.time() - start_time)
        except sr.RequestError as e:
            print(f"Error en el servicio de reconocimiento: {e}")
            return "ERROR_SERVICE", 0, 0
        except Exception as e:
            print(f"Error inesperado en VoiceService: {e}")
            return None, 0, 0
