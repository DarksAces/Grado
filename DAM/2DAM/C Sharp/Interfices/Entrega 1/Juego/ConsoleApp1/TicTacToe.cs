using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;




using System;

namespace Juegos
{
    public static class TicTacToe
    {
        static char[,] tablero = new char[5, 5]; // Tablero 5x5
        static char jugadorActual = 'X';
        static int fichasParaGanar = 4; // Cambia a 5 si quieres 5 en raya

        public static void Jugar()
        {
            InicializarTablero();
            bool juegoTerminado = false;

            while (!juegoTerminado)
            {
                Console.Clear();
                DibujarTablero();
                Console.WriteLine($"Turno del jugador {jugadorActual}");
                Console.Write("Ingresa fila (0-4): ");
                int fila = int.Parse(Console.ReadLine());
                Console.Write("Ingresa columna (0-4): ");
                int columna = int.Parse(Console.ReadLine());

                if (fila < 0 || fila > 4 || columna < 0 || columna > 4)
                {
                    Console.WriteLine("Entrada fuera de rango. Presiona Enter para continuar...");
                    Console.ReadLine();
                    continue;
                }

                if (tablero[fila, columna] == ' ')
                {
                    tablero[fila, columna] = jugadorActual;
                    if (HayGanador(fila, columna))
                    {
                        Console.Clear();
                        DibujarTablero();
                        Console.WriteLine($"¡Jugador {jugadorActual} gana!");
                        juegoTerminado = true;
                    }
                    else if (TableroLleno())
                    {
                        Console.Clear();
                        DibujarTablero();
                        Console.WriteLine("¡Empate!");
                        juegoTerminado = true;
                    }
                    else
                    {
                        CambiarJugador();
                    }
                }
                else
                {
                    Console.WriteLine("Casilla ocupada, presiona Enter y prueba otra.");
                    Console.ReadLine();
                }
            }

            Console.WriteLine("Presiona Enter para volver al menú...");
            Console.ReadLine();
        }

        static void InicializarTablero()
        {
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 5; j++)
                    tablero[i, j] = ' ';
        }

        static void DibujarTablero()
        {
            Console.Write("   ");
            for (int i = 0; i < 5; i++) Console.Write(i + " ");
            Console.WriteLine();

            for (int i = 0; i < 5; i++)
            {
                Console.Write(i + "  ");
                for (int j = 0; j < 5; j++)
                {
                    Console.Write(tablero[i, j]);
                    if (j < 4) Console.Write("|");
                }
                Console.WriteLine();
                if (i < 4) Console.WriteLine("   -----------");
            }
        }

        static void CambiarJugador()
        {
            jugadorActual = (jugadorActual == 'X') ? 'O' : 'X';
        }

        static bool TableroLleno()
        {
            foreach (char c in tablero)
                if (c == ' ') return false;
            return true;
        }

        static bool HayGanador(int fila, int columna)
        {
            // Comprobar todas las direcciones desde la última ficha
            return RevisarLinea(fila, columna, 1, 0) || // Horizontal
                   RevisarLinea(fila, columna, 0, 1) || // Vertical
                   RevisarLinea(fila, columna, 1, 1) || // Diagonal \
                   RevisarLinea(fila, columna, 1, -1);  // Diagonal /
        }

        static bool RevisarLinea(int fila, int col, int dirFila, int dirCol)
        {
            int contador = 1;

            // Revisar en la dirección positiva
            int f = fila + dirFila;
            int c = col + dirCol;
            while (f >= 0 && f < 5 && c >= 0 && c < 5 && tablero[f, c] == jugadorActual)
            {
                contador++;
                f += dirFila;
                c += dirCol;
            }

            // Revisar en la dirección negativa
            f = fila - dirFila;
            c = col - dirCol;
            while (f >= 0 && f < 5 && c >= 0 && c < 5 && tablero[f, c] == jugadorActual)
            {
                contador++;
                f -= dirFila;
                c -= dirCol;
            }

            return contador >= fichasParaGanar;
        }
    }
}
