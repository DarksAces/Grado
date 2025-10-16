<?php
// Configuración de la conexión a la base de datos
$host = "localhost";
$usuario = "root";
$contrasena = "";
$base_datos = "login_db";

// Crear conexión
function conectar() {
    global $host, $usuario, $contrasena, $base_datos;
    
    try {
        $conexion = new mysqli($host, $usuario, $contrasena, $base_datos);
        
        // Verificar conexión
        if ($conexion->connect_error) {
            error_log("Error de conexión a la base de datos: " . $conexion->connect_error);
            throw new Exception("Error de conexión a la base de datos");
        }
        
        // Configurar conjunto de caracteres a utf8mb4
        $conexion->set_charset("utf8mb4");
        
        return $conexion;
    } catch (Exception $e) {
        error_log("Error al conectar: " . $e->getMessage());
        die("Error de conexión a la base de datos. Consulte los logs para más detalles.");
    }
}

// Función para registrar actividad en logs
function registrarActividad($usuario_id, $accion, $descripcion = '', $ip = null) {
    $conexion = conectar();
    
    if ($ip === null) {
        $ip = $_SERVER['REMOTE_ADDR'] ?? '0.0.0.0';
    }
    
    $consulta = $conexion->prepare("INSERT INTO logs_actividad (usuario_id, accion, descripcion, ip_address) VALUES (?, ?, ?, ?)");
    $consulta->bind_param("isss", $usuario_id, $accion, $descripcion, $ip);
    $consulta->execute();
    $consulta->close();
    $conexion->close();
}

// Función para verificar si un usuario está bloqueado
function verificarBloqueo($email) {
    $conexion = conectar();
    
    $consulta = $conexion->prepare("SELECT bloqueado_hasta, intentos_fallidos FROM usuarios WHERE email = ?");
    $consulta->bind_param("s", $email);
    $consulta->execute();
    $resultado = $consulta->get_result();
    
    if ($resultado->num_rows > 0) {
        $usuario = $resultado->fetch_assoc();
        
        // Si tiene fecha de bloqueo y aún no ha pasado
        if ($usuario['bloqueado_hasta'] && strtotime($usuario['bloqueado_hasta']) > time()) {
            $consulta->close();
            $conexion->close();
            return ['bloqueado' => true, 'hasta' => $usuario['bloqueado_hasta']];
        }
        
        // Si el bloqueo ya expiró, resetear campos
        if ($usuario['bloqueado_hasta'] && strtotime($usuario['bloqueado_hasta']) <= time()) {
            $resetear = $conexion->prepare("UPDATE usuarios SET intentos_fallidos = 0, bloqueado_hasta = NULL WHERE email = ?");
            $resetear->bind_param("s", $email);
            $resetear->execute();
            $resetear->close();
        }
    }
    
    $consulta->close();
    $conexion->close();
    return ['bloqueado' => false];
}

// Función para incrementar intentos fallidos
function incrementarIntentosFallidos($email) {
    $conexion = conectar();
    
    // Incrementar intentos
    $consulta = $conexion->prepare("UPDATE usuarios SET intentos_fallidos = intentos_fallidos + 1 WHERE email = ?");
    $consulta->bind_param("s", $email);
    $consulta->execute();
    
    // Verificar si debe bloquearse (después de 5 intentos)
    $verificar = $conexion->prepare("SELECT intentos_fallidos FROM usuarios WHERE email = ?");
    $verificar->bind_param("s", $email);
    $verificar->execute();
    $resultado = $verificar->get_result();
    
    if ($resultado->num_rows > 0) {
        $usuario = $resultado->fetch_assoc();
        
        if ($usuario['intentos_fallidos'] >= 5) {
            // Bloquear por 15 minutos
            $tiempo_bloqueo = date('Y-m-d H:i:s', time() + 900);
            $bloquear = $conexion->prepare("UPDATE usuarios SET bloqueado_hasta = ? WHERE email = ?");
            $bloquear->bind_param("ss", $tiempo_bloqueo, $email);
            $bloquear->execute();
            $bloquear->close();
        }
    }
    
    $consulta->close();
    $verificar->close();
    $conexion->close();
}

// Función para resetear intentos fallidos (login exitoso)
function resetearIntentosFallidos($email) {
    $conexion = conectar();
    
    $consulta = $conexion->prepare("UPDATE usuarios SET intentos_fallidos = 0, bloqueado_hasta = NULL, ultima_conexion = CURRENT_TIMESTAMP WHERE email = ?");
    $consulta->bind_param("s", $email);
    $consulta->execute();
    $consulta->close();
    $conexion->close();
}

// Función para obtener información de usuario
function obtenerUsuario($email) {
    $conexion = conectar();
    
    $consulta = $conexion->prepare("SELECT id, email, password, nombre, apellido, activo FROM usuarios WHERE email = ? AND activo = 1");
    $consulta->bind_param("s", $email);
    $consulta->execute();
    $resultado = $consulta->get_result();
    
    $usuario = null;
    if ($resultado->num_rows > 0) {
        $usuario = $resultado->fetch_assoc();
    }
    
    $consulta->close();
    $conexion->close();
    
    return $usuario;
}

// Función para obtener todos los usuarios (para dashboard)
function obtenerTodosUsuarios() {
    $conexion = conectar();
    
    $consulta = "SELECT id, email, nombre, apellido, activo, fecha_registro, ultima_conexion, intentos_fallidos, bloqueado_hasta FROM usuarios ORDER BY fecha_registro DESC";
    $resultado = $conexion->query($consulta);
    
    $usuarios = [];
    if ($resultado->num_rows > 0) {
        while ($fila = $resultado->fetch_assoc()) {
            $usuarios[] = $fila;
        }
    }
    
    $conexion->close();
    return $usuarios;
}

// Función para cambiar estado de usuario (activar/desactivar)
function cambiarEstadoUsuario($id, $activo) {
    $conexion = conectar();
    
    $consulta = $conexion->prepare("UPDATE usuarios SET activo = ? WHERE id = ?");
    $consulta->bind_param("ii", $activo, $id);
    $resultado = $consulta->execute();
    
    $consulta->close();
    $conexion->close();
    
    return $resultado;
}

// Función para eliminar usuario
function eliminarUsuario($id) {
    $conexion = conectar();
    
    // Primero eliminar logs relacionados
    $eliminarLogs = $conexion->prepare("DELETE FROM logs_actividad WHERE usuario_id = ?");
    $eliminarLogs->bind_param("i", $id);
    $eliminarLogs->execute();
    $eliminarLogs->close();
    
    // Luego eliminar usuario
    $consulta = $conexion->prepare("DELETE FROM usuarios WHERE id = ?");
    $consulta->bind_param("i", $id);
    $resultado = $consulta->execute();
    
    $consulta->close();
    $conexion->close();
    
    return $resultado;
}
?>