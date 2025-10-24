using System;
using System.Collections.Generic;

class PiedraPapelTijeras
{
    static Dictionary<string, List<string>> reglas = new Dictionary<string, List<string>>()
    {
        {"Piedra", new List<string>{"Tijeras", "Lagarto"}},
        {"Papel", new List<string>{"Piedra", "Spock"}},
        {"Tijeras", new List<string>{"Papel", "Lagarto"}},
        {"Lagarto", new List<string>{"Spock", "Papel"}},
        {"Spock", new List<string>{"Tijeras", "Piedra"}},
        {"Serpiente", new List<string>{"Papel", "Spock"}}, // tu elemento extra
        {"Spook", new List<string>{"Tijeras", "Serpiente"}} // si Spook = Spock
    };

    public static void Jugar()
    {
        Console.Clear();
        Console.WriteLine("¡Has iniciado Piedra, Papel, Tijeras extendido!");
        Console.WriteLine("Opciones: Piedra, Papel, Tijeras, Lagarto, Spock, Serpiente, Spook");

        Console.Write("Jugador 1 elige: ");
        string jugador1 = Console.ReadLine();
        Console.Write("Jugador 2 elige: ");
        string jugador2 = Console.ReadLine();

        string resultado = DeterminarGanador(jugador1, jugador2);
        Console.WriteLine(resultado);
        Console.WriteLine("Presiona Enter para volver al menú...");
        Console.ReadLine();
    }

    static string DeterminarGanador(string j1, string j2)
    {
        j1 = Capitalizar(j1);
        j2 = Capitalizar(j2);

        if (j1 == j2) return "Empate";

        if (reglas.ContainsKey(j1) && reglas[j1].Contains(j2))
            return $"{j1} gana a {j2} — ¡Jugador 1 gana!";

        if (reglas.ContainsKey(j2) && reglas[j2].Contains(j1))
            return $"{j2} gana a {j1} — ¡Jugador 2 gana!";

        return "Opción inválida";
    }

    static string Capitalizar(string palabra)
    {
        if (string.IsNullOrEmpty(palabra)) return palabra;
        return char.ToUpper(palabra[0]) + palabra.Substring(1).ToLower();
    }
}
