# Calculadora Científica en C# con Herencia

## Descripción
Este proyecto es una **calculadora científica de consola** desarrollada en **C#**, diseñada para practicar **programación orientada a objetos y herencia**.  
Cada operación (suma, resta, multiplicación, división, potencia y raíz cuadrada) se implementa como **clase independiente** que hereda de una clase base abstracta `Operacion`. Esto permite el **polimorfismo** y facilita la ampliación del proyecto.

---

## Funcionalidades

- Suma, resta, multiplicación y división.  
- Potencia y raíz cuadrada.  
- Validación de entradas:
  - No se permite división entre cero.  
  - La raíz cuadrada solo para números no negativos.  
  - Entrada de números segura usando `TryParse`.  
- Menú interactivo que se repite hasta que el usuario elija salir.  
- Estructura orientada a objetos con **clases separadas y herencia**.

---

## Ejemplo de uso

```csharp
Operacion op = new Suma(5, 3);
Console.WriteLine(op.Calcular()); // Imprime 8

op = new Potencia(2, 3);
Console.WriteLine(op.Calcular()); // Imprime 8

