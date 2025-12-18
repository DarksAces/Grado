# Pruebas de Seguridad

Este repositorio contiene una colección de ejemplos y pruebas orientadas a demostrar vulnerabilidades y patrones seguros en código (principalmente Python), organizadas por CWE y casos prácticos para validación con herramientas como Postman.

**Propósito**: Proveer muestras didácticas para aprender, probar y validar controles de seguridad (ej.: inyecciones, control de acceso, gestión de credenciales, etc.).

**Estructura principal del repositorio**
- `Broken Object Level Authorization/` : ejemplo simple con un servidor inseguro (`server_insecure.py`) que demuestra autorización de objetos rota.
- `SecureCode/` : colección de carpetas por CWE (Common Weakness Enumeration) con ejemplos **compliant** (seguros) y **noncompliant** (inseguros). Cada subcarpeta contiene ejemplos en Python y archivos README cuando aplica.
- `ValidacionAutentificacionConPostman/` : ejemplos de autenticación para probar con Postman (API Keys, Basic Auth, Bearer Token, JWT).

**Cómo usar este repositorio**

- Clona o descarga el repositorio y sitúate en la carpeta raíz.
- Para ejecutar ejemplos Python (Windows PowerShell):

```powershell
# Ejecutar un script Python
python .\SecureCode\CWE-134\noncompliant01.py

# Ejecutar el servidor de ejemplo inseguro
python .\"Broken Object Level Authorization"\server_insecure.py
```

- Revisa los `README` dentro de las subcarpetas (por ejemplo `SecureCode/*/README*.md`) para detalles específicos de cada CWE.

**Notas por carpeta**

- `Broken Object Level Authorization/` : muestra cómo un servidor puede exponer objetos a usuarios no autorizados. Útil para practicar pruebas de control de acceso a nivel de objeto.

- `SecureCode/` : cada CWE suele incluir:
	- archivos `compliant*.py`: ejemplos que muestran prácticas seguras.
	- archivos `noncompliant*.py` o `example*.py`: ejemplos vulnerables.
	- `README*.md` en algunas carpetas con explicaciones adicionales.

- `ValidacionAutentificacionConPostman/` : scripts y ejemplos para usar en Postman y probar distintos esquemas de autenticación.

**Buenas prácticas**

- No ejecutes ejemplos inseguros en entornos de producción ni con datos reales.
- Usa entornos virtuales (`venv`) si vas a instalar dependencias.

Ejemplo rápido para crear un entorno virtual y ejecutar un script:

```powershell
python -m venv .venv; .\.venv\Scripts\Activate.ps1; python -m pip install --upgrade pip; python .\SecureCode\CWE-134\noncompliant01.py
```

**Contribuciones**

- Pull requests bienvenidos. Incluye descripción del problema o la mejora, y añade pruebas o instrucciones de ejecución cuando sea relevante.

**Contacto**

- Repositorio: `Codigos-pruebas-seguridad` (propietario: `DarksAces`).
- Para preguntas o issues, abre un ticket en el repositorio.

---

Si quieres, puedo:
- Ejecutar alguno de los ejemplos y mostrar la salida.
- Añadir instrucciones específicas para instalar dependencias en cada carpeta.
- Traducir o ampliar los README por CWE con explicaciones detalladas.
