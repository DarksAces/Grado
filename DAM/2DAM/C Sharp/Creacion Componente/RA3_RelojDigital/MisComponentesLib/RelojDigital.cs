using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace MisComponentesLib
{
    public partial class RelojDigital : UserControl
    {
        // Internal timer
        private Timer tmrReloj;

        // Custom properties
        private string formatoHora = "HH:mm:ss";
        private bool mostrarSegundos = true;
        private Color colorTexto = Color.Black;
        private DateTime? horaAlarma = null;
        private bool alarmaDisparada = false;

        // Event
        public event EventHandler AlarmaActivada;

        [Category("Configuracion Reloj")]
        [Description("Formato de hora para mostrar (ej. HH:mm:ss)")]
        public string FormatoHora
        {
            get { return formatoHora; }
            set { formatoHora = value; Invalidate(); }
        }

        [Category("Configuracion Reloj")]
        [Description("Indica si se deben mostrar los segundos")]
        public bool MostrarSegundos
        {
            get { return mostrarSegundos; }
            set 
            { 
                mostrarSegundos = value;
                // Update format automatically if simple switch
                if (value && !formatoHora.Contains("ss")) formatoHora = "HH:mm:ss";
                else if (!value && formatoHora.Contains("ss")) formatoHora = "HH:mm";
                Invalidate(); 
            }
        }

        [Category("Configuracion Reloj")]
        [Description("Color del texto del reloj")]
        public Color ColorTexto
        {
            get { return colorTexto; }
            set { colorTexto = value; Invalidate(); }
        }

        [Category("Configuracion Reloj")]
        [Description("Hora a la que sonará la alarma")]
        public DateTime? HoraAlarma
        {
            get { return horaAlarma; }
            set { 
                horaAlarma = value; 
                alarmaDisparada = false; // Reset alarm state when time changes
            }
        }

        public RelojDigital()
        {
            InitializeComponent();
            
            // Set styles for smoother drawing
            this.SetStyle(ControlStyles.UserPaint | 
                          ControlStyles.AllPaintingInWmPaint | 
                          ControlStyles.OptimizedDoubleBuffer, true);
            
            this.tmrReloj = new Timer();
            this.tmrReloj.Interval = 1000; // 1 second
            this.tmrReloj.Tick += TmrReloj_Tick;
        }

        private void TmrReloj_Tick(object sender, EventArgs e)
        {
            // Trigger repaint
            this.Invalidate();

            // Check alarm
            if (horaAlarma.HasValue && !alarmaDisparada)
            {
                DateTime now = DateTime.Now;
                // Simple check: same hour, minute, second
                if (now.Hour == horaAlarma.Value.Hour &&
                    now.Minute == horaAlarma.Value.Minute &&
                    now.Second == horaAlarma.Value.Second)
                {
                    alarmaDisparada = true;
                    OnAlarmaActivada(EventArgs.Empty);
                }
            }
        }

        protected virtual void OnAlarmaActivada(EventArgs e)
        {
            AlarmaActivada?.Invoke(this, e);
        }

        public void Iniciar()
        {
            tmrReloj.Start();
        }

        public void Detener()
        {
            tmrReloj.Stop();
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            base.OnPaint(e);
            e.Graphics.Clear(this.BackColor);

            string tiempoStr = DateTime.Now.ToString(formatoHora);

            // Center the text
            using (Font font = new Font(this.Font.FontFamily, 24, FontStyle.Bold))
            using (Brush brush = new SolidBrush(colorTexto))
            {
                SizeF textSize = e.Graphics.MeasureString(tiempoStr, font);
                float x = (this.Width - textSize.Width) / 2;
                float y = (this.Height - textSize.Height) / 2;

                e.Graphics.DrawString(tiempoStr, font, brush, x, y);
            }
        }
    }
}
