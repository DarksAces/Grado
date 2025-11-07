using System;
using System.Windows.Forms;

namespace WinFormsApp1
{
    internal static class Program
    {
        [STAThread]
        static void Main()
        {
            // Inicializa la configuración moderna de WinForms (.NET 6+)
            ApplicationConfiguration.Initialize();

            // Lanza el formulario principal de la app
            Application.Run(new Form1());
        }
    }
}
