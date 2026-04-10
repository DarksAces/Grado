# 🛡️ SecureCode: CWE-79 (XSS) Mitigation Lab
# 🛡️ SecureCode: Laboratorio de Mitigación CWE-79 (XSS)

## 📋 Description | Descripción

A specialized security laboratory focused on identifying and mitigating **Cross-Site Scripting (XSS)** vulnerabilities, specifically **CWE-79**. This project contrasts "Non-Compliant" code (vulnerable) with "Compliant" implementations that use proper output encoding and input sanitization to secure web applications.

Un laboratorio de seguridad especializado enfocado en identificar y mitigar vulnerabilidades de **Cross-Site Scripting (XSS)**, específicamente **CWE-79**. Este proyecto contrasta código "No Cumplidor" (vulnerable) con implementaciones "Cumplidoras" que usan codificación de salida y sanitización de entrada adecuadas.

---

## ✨ Key Features | Características Clave

- **Vulnerability Analysis:** Real-world demonstration of how unescaped user input can lead to malicious script execution in the browser. | *Análisis de Vulnerabilidades: Demostración real de cómo la entrada de usuario sin escapar puede llevar a la ejecución de scripts maliciosos.*
- **Compliance Patterns:** Implementation of industrial standards (Compliant) using specialized libraries to neutralize HTML injections. | *Patrones de Cumplimiento: Implementación de estándares industriales usando librerías especializadas para neutralizar inyecciones HTML.*
- **CWE Mapping:** Direct reference to the **Common Weakness Enumeration (CWE)** framework for professional security documentation. | *Mapeo CWE: Referencia directa al framework CWE para documentación de seguridad profesional.*
- **Sanitization Logic:** Focus on escaping special characters (`<`, `>`, `&`, `"`, `'`) before rendering them in dynamic templates. | *Lógica de Sanitización: Enfoque en el escapado de caracteres especiales antes de renderizarlos en plantillas dinámicas.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Python
- **Security Concept:** CWE-79 (Improper Neutralization of Input During Web Page Generation).
- **Techniques:** Output Encoding, Input Sanitization.

---

## 📂 Project Structure | Estructura del Proyecto

- **`noncompliant_cwe79.py`**: A vulnerable implementation allowing script injection. | *Implementación vulnerable que permite inyección de scripts.*
- **`compliant01.py`**: The secure solution utilizing proper sanitation filters. | *La solución segura utilizando filtros de sanitización adecuados.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Analyze the code in `noncompliant_cwe79.py` to identify the data reflection point. | *Analiza el código vulnerable para identificar el punto de reflexión.*
2.  Review `compliant01.py` to understand the protective logic applied. | *Revisa el código seguro para entender la lógica protectora.*
3.  Implement these patterns in any web application handling dynamic user data. | *Implementa estos patrones en cualquier aplicación web.*
