# 🥖 Proyecto Bakery POS

Este documento explica el funcionamiento, arquitectura, el CÓDIGO y estructura del sistema de Punto de Venta (POS) para panadería.

## 📋 Resumen

El proyecto es un sistema completo de gestión de ventas dividido en dos aplicaciones que se comunican entre sí:
1.  **Backend (Servidor):** Gestiona los datos, el stock y la lógica de negocio. (Python)
2.  **Frontend (Cliente):** La interfaz visual que utilizan los empleados para realizar ventas. (C# .NET)

---

## 🏗️ Arquitectura del Sistema

### 1. El Backend (API REST)
Ubicación: `/ProyectoBakery/BackendAPI`

Es el "cerebro" del sistema. Está construido con **Python** y **FastAPI**.
*   **Función Principal:** Recibe peticiones del cliente (App de escritorio) y responde con datos.
*   **Base de Datos:** No utiliza SQL. Almacena toda la información en archivos de texto planos en formato JSON:
    *   `datos.json`: Contiene el inventario de productos (ID, Nombre, Precio, Stock).
    *   `ventas.json`: Registro histórico de todas las ventas realizadas.
*   **Lógica de Seguridad:** Se encarga de validar que haya suficiente stock antes de autorizar una venta.

### 2. El Cliente (App de Escritorio)
Ubicación: `/ProyectoBakery/ClienteApp/PanaderiaPOS`

Es la "cara" del sistema. Está construido con **C#** y **Windows Forms**.
*   **Función Principal:** Proporcionar una interfaz amigable para que el panadero cobre los productos.
*   **Comunicación:** Utiliza `HttpClient` para enviar y recibir datos del Backend (por defecto en `http://127.0.0.1:8000`).
*   **Módulos:**
    *   **Ventas:** Pantalla principal para agregar productos al carrito y cobrar.
    *   **Catálogo:** Permite ver, añadir o editar productos.
    *   **Informes:** Muestra el total de ventas del día.

---

## � Explicación del Código (Lo Importante)

A continuación se detallan las partes críticas del código que hacen que todo funcione.

### 🐍 1. Backend: `main.py` (Python)

Este archivo es el servidor. Aquí están las reglas del juego.

#### Modelos de Datos (Pydantic)
Define qué información esperamos recibir.
```python
class VentaItem(BaseModel):
    producto_id: int
    cantidad: int

class Venta(BaseModel):
    items: List[VentaItem]  # Lista de productos vendidos
    total: float = 0.0      # Opcional, el servidor lo calcula
```
*   **Importante:** Usamos `BaseModel` para validar los datos automáticamente. Si la App envía algo mal, Python rechaza la petición.

#### Persistencia (Guardar en Archivos)
Funciones simples para leer y escribir en los archivos `.json`. De esta forma no necesitamos instalar MySQL ni nada complejo.
```python
def guardar_db(archivo, datos):
    with open(archivo, "w") as f: json.dump(datos, f, indent=4)
```

#### El Endpoint Crítico: `/ventas` (POST)
Aquí ocurre la magia de la validación de stock.
```python
@app.post("/ventas")
def vender(v: Venta):
    # 1. Recorremos los items que se quieren vender
    for item in v.items:
        # 2. Buscamos el producto en la base de datos
        p_db = next((p for p in prods if p["id"] == item.producto_id), None)
        
        # 3. VERIFICACIÓN DE STOCK (CRÍTICO)
        if p_db["stock"] < item.cantidad:
            raise HTTPException(400, f"Stock insuficiente para {p_db['nombre']}")
            
        # 4. Si hay stock, lo descontamos temporalmente en memoria
        p_db["stock"] -= item.cantidad
```
*   **Por qué es importante:** Esta lógica previene que vendas algo que no tienes. Si la validación falla, se lanza un error `400` y **no se guarda nada**.

---

### #️⃣ 2. Frontend: `Ayudantes.cs` (C#)

Este archivo actúa como un "puente" o traductor entre la ventana de Windows y el servidor de Python.

#### Clase `ApiClient`
Encapsula todas las llamadas a internet (`HttpClient`).
```csharp
public class ApiClient
{
    HttpClient client = new HttpClient();
    string url = "http://127.0.0.1:8000"; // Dirección del servidor local

    // Función genérica para ENVIAR datos (POST o PUT)
    private async Task<bool> Enviar(object data, string ruta, string metodo)
    {
        try
        {
            // Convierte el objeto C# a Texto JSON
            var json = new StringContent(JsonConvert.SerializeObject(data), Encoding.UTF8, "application/json");
            
            // Envía la petición y espera respuesta
            var res = (metodo == "POST") ? await client.PostAsync(url + ruta, json) : ...;
            
            // Devuelve TRUE solo si el servidor (Python) respondió con éxito (200 OK)
            return res.IsSuccessStatusCode;
        }
        catch { return false; }
    }
}
```
*   **Importante:** El uso de `async` y `Task` es vital para que la ventana **no se congele** mientras espera la respuesta del servidor.

---

### 🖥️ 3. Frontend: `FormVentas.cs` (C#)

Es el código detrás de la ventana de ventas.

#### Cargar Productos
```csharp
private async Task CargarCatalogo()
{
    // Pide la lista al servidor y rellena la tabla visual
    gridProductos.DataSource = await api.GetProductos();
}
```

#### Botón Pagar (Confirmar Venta)
```csharp
private async void BotonPagar_Click(object sender, EventArgs e)
{
    // 1. Crea el objeto Venta con lo que hay en el carrito
    Venta v = new Venta { Items = carrito };

    // 2. Envía la venta al servidor y espera respuesta
    if (await api.PostVenta(v))
    {
        // SI VENTA OK: Limpia todo y recarga el inventario actualizado
        MessageBox.Show("¡Venta realizada! 🥖");
        carrito.Clear();
        await CargarCatalogo(); 
    }
    else 
    {
        // SI VENTA ERROR: Muestra aviso (Ej: Stock insuficiente)
        MessageBox.Show("Error: Stock insuficiente.");
    }
}
```
*   **Flujo:** Nótese que el stock **no se descuenta aquí en C#**. C# solo envía la orden "Véndeme esto". Si el servidor dice que no se puede, C# muestra el error. La verdad absoluta siempre la tiene el servidor.

---

## 📂 Estructura de Carpetas

```text
ProyectoBakery/
├── BackendAPI/
│   ├── main.py          <-- El código del servidor (ARRANCAR ESTO PRIMERO)
│   ├── datos.json       <-- Inventario (se crea automático si no existe)
│   └── ventas.json      <-- Historial de ventas
│
└── ClienteApp/
    └── PanaderiaPOS/    <-- Código fuente de la App de Windows
        ├── FormVentas.cs   <-- Pantalla principal de ventas
        ├── Ayudantes.cs    <-- Lógica de conexión con el servidor
        └── ...
```

## ⚠️ Nota Importante para el Uso

Para que el sistema funcione, **SIEMPRE** se debe seguir este orden:

1.  Ejecutar `main.py` (Se abrirá una consola negra. **NO CERRARLA**).
2.  Ejecutar la aplicación de Windows (`PanaderiaPOS`).

Si cierras la consola de Python, la aplicación de Windows perderá la conexión y dará errores.
