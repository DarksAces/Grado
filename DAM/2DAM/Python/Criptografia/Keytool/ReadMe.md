# Keytool Manager

Herramienta de línea de comandos en Python para gestionar keystores y certificados Java de forma sencilla e interactiva.

## Descripción

Keytool Manager es una interfaz simplificada para trabajar con la herramienta `keytool` de Java, facilitando las operaciones más comunes de generación y exportación de certificados sin necesidad de recordar comandos complejos.

## Características

- **Generación de Keystores**: Crea keystores JKS con pares de claves RSA
- **Exportación de Certificados**: Exporta certificados desde keystores existentes
- **Interfaz Interactiva**: Menú guiado con validación de entradas
- **Validación de Contraseñas**: Comprueba requisitos mínimos de seguridad
- **Gestión de Errores**: Manejo robusto de errores y mensajes informativos

## Requisitos Previos

- Python 3.6 o superior
- Java JDK instalado (con `keytool` disponible en el PATH)

### Verificar instalación de Java

```bash
java -version
keytool
```

## Instalación

1. Clona este repositorio:
```bash
git clone https://github.com/tu-usuario/keytool-manager.git
cd keytool-manager
```

2. No se requieren dependencias adicionales (usa solo módulos estándar de Python)

## Uso

Ejecuta el script principal:

```bash
python keytool_manager.py
```

### Menú Principal

```
==================================================
HERRAMIENTA KEYTOOL - GESTION DE CERTIFICADOS
==================================================
1. Generar keystore (generar par de claves)
2. Exportar certificado
0. Salir
==================================================
```

### Opción 1: Generar Keystore

Genera un nuevo keystore con un par de claves RSA. Se te solicitará:

- **Alias**: Identificador único para el certificado
- **Nombre del fichero**: Nombre del archivo .jks (se añade automáticamente la extensión)
- **Contraseña**: Mínimo 6 caracteres, sin espacios
- **Tamaño de clave**: 2048

**Ejemplo:**
```
Escribe el alias: miAlias
Nombre del fichero .jks (ej: miClave.jks): miKeystore
Escribe la contraseña (mínimo 6 caracteres, sin espacios): miPassword123
Tamaño de clave (2048 o 4096) [default: 2048]: 2048
```

**Comando equivalente:**
```bash
keytool -genkeypair -keyalg RSA -alias miAlias -keystore miKeystore.jks \
  -storepass miPassword123 -keypass miPassword123 -keysize 2048 \
  -dname "CN=Ejemplo, OU=Org, O=Empresa, L=Ciudad, S=Provincia, C=ES"
```

### Opción 2: Exportar Certificado

Exporta un certificado desde un keystore existente. Se te solicitará:

- **Alias**: El alias del certificado a exportar
- **Nombre del keystore**: Archivo .jks existente
- **Nombre del certificado**: Nombre del archivo .crt de salida
- **Contraseña**: Contraseña del keystore

**Ejemplo:**
```
Escribe el alias: miAlias
Nombre del fichero .jks: miKeystore
Nombre del archivo certificado .crt (ej: miCertificado.crt): miCertificado
Contraseña del keystore: miPassword123
```

**Comando equivalente:**
```bash
keytool -export -alias miAlias -file miCertificado.crt \
  -keystore miKeystore.jks -storepass miPassword123
```


## Validaciones Implementadas

- **Alias**: No puede estar vacío
- **Nombre de fichero**: No puede estar vacío, se añade extensión automáticamente
- **Contraseña**: 
  - Mínimo 6 caracteres
  - No puede contener espacios
- **Archivo keystore**: Verifica existencia al exportar

## Manejo de Errores

El programa maneja los siguientes errores:

- Keytool no encontrado (Java no instalado)
- Errores de ejecución de comandos
- Archivos no existentes
- Entradas inválidas

## Notas Importantes

- Los datos del certificado (DN) están predefinidos como: `"CN=Ejemplo, OU=Org, O=Empresa, L=Ciudad, S=Provincia, C=ES"`
- Para uso en producción, considera modificar estos valores o hacerlos configurables
- Las extensiones .jks y .crt se añaden automáticamente si no se especifican

## Contribuir

Las contribuciones son bienvenidas. Por favor:

1. Haz fork del proyecto
2. Crea una rama para tu función (`git checkout -b feature/nueva-funcion`)
3. Commit tus cambios (`git commit -am 'Añade nueva función'`)
4. Push a la rama (`git push origin feature/nueva-funcion`)
5. Abre un Pull Request
