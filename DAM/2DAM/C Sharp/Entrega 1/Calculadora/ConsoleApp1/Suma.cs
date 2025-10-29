class Suma : Operacion
{
    private double a, b;
    public Suma(double a, double b) { this.a = a; this.b = b; }
    public override double Calcular() => a + b;
}

