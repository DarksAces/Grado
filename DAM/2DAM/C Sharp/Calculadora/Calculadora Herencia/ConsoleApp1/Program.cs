using System;

class Calculadora
{
    static void Main()
    {
        bool salir = false;

        while (!salir)
        {
            MostrarMenu();
            Console.Write("Seleccione una opción: ");
            string opcion = Console.ReadLine();

            try
            {
                // Se inicializa en null hasta que el usuario elija la operación en el switch
                Operacion operacion = null;

                switch (opcion)
                {
                    //Si el usuario ingresa 1 llamamos a la funcion suma
                    case "1":
                        operacion = new Suma(PedirNumero("Ingrese el primer número: "), PedirNumero("Ingrese el segundo número: "));
                        break;
                    //Si el usuario ingresa 2 llamamos a la funcion resta
                    case "2":
                        operacion = new Resta(PedirNumero("Ingrese el primer número: "), PedirNumero("Ingrese el segundo número: "));
                        break;
                    //Si el usuario ingresa 3 llamamos a la funcion multiplicacion
                    case "3":
                        operacion = new Multiplicacion(PedirNumero("Ingrese el primer número: "), PedirNumero("Ingrese el segundo número: "));
                        break;
                    //Si el usuario ingresa 4 llamamos a la funcion division
                    case "4":
                        operacion = new Division(PedirNumero("Ingrese el numerador: "), PedirNumero("Ingrese el denominador: "));
                        break;
                    //Si el usuario ingresa 5 llamamos a la funcion potencia
                    case "5":
                        operacion = new Potencia(PedirNumero("Ingrese la base: "), PedirNumero("Ingrese el exponente: "));
                        break;
                    //Si el usuario ingresa 6 llamamos a la funcion raiz cuadrada
                    case "6":
                        operacion = new RaizCuadrada(PedirNumero("Ingrese el número: "));
                        break;
                    //Si el usuario ingresa 7 cambiamos el valor de salir a true y el programa termina
                    case "7":
                        salir = true;
                        Console.WriteLine("Saliendo del programa...");
                        break;
                    default:
                        Console.WriteLine("Opción inválida. Por favor, seleccione una opción del 1 al 7.");
                        break;
                }

                if (operacion != null)
                    Console.WriteLine($"Resultado: {operacion.Calcular()}");
            }
            // Manejamos excepciones para que no pete el programa
            catch (Exception e)
            {
                Console.WriteLine($"Error: {e.Message}");
            }

            Console.WriteLine("\nPresione cualquier tecla para continuar...");
            Console.ReadKey();
            Console.Clear();
        }
    }

    static void MostrarMenu()
    {
        Console.WriteLine("=== Calculadora Científica Básica (Herencia) ===");
        Console.WriteLine("1. Suma");
        Console.WriteLine("2. Resta");
        Console.WriteLine("3. Multiplicación");
        Console.WriteLine("4. División");
        Console.WriteLine("5. Potencia");
        Console.WriteLine("6. Raíz cuadrada");
        Console.WriteLine("7. Salir");
    }

    static double PedirNumero(string mensaje)
    {
        double numero;
        Console.Write(mensaje);
        while (!double.TryParse(Console.ReadLine(), out numero))
        {
            Console.Write("Entrada inválida. Intente de nuevo: ");
        }
        return numero;
    }
}

