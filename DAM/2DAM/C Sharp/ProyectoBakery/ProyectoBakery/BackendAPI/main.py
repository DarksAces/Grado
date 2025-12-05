from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Optional
import json
import os
from datetime import date

app = FastAPI()

# --- MODELOS ---
class Producto(BaseModel):
    id: int
    nombre: str
    categoria: str
    precio: float
    stock: int

class VentaItem(BaseModel):
    producto_id: int
    cantidad: int

# --- AQUÍ ESTABA EL ERROR 422 ---
# Antes Python exigía recibir 'total' desde C#.
# Al poner '= 0' y '= ""', le decimos que si no llegan, no pasa nada.
class Venta(BaseModel):
    items: List[VentaItem]
    total: float = 0.0     # <--- Opcional (valor por defecto 0)
    fecha: str = ""        # <--- Opcional (valor por defecto vacío)

# --- BASE DE DATOS ---
DB_FILE = "datos.json"
DB_VENTAS = "ventas.json"

def leer_db(archivo):
    if not os.path.exists(archivo): return []
    with open(archivo, "r") as f: return json.load(f)

def guardar_db(archivo, datos):
    with open(archivo, "w") as f: json.dump(datos, f, indent=4)

# Inicializar datos si está vacío
if not os.path.exists(DB_FILE):
    inicial = [
        {"id": 1, "nombre": "Baguette", "categoria": "Pan", "precio": 1.20, "stock": 50},
        {"id": 2, "nombre": "Croissant", "categoria": "Bollería", "precio": 1.50, "stock": 20},
        {"id": 3, "nombre": "Tarta Manzana", "categoria": "Pasteles", "precio": 12.00, "stock": 5}
    ]
    guardar_db(DB_FILE, inicial)

# --- ENDPOINTS ---
@app.get("/productos", response_model=List[Producto])
def listar(): return leer_db(DB_FILE)

@app.post("/productos")
def crear(p: Producto):
    db = leer_db(DB_FILE)
    p.id = max([x["id"] for x in db], default=0) + 1
    db.append(p.dict())
    guardar_db(DB_FILE, db)
    return p

@app.put("/productos/{id}")
def actualizar(id: int, p: Producto):
    db = leer_db(DB_FILE)
    for i, item in enumerate(db):
        if item["id"] == id:
            p.id = id
            db[i] = p.dict()
            guardar_db(DB_FILE, db)
            return p
    raise HTTPException(404, "No encontrado")

@app.delete("/productos/{id}")
def borrar(id: int):
    db = leer_db(DB_FILE)
    db = [x for x in db if x["id"] != id]
    guardar_db(DB_FILE, db)
    return {"ok": True}

@app.post("/ventas")
def vender(v: Venta):
    prods = leer_db(DB_FILE)
    ventas = leer_db(DB_VENTAS)
    total_calculado = 0
    
    # Validar Stock y Calcular Total
    for item in v.items:
        # Buscamos el producto en la DB
        p_db = next((p for p in prods if p["id"] == item.producto_id), None)
        
        if not p_db:
             raise HTTPException(400, f"Producto ID {item.producto_id} no existe")
        
        if p_db["stock"] < item.cantidad:
            raise HTTPException(400, f"Stock insuficiente para {p_db['nombre']}")
            
        # Restar stock y sumar precio
        p_db["stock"] -= item.cantidad
        total_calculado += p_db["precio"] * item.cantidad
    
    # Completar datos de la venta
    v.total = total_calculado
    v.fecha = str(date.today())
    
    # Guardar cambios
    ventas.append(v.dict())
    guardar_db(DB_VENTAS, ventas) # Guardar venta
    guardar_db(DB_FILE, prods)    # Guardar stock actualizado
    
    return {"mensaje": "Venta OK", "total": total_calculado}

@app.get("/informes")
def informe():
    ventas = leer_db(DB_VENTAS)
    hoy = str(date.today())
    ventas_hoy = [v for v in ventas if v.get("fecha") == hoy]
    
    # Calculamos totales
    total_dia = sum(v["total"] for v in ventas_hoy)
    
    return {
        "total": total_dia,
        "cantidad": len(ventas_hoy)
    }