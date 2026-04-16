using System;
using System.Drawing;
using System.Windows.Forms;

namespace PanaderiaPOS
{
    public partial class FormMenu : Form
    {
        public FormMenu()
        {
            this.Text = "Bakery POS - Menú Principal";
            this.Size = new Size(800, 500);
            this.StartPosition = FormStartPosition.CenterScreen; // Centrar ventana
            this.BackColor = Color.FromArgb(248, 248, 248); // Fondo claro

            // Título
            Label titulo = new Label();
            titulo.Text = "PANADERÍA TPV";
            titulo.Font = new Font("Segoe UI", 24, FontStyle.Bold);
            titulo.ForeColor = Color.FromArgb(93, 53, 24); // Marrón
            titulo.AutoSize = true;
            titulo.Location = new Point(280, 50);
            this.Controls.Add(titulo);

            // Botones
            CrearBoton("🛒 PUNTO DE VENTA", 150, () => AbrirForm(new FormVentas()));

            // Nota: Si aún no has creado FormCatalogo o FormStock, comenta estas líneas poniendo // delante
            CrearBoton("📦 CATÁLOGO", 230, () => AbrirForm(new FormCatalogo()));
            CrearBoton("📊 STOCK", 310, () => AbrirForm(new FormStock()));
            CrearBoton("📈 INFORMES", 390, () => AbrirForm(new FormInformes()));
        }

        private void CrearBoton(string texto, int top, Action accion)
        {
            Button btn = new Button();
            btn.Text = texto;
            btn.Location = new Point(250, top);
            btn.Size = new Size(300, 60);
            btn.BackColor = Color.FromArgb(242, 139, 52); // Naranja
            btn.ForeColor = Color.White;
            btn.FlatStyle = FlatStyle.Flat;
            btn.Font = new Font("Segoe UI", 12, FontStyle.Bold);
            btn.Cursor = Cursors.Hand;
            btn.Click += (s, e) => accion();
            this.Controls.Add(btn);
        }

        private void AbrirForm(Form form)
        {
            this.Hide(); // Ocultar menú
            form.ShowDialog(); // Abrir la otra ventana
            this.Show(); // Mostrar menú al volver
        }
    }
}