# 🛒 SupermarketGo: Native Android Inventory Management
# 🛒 SupermarketGo: Gestión de Inventario Nativa para Android

## 📋 Description | Descripción

A robust **Android Native** industrial application developed in **2DAM** that explores the integration of local persistence and dynamic UI orchestration. **SupermarketGo** leverages **SQLite** for offline data management, allowing users to browse a product catalog (Fruit focus), manage a virtual cart, and complete a formal checkout process. It serves as a comprehensive example of the **CRUD (Create, Read, Update, Delete)** lifecycle within a mobile environment.

Una aplicación industrial **Android Nativa** robusta desarrollada en **2DAM** que explora la integración de persistencia local y orquestación de UI dinámica. **SupermarketGo** utiliza **SQLite** para la gestión de datos offline, permitiendo a los usuarios navegar por un catálogo, gestionar un carrito y completar el proceso de pago.

---

## ✨ Key Features | Características Clave

- **SQLite Database Integration:** Specialized implementation of `AdmBaseDatosSQLite.java` to handle persistent storage of product inventories and user sessions. | *Integración SQLite: Implementación especializada para el manejo de almacenamiento persistente.*
- **Dynamic Catalog Rendering:** Usage of **Adapters** (`FrutaAdapter.java`) to transform raw database records into interactive visual cards. | *Renderizado Dinámico de Catálogo: Uso de Adapters para transformar registros.*
- **Complex UI Orchestration:** Seamless transition between the library browser (`MainActivity`) and the financial settlement layer (`CheckoutActivity`). | *Orquestación de UI Compleja: Transición fluida entre el navegador y el checkout.*
- **POO Domain Modeling:** Clean data representation through the `Fruta.java` domain entity. | *Modelado de Dominio POO: Representación limpia de datos.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 8+ / Kotlin support.
- **Database:** SQLite (Native Android implementation).
- **UI Components:** RecyclerView, CardView, Activity Bundles.
- **Patterns:** DAO/Adapter, Singleton (Database).

---

## 📂 Project Structure | Estructura del Proyecto

- **`MainActivity.java`**: The primary shopping dashboard and catalog engine. | *El dashboard de compras principal.*
- **`CheckoutActivity.java`**: Logic for transaction summary and payment finalization. | *Lógica para el resumen de transacciones.*
- **`AdmBaseDatosSQLite.java`**: The core database helper and schema manager. | *El ayudante de base de datos core y gestor de esquemas.*

---

## 🚀 How to Run | Cómo Ejecutar

1.  Open the project in **Android Studio**. | *Abre el proyecto en **Android Studio**.*
2.  Build the project to ensure the SQLite schema is initialized. | *Compila el proyecto para asegurar que el esquema SQLite se inicialice.*
3.  Run the application on an emulator or physical device (**Shift+F10**). | *Ejecuta la aplicación.*
4.  Add products to your cart and proceed to checkout to see the persistence logic in action. | *Añade productos y procede al checkout.*
