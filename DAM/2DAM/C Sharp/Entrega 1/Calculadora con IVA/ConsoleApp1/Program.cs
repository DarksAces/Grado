using System;

class PrecioFinal
{
    static void Main()
    {
        Console.WriteLine("=== Calculadora de Precio Final (IVA + Descuento) ===");

        // 1. Pedir Precio
        double precioBase = PedirDouble("Ingrese el precio base del producto: ");

        // 2. Preguntar Porcentaje de IVA
        double porcentajeIVA = PedirDouble("Ingrese el porcentaje de IVA a aplicar (ej. 21): ");

        // 3. Preguntar si es socio
        Console.Write("¿El cliente es socio? (S/N): ");
        string esSocioInput = Console.ReadLine().Trim().ToUpper();

        // Declarábamos 'esSocio' aquí para que sea visible en todo Main()
        bool esSocio = false;

        // === Bloque de Validación y Ejecución ===

        // La condición revisa si el input es CUALQUIERA de las opciones válidas:
        bool inputValido = esSocioInput == "S" || esSocioInput == "N" || esSocioInput == "SI" || esSocioInput == "NO" || esSocioInput == "YES";

        if (!inputValido) // Si NO es una opción válida, mostramos error.
        {
            Console.WriteLine($"\n❌ ¡Error de Entrada! '{esSocioInput}' no es una opción válida.");
            Console.WriteLine("El programa terminará. Por favor, reinicie e ingrese 'S', 'N', 'SI', 'NO' o 'YES'.");
        }
        else // Si es una opción válida, ejecutamos la lógica de cálculo.
        {
            // Asignamos el valor de esSocio basado en las respuestas afirmativas
            if (esSocioInput == "S" || esSocioInput == "SI" || esSocioInput == "YES")
            {
                esSocio = true;
            }

            // 4. Lógica de Cálculo y Descuento Condicional

            // Convertimos el porcentaje a factor (ej. 21 -> 0.21)
            double factorIVA = porcentajeIVA / 100.0;

            // Primer cálculo: Aplicar IVA
            double precioConIVA = precioBase * (1 + factorIVA);

            double precioFinal;
            double descuentoAplicado = 0.0;
            const double PORCENTAJE_DESCUENTO_SOCIO = 0.05; // Descuento fijo del 5%

            // Aplicamos la estructura condicional (if/else)
            if (esSocio)
            {
                // Si es socio, calculamos el 5% sobre el precio ya con IVA
                descuentoAplicado = precioConIVA * PORCENTAJE_DESCUENTO_SOCIO;
                precioFinal = precioConIVA - descuentoAplicado;
            }
            else
            {
                // Si no es socio, no hay descuento
                precioFinal = precioConIVA;
            }

            // 5. Mostrar Resultado Final
            Console.WriteLine("\n===============================================");
            Console.WriteLine($"Precio Base:           {precioBase:F2} euros");
            Console.WriteLine($"IVA ({porcentajeIVA}%) Aplicado:   {(precioConIVA - precioBase):F2} euros");

            // Mostrar el descuento solo si se aplicó
            if (esSocio)
            {
                Console.WriteLine($"Descuento Socio (5%):  {-descuentoAplicado:F2} euros");
                Console.WriteLine("-----------------------------------------------");
            }
            else
            {
                Console.WriteLine($"Descuento Socio:       0.00 euros");
                Console.WriteLine("-----------------------------------------------");
            }

            Console.WriteLine($"**PRECIO FINAL A PAGAR:** {precioFinal:F2} euros");
            Console.WriteLine("===============================================");
        }
    }

    /// <summary>
    /// Método auxiliar para solicitar una entrada numérica (double) de forma segura.
    /// Utiliza TryParse para robustez.
    /// </summary>
    static double PedirDouble(string mensaje)
    {
        double numero;
        Console.Write(mensaje);
        // Bucle que garantiza que el usuario ingrese un número válido y positivo.
        while (!double.TryParse(Console.ReadLine(), out numero) || numero < 0)
        {
            Console.Write("❌ Entrada inválida. Por favor, ingrese un valor numérico positivo: ");
        }
        return numero;
    }
}