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

        private DateTimePicker dtpAlarma;
        private Button btnFijarAlarma;

        private void ConfigurarReloj()
        {
         
            reloj = new RelojDigital();
            
       
            reloj.Location = new Point(50, 50);
            reloj.Size = new Size(300, 120);
            reloj.BorderStyle = BorderStyle.FixedSingle; // Make it visible
            reloj.BackColor = Color.LightCyan;
            
            reloj.FormatoHora = "HH:mm:ss";
            reloj.ColorTexto = Color.DarkBlue;
            reloj.MostrarSegundos = true;
            
         
            reloj.AlarmaActivada += Reloj_AlarmaActivada;

       
            this.Controls.Add(reloj);

            
            ConfigurarControlesAlarma();

            
            reloj.Iniciar();
        }

        private void ConfigurarControlesAlarma()
        {
          
            dtpAlarma = new DateTimePicker();
            dtpAlarma.Format = DateTimePickerFormat.Time;
            dtpAlarma.ShowUpDown = true;
            dtpAlarma.Location = new Point(50, 200);
            dtpAlarma.Size = new Size(100, 30);
            this.Controls.Add(dtpAlarma);

                        
            btnFijarAlarma = new Button();
            btnFijarAlarma.Text = "Fijar Alarma";
            btnFijarAlarma.Location = new Point(160, 200);
            btnFijarAlarma.Size = new Size(100, 30);
            btnFijarAlarma.Click += BtnFijarAlarma_Click;
            this.Controls.Add(btnFijarAlarma);
        }

        private void BtnFijarAlarma_Click(object sender, EventArgs e)
        {
            reloj.HoraAlarma = dtpAlarma.Value;
            MessageBox.Show($"Alarma establecida a las: {reloj.HoraAlarma.Value.ToLongTimeString()}", "Alarma Configurada");
        }

        private void Reloj_AlarmaActivada(object sender, EventArgs e)
        {
            MessageBox.Show("¡Alarma Activada!", "Aviso", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }
    }
}
