class Resta : Operacion
{
    private double a, b;
    public Resta(double a, double b) { this.a = a; this.b = b; }
    public override double Calcular() => a - b;
}

