using System;

class Calculadora
{
    static void Main()
    {
        //Creamos un booleano para que solo deje el programa si cambiamos su valor a true
        bool salir = false;

        while (!salir)
        {
            MostrarMenu();
            Console.Write("Seleccione una opción: ");
            //Console.ReadLine = Cin
            string opcion = Console.ReadLine();

            switch (opcion)
            {
                //Si el usuario ingresa 1 llamamos a la funcion suma
                case "1":
                    double sumaA = PedirNumero("Ingrese el primer número: ");
                    double sumaB = PedirNumero("Ingrese el segundo número: ");
                    Console.WriteLine($"Resultado: {Suma(sumaA, sumaB)}");
                    break;
                //Si el usuario ingresa 2 llamamos a la funcion resta
                case "2":
                    double restaA = PedirNumero("Ingrese el primer número: ");
                    double restaB = PedirNumero("Ingrese el segundo número: ");
                    Console.WriteLine($"Resultado: {Resta(restaA, restaB)}");
                    break;
                //Si el usuario ingresa 3 llamamos a la funcion multiplicacion
                case "3":
                    double mulA = PedirNumero("Ingrese el primer número: ");
                    double mulB = PedirNumero("Ingrese el segundo número: ");
                    Console.WriteLine($"Resultado: {Multiplicacion(mulA, mulB)}");
                    break;
                //Si el usuario ingresa 4 llamamos a la funcion division
                case "4":
                    double divA = PedirNumero("Ingrese el numerador: ");
                    double divB = PedirNumero("Ingrese el denominador: ");
                    if (divB != 0)
                        Console.WriteLine($"Resultado: {Division(divA, divB)}");
                    else
                        Console.WriteLine("Error: No se puede dividir entre cero.");
                    break;
                //Si el usuario ingresa 5 llamamos a la funcion potencia
                case "5":
                    double basePot = PedirNumero("Ingrese la base: ");
                    double exponente = PedirNumero("Ingrese el exponente: ");
                    Console.WriteLine($"Resultado: {Potencia(basePot, exponente)}");
                    break;
                //Si el usuario ingresa 6 llamamos a la funcion raiz cuadrada
                case "6":
                    double raizNum = PedirNumero("Ingrese el número: ");
                    if (raizNum >= 0)
                        Console.WriteLine($"Resultado: {RaizCuadrada(raizNum)}");
                    else
                        Console.WriteLine("Error: No se puede calcular la raíz de un número negativo.");
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
            // Console.Write Line = Cout
            Console.WriteLine("\nPresione cualquier tecla para continuar...");
            Console.ReadKey();
            Console.Clear();
        }
    }

    //Definimos la funcion que muestra el menu
    static void MostrarMenu()
    {
        Console.WriteLine("=== Calculadora Científica Básica ===");
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
        //Try Parse = intenta convertir un valor de tipo string a otro tipo de dato como int sin que el programa lance una excepción si falla.
        //Basicamente es como un try catch pero mas limpio
        while (!double.TryParse(Console.ReadLine(), out numero))
        {
            Console.Write("Entrada inválida. Intente de nuevo: ");
        }
        return numero;
    }
    //Declaramos las funciones que usaremos en el programa
    static double Suma(double a, double b) => a + b;
    static double Resta(double a, double b) => a - b;
    static double Multiplicacion(double a, double b) => a * b;
    static double Division(double a, double b) => a / b;
    static double Potencia(double a, double b) => Math.Pow(a, b);
    static double RaizCuadrada(double a) => Math.Sqrt(a);
}

