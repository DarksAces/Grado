# 🌡️ BioSense: Arduino Environmental & Touch Interaction Lab
# 🌡️ BioSense: Laboratorio de Interacción Táctil y Ambiental con Arduino

## 📋 Description | Descripción

A specialized **Arduino Sensorics** laboratory developed in **1SMX** focusing on the orchestration of environmental monitoring and human-machine interaction. **BioSense** implements a multi-module system that integrates **DHT (Temperature/Humidity)** sensors with specialized **Capacitive Touch** sensors. This project masters real-time sensor polling, threshold-based logic, and the activation of hardware feedback loops (LEDs/Buzzers) based on environmental and tactile triggers.

Un laboratorio especializado de **Sensórica con Arduino** desarrollado en **1SMX** centrado en la orquestación del monitoreo ambiental y la interacción hombre-máquina. **BioSense** implementa un sistema multi-módulo que integra sensores **DHT** con sensores **Táctiles Capacitivos**.

---

## ✨ Key Features | Características Clave

- **Dual-Sensor Orchestration:** Concurrent management of environmental (DHT) and tactile (Touch) input streams. | *Orquestación de Doble Sensor.*
- **Interactive Feedback Engine:** Logic-driven activation of physical pins (`DHT_boton_led`) to provide real-time status indication. | *Motor de Feedback Interactivo.*
- **High-Precision Polling:** Optimized usage of specialized libraries to ensure accurate data retrieval from digital sensors. | *Muestreo de Alta Precisión.*
- **Threshold-Driven Logic:** Robust implementation of conditional branching to trigger events based on temperature or touch intensity. | *Lógica Basada en Umbrales.*

---

## 🛠️ Tech Stack | Tecnologías

- **Platform:** Arduino.
- **Language:** C++.
- **Components:** DHT11/22 Temperature sensor, TTP223 Touch sensor, LEDs, Buttons.
- **Concepts:** Capacitive Sensing, Digital data protocols, I/O Interactivity.

---

## 📂 Project Structure | Estructura del Proyecto

- **`DHT_boton_led/`**: Integrated logic for environmental sensing with interactive LED feedback. | *Lógica integrada para sensórica ambiental.*
- **`DTH/`**: Module focused on precise temperature/humidity data extraction. | *Módulo enfocado en extracción de datos.*
- **`Final/`**: Culmination of the sensor track, merging all hardware features into a single orchestrated system. | *Culminación de la rama de sensores.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Connect the DHT and Touch sensors to your Arduino board. | *Conecta los sensores DHT y Táctil a tu placa.*
2.  Install the required **DHT Sensor Library** via the Arduino Manager. | *Instala la librería DHT.*
3.  Upload the `Final.ino` sketch to the board. | *Sube el sketch Final a la placa.*
4.  Observe the Serial Monitor and hardware responses when interacting with the touch sensor or changing environmental conditions. | *Observa las respuestas del hardware.*
