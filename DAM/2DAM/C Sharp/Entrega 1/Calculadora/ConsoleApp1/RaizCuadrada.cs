using System;

class RaizCuadrada : Operacion
{
    private double a;
    public RaizCuadrada(double a) { this.a = a; }
    public override double Calcular()
    {
        if (a < 0) throw new ArgumentException("No se puede calcular la raíz de un número negativo.");
        return Math.Sqrt(a);
    }
}

