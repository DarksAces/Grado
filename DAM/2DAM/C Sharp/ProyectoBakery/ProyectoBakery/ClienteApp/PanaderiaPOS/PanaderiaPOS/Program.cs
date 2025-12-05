using System;
using System.Windows.Forms;

namespace PanaderiaPOS
{
    internal static class Program
    {
        /// <summary>
        ///  Punto de entrada principal para la aplicación.
        /// </summary>
        [STAThread]
        static void Main()
        {
            // Configuración estándar para .NET Core / .NET 6+
            Application.SetHighDpiMode(HighDpiMode.SystemAware);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            // AQUÍ ESTÁ EL ARREGLO:
            // Le decimos que arranque el FormMenu que acabamos de crear.
            // Si te da error rojo en 'FormMenu', es que no creaste el archivo del Paso 1.
            Application.Run(new FormMenu());
        }
    }
}