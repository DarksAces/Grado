using System;
using Juegos; // Tu namespace con las clases de juegos

class Program
{
    static void Main()
    {
        bool salir = false;

        while (!salir)
        {
            Console.Clear();
            Console.WriteLine("=== Menú de selección de juego ===");
            Console.WriteLine("1: Tic Tac Toe (Tres en raya)");
            Console.WriteLine("2: Piedra Papel o Tijeras");
            Console.WriteLine("Z: Salir");
            Console.Write("Escribe la opción que quieres: ");

            string opcion = Console.ReadLine();
            string opcionMayuscula = opcion.ToUpper();

            switch (opcionMayuscula)
            {
                case "1":
                    TicTacToe.Jugar(); // Ejemplo, reemplaza con tu clase/función
                    break;

                case "2":
                    PiedraPapelTijeras.Jugar(); // Ejemplo, reemplaza con tu clase/función
                    break;

                case "Z":
                    salir = true;
                    Console.WriteLine("Saliendo del programa...");
                    break;

                default:
                    Console.WriteLine("Opción inválida. Presiona Enter para continuar...");
                    Console.ReadLine();
                    break;
            }
        }
    }
}

