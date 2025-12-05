using System;
using System.Drawing;
using System.Windows.Forms;
using System.Threading.Tasks;

namespace PanaderiaPOS
{
    public partial class FormInformes : Form
    {
        public FormInformes()
        {
            // Configuración ventana
            this.Text = "Informes del Día";
            this.Size = new Size(500, 350);
            this.StartPosition = FormStartPosition.CenterParent;
            this.BackColor = Tema.Fondo;

            // Etiqueta grande central
            Label lblResumen = new Label();
            lblResumen.Text = "Cargando datos...";
            lblResumen.AutoSize = false;
            lblResumen.Dock = DockStyle.Fill; // Ocupa todo el centro
            lblResumen.TextAlign = ContentAlignment.MiddleCenter;
            lblResumen.Font = new Font("Segoe UI", 14);

            this.Controls.Add(lblResumen);

            // Al cargar, llamamos a la API
            this.Load += async (sender, e) =>
            {
                ApiClient api = new ApiClient();
                Informe info = await api.GetInforme();

                // Usamos 'Total' y 'Cantidad' con Mayúscula (según Ayudantes.cs)
                lblResumen.Text = "📊 RESUMEN DE VENTAS DE HOY\n\n" +
                                  "----------------------------------\n" +
                                  $"🛒 Nº Ventas:      {info.Cantidad}\n\n" +
                                  $"💰 Total Caja:     {info.Total.ToString("0.00")} €\n" +
                                  "----------------------------------";
            };
        }
    }
}