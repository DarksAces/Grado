using System;

class Potencia : Operacion
{
    private double a, b;
    public Potencia(double a, double b) { this.a = a; this.b = b; }
    // Clase base abstracta para todas las operaciones; obliga a que cada operación implemente el método Calcular().
    public override double Calcular() => Math.Pow(a, b);
}

