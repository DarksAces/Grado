using System;
using System.Drawing;
using System.Windows.Forms;
using MisComponentesLib;

namespace TestRelojDigital
{
    public partial class Form1 : Form
    {
        private RelojDigital reloj;

        public Form1()
        {
            InitializeComponent();
            ConfigurarReloj();
        }

        private void ConfigurarReloj()
        {
            // 1. Create instance
            reloj = new RelojDigital();
            
            // 2. Configure position and visual properties
            reloj.Location = new Point(50, 50);
            reloj.Size = new Size(300, 120);
            reloj.BorderStyle = BorderStyle.FixedSingle; // Make it visible
            reloj.BackColor = Color.LightCyan;
            
            reloj.FormatoHora = "HH:mm:ss";
            reloj.ColorTexto = Color.DarkBlue;
            reloj.MostrarSegundos = true;
            
            // 3. Set alarm (e.g., 1 minute from now)
            reloj.HoraAlarma = DateTime.Now.AddMinutes(1);

            // 4. Subscribe to event
            reloj.AlarmaActivada += Reloj_AlarmaActivada;

            // 5. Add to controls
            this.Controls.Add(reloj);

            // 6. Start
            reloj.Iniciar();
        }

        private void Reloj_AlarmaActivada(object sender, EventArgs e)
        {
            MessageBox.Show("¡Alarma Activada!", "Aviso", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }
    }
}
