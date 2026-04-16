using System;
using Juegos;

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

            string opcion = Console.ReadLine().ToUpper();

            switch (opcion)
            {
                case "1":
                    TicTacToe.Jugar();
                    break;
                case "2":
                    PiedraPapelTijeras.Jugar();
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
