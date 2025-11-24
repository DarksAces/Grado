using System;

namespace App_Desktop
{
    public class Producto
    {
        public int id { get; set; }
        public string nombre { get; set; } = string.Empty;
        public string categoria { get; set; } = string.Empty;
        public double precioKg { get; set; }
        public double stockKg { get; set; }
    }

    public class Venta
    {
        public int id { get; set; }
        public int productoId { get; set; }
        public string productoNombre { get; set; } = string.Empty;
        public double kilos { get; set; }
        public double total { get; set; }
        public DateTime fecha { get; set; }
    }

    public class VentaRequest
    {
        public int productoId { get; set; }
        public double kilos { get; set; }
    }
}