using System;

class Division : Operacion
{
    private double a, b;
    public Division(double a, double b) { this.a = a; this.b = b; }
    public override double Calcular()
    {
        if (b == 0) throw new DivideByZeroException("No se puede dividir entre cero.");
        return a / b;
    }
}

