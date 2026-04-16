class Multiplicacion : Operacion
{
    private double a, b;
    public Multiplicacion(double a, double b) { this.a = a; this.b = b; }
    // override indica que una clase hija reemplaza la versión de un método de la clase base.
    public override double Calcular() => a * b;
}

