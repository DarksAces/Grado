import speech_recognition as sr
import time

class VoiceService:
    """
    Patrón Facade para la librería SpeechRecognition.
    Oculta la complejidad de la captura y procesamiento de audio.
    """
    def __init__(self, device_indices=None):
        self.recognizer = sr.Recognizer()
        # Si no se pasan índices, usamos el por defecto [None]
        self.device_indices = device_indices if device_indices else [None]

    def escuchar(self):
        """
        Intenta capturar audio rotando por los micrófonos configurados.
        """
        for idx in self.device_indices:
            start_time = time.time()
            try:
                mic = sr.Microphone(device_index=idx)
                with mic as source:
                    print(f"Iniciando Micrófono ID {idx if idx is not None else 'Default'}...")
                    
                    # SENSIBILIDAD EQUILIBRADA:
                    # Dejamos que el sistema se calibre solo pero con un punto de partida bajo
                    self.recognizer.energy_threshold = 100
                    self.recognizer.dynamic_energy_threshold = True
                    self.recognizer.pause_threshold = 0.8  # Esperar un poco antes de cortar
                    
                    print("Calibrando ruido ambiental (2 segundos)... Por favor, silencio.")
                    self.recognizer.adjust_for_ambient_noise(source, duration=2)
                    
                    print(f"Umbral calibrado: {self.recognizer.energy_threshold}")
                    print(f"¡ESCÚCHAME AHORA! Di tu frase...")
                    audio = self.recognizer.listen(source, timeout=10, phrase_time_limit=10)
                
                print(f"¡Audio capturado en Micrófono {idx}!")
                
                # Guardamos el audio para que el usuario pueda verificarlo
                with open("debug_audio.wav", "wb") as f:
                    f.write(audio.get_wav_data())
                print("DEBUG: Archivo 'debug_audio.wav' generado. Por favor, escúchalo.")
                
                end_time = time.time()
                latencia = round(end_time - start_time, 2)
                
                print("Enviando a Google para reconocimiento...")
                result = self.recognizer.recognize_google(audio, language="es-ES", show_all=True)
                
                if not result or len(result.get('alternative', [])) == 0:
                    print(f"Micrófono {idx}: Audio capturado pero Google no detectó palabras claras.")
                    # Como prueba de emergencia para ver si el flujo funciona:
                    # texto, confianza = "prueba_manual", 0.99
                    continue

                best_match = result['alternative'][0]
                texto = best_match.get('transcript')
                confianza = best_match.get('confidence', 0.8)
                print(f"¡Reconocido!: '{texto}' (Confianza: {confianza})")
                return texto, confianza, latencia

            except sr.WaitTimeoutError:
                print(f"Micrófono {idx}: Se agotó el tiempo esperando a que hables.")
                continue
            except sr.UnknownValueError:
                print(f"Micrófono {idx}: Google no pudo entender el audio (prueba a hablar más alto).")
                continue
            except Exception as e:
                print(f"Error técnico en Micrófono {idx}: {e}")
                continue
        
        print("Finalizado intento en todos los micrófonos.")
        return None, 0, 0
