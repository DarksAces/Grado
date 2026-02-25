import json
from src.conexion_db import ConexionDB
from datetime import datetime, timedelta

class AuthDAO:
    def __init__(self):
        self.conexion_singleton = ConexionDB()

    def registrar_usuario(self, username, passphrase_text):
        """
        Registra un nuevo usuario y crea un log inicial en JSONB.
        """
        conn = self.conexion_singleton.get_conexion()
        cur = conn.cursor()
        try:
            # 1. Insertar usuario
            cur.execute(
                "INSERT INTO usuarios_voz (username, passphrase_text) VALUES (%s, %s) RETURNING id",
                (username, passphrase_text)
            )
            usuario_id = cur.fetchone()[0]

            # 2. Crear log inicial
            resultado_json = {
                "status": "OK",
                "tipo": "registro",
                "fecha": datetime.now().isoformat()
            }
            cur.execute(
                "INSERT INTO log_accesos_voz (usuario_id, resultado_json) VALUES (%s, %s)",
                (usuario_id, json.dumps(resultado_json))
            )

            conn.commit()
            return True
        except Exception as e:
            conn.rollback()
            print(f"Error al registrar usuario: {e}")
            return False
        finally:
            cur.close()

    def obtener_usuario(self, username):
        conn = self.conexion_singleton.get_conexion()
        cur = conn.cursor()
        try:
            cur.execute("SELECT id, username, passphrase_text, intentos_fallidos, bloqueado_hasta FROM usuarios_voz WHERE username = %s", (username,))
            usuario = cur.fetchone()
            return usuario
        except Exception as e:
            conn.rollback()
            print(f"Error al obtener usuario: {e}")
            return None
        finally:
            cur.close()

    def registrar_intento_login(self, usuario_id, status, extra_data=None):
        """
        Registra un intento de login en la tabla de logs (JSONB) y actualiza el estado del usuario.
        """
        conn = self.conexion_singleton.get_conexion()
        cur = conn.cursor()
        try:
            resultado_json = {"status": status, "fecha": datetime.now().isoformat()}
            if extra_data:
                resultado_json.update(extra_data)

            # Insertar log
            cur.execute(
                "INSERT INTO log_accesos_voz (usuario_id, resultado_json) VALUES (%s, %s)",
                (usuario_id, json.dumps(resultado_json))
            )

            # Actualizar intentos si falló
            if status == "FAIL":
                cur.execute(
                    "UPDATE usuarios_voz SET intentos_fallidos = intentos_fallidos + 1 WHERE id = %s RETURNING intentos_fallidos",
                    (usuario_id,)
                )
                intentos = cur.fetchone()[0]
                if intentos >= 3:
                    bloqueo = datetime.now() + timedelta(minutes=5)
                    cur.execute("UPDATE usuarios_voz SET bloqueado_hasta = %s WHERE id = %s", (bloqueo, usuario_id))
            elif status == "OK":
                cur.execute("UPDATE usuarios_voz SET intentos_fallidos = 0, bloqueado_hasta = NULL WHERE id = %s", (usuario_id,))

            conn.commit()
            return True
        except Exception as e:
            conn.rollback()
            print(f"Error al registrar intento: {e}")
            return False
        finally:
            cur.close()

    def obtener_auditoria_critica(self):
        """
        Consulta avanzada buceando en JSONB.
        """
        conn = self.conexion_singleton.get_conexion()
        cur = conn.cursor()
        try:
            query = """
            SELECT u.username, l.fecha_intento, l.resultado_json->>'status' as status, l.resultado_json
            FROM log_accesos_voz l
            JOIN usuarios_voz u ON l.usuario_id = u.id
            WHERE l.resultado_json->>'status' = 'FAIL' 
               OR (COALESCE(l.resultado_json->>'confianza', '0'))::float < 0.6
            ORDER BY l.fecha_intento DESC;
            """
            cur.execute(query)
            logs = cur.fetchall()
            return logs
        except Exception as e:
            conn.rollback()
            print(f"Error al obtener auditoría: {e}")
            return []
        finally:
            cur.close()
