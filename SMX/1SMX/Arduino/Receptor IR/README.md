# 📡 IRConnect: Arduino Remote Control & Infrared Detection
# 📡 IRConnect: Control Remoto y Detección Infrarroja con Arduino

## 📋 Description | Descripción

A specialized **Arduino communication** laboratory developed in **1SMX** focusing on the orchestration of **Infrared (IR)** wireless signals. **IRConnect** implements a decoder system using an IR receiver module to capture and translate remote control signals (HEX codes) into digital commands. This project masters the integration of the `IRremote` library, signal decoding patterns, and the creation of wireless-driven hardware interfaces.

Un laboratorio especializado de **comunicación con Arduino** desarrollado en **1SMX** centrado en la orquestación de señales inalámbricas **Infrarrojas (IR)**. **IRConnect** implementa un sistema decodificador usando un módulo receptor IR.

---

## ✨ Key Features | Características Clave

- **Wireless Signal Orchestration:** High-precision capturing of diverse IR protocols using industrial-standard sensors. | *Orquestación de Señales Inalámbricas: Captura de diversos protocolos IR.*
- **HEX Protocol Decoding:** Mastery of translating raw infrared waves into unique hexadecimal identifiers for command mapping. | *Decodificación de Protocolo HEX.*
- **Remote Execution Hub:** Implementation of logic paths driven by external wireless inputs (TV remotes, IR keypads). | *Hub de Ejecución Remota.*
- **Library Integration Lab:** Performance-oriented usage of the `IRremote.h` ecosystem. | *Laboratorio de Integración de Librerías.*

---

## 🛠️ Tech Stack | Tecnologías

- **Platform:** Arduino.
- **Language:** C++.
- **Library:** IRremote.
- **Components:** HX1838 IR Receiver, IR Remote control.
- **Concepts:** Wireless Communication, HEX Decoding, Interrupt-driven input.

---

## 📂 Project Structure | Estructura del Proyecto

- **`IR02/IR02.ino`**: The core logical source for signal decoding and command orchestration. | *La fuente lógica core para decodificación y orquestación.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Connect the IR receiver to the designated digital pins on your Arduino. | *Conecta el receptor IR a los pines digitales.*
2.  Install the **IRremote** library in your Arduino IDE. | *Instala la librería IRremote.*
3.  Upload the sketch and open the **Serial Monitor**. | *Sube el sketch y abre el Serial Monitor.*
4.  Press buttons on your remote and observe the HEX codes being decoded in real-time. | *Presiona botones en tu control remoto.*
