using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;
using System.Threading.Tasks;

namespace PanaderiaPOS
{
    public partial class FormVentas : Form
    {
        ApiClient api = new ApiClient();
        List<ItemVenta> carrito = new List<ItemVenta>();

        DataGridView gridProductos;
        DataGridView gridCarrito;
        Label lblTotal;
        NumericUpDown numCantidad;

        public FormVentas()
        {
            this.Text = "Punto de Venta (TPV)";
            this.WindowState = FormWindowState.Maximized;
            this.BackColor = Tema.Fondo;

            SplitContainer split = new SplitContainer();
            split.Dock = DockStyle.Fill;
            this.Controls.Add(split);

            // --- LADO IZQUIERDO: CATÁLOGO ---
            Panel panelTopIzq = new Panel { Dock = DockStyle.Top, Height = 60, BackColor = Tema.Marron };
            Label lblInstr = new Label { Text = "1. Elige Producto:", ForeColor = Color.White, Font = new Font("Segoe UI", 12, FontStyle.Bold), Location = new Point(10, 18), AutoSize = true };
            numCantidad = new NumericUpDown { Location = new Point(180, 18), Width = 60, Font = new Font("Segoe UI", 12), Minimum = 1, Value = 1 };
            Button btnAdd = new Button { Text = "AÑADIR +", Location = new Point(260, 15), Width = 120, Height = 35, BackColor = Tema.Naranja, ForeColor = Color.White, FlatStyle = FlatStyle.Flat, Font = new Font("Segoe UI", 10, FontStyle.Bold) };
            btnAdd.Click += BotonAgregar_Click;

            panelTopIzq.Controls.Add(lblInstr);
            panelTopIzq.Controls.Add(numCantidad);
            panelTopIzq.Controls.Add(btnAdd);

            gridProductos = new DataGridView
            {
                Dock = DockStyle.Fill,
                BackgroundColor = Color.White,
                BorderStyle = BorderStyle.None,
                ReadOnly = true,
                SelectionMode = DataGridViewSelectionMode.FullRowSelect,
                AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill,
                RowHeadersVisible = false,
                AllowUserToAddRows = false // IMPORTANTE: Evita filas vacías
            };

            split.Panel1.Controls.Add(gridProductos);
            split.Panel1.Controls.Add(panelTopIzq);

            // --- LADO DERECHO: CARRITO ---
            Panel pRightTop = new Panel { Dock = DockStyle.Top, Height = 60, BackColor = Color.White };
            Label lblC = new Label { Text = "2. Carrito", ForeColor = Tema.Marron, Font = new Font("Segoe UI", 14, FontStyle.Bold), Location = new Point(10, 15), AutoSize = true };

            Button btnDel = new Button { Text = "🗑 Quitar", Location = new Point(300, 15), Width = 100, Height = 35, BackColor = Tema.Rojo, ForeColor = Color.White, FlatStyle = FlatStyle.Flat, Font = new Font("Segoe UI", 9, FontStyle.Bold) };
            btnDel.Click += BotonEliminar_Click;

            pRightTop.Controls.Add(lblC);
            pRightTop.Controls.Add(btnDel);

            gridCarrito = new DataGridView
            {
                Dock = DockStyle.Fill,
                BackgroundColor = Color.White,
                BorderStyle = BorderStyle.None,
                ReadOnly = true,
                AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill,
                RowHeadersVisible = false,
                SelectionMode = DataGridViewSelectionMode.FullRowSelect,
                AllowUserToAddRows = false // ESTO ARREGLA TU ERROR DEL 'NULL'
            };
            gridCarrito.Columns.Add("Prod", "Producto");
            gridCarrito.Columns.Add("Cant", "Cant.");
            gridCarrito.Columns.Add("Sub", "Subtotal");

            Panel panelAbajoDer = new Panel { Dock = DockStyle.Bottom, Height = 120, BackColor = Color.White };
            lblTotal = new Label { Text = "TOTAL: 0.00 €", Dock = DockStyle.Top, Height = 50, Font = new Font("Segoe UI", 20, FontStyle.Bold), ForeColor = Tema.Marron, TextAlign = ContentAlignment.MiddleRight };
            Button btnPagar = new Button { Text = "CONFIRMAR VENTA", Dock = DockStyle.Bottom, Height = 60, BackColor = Tema.Verde, ForeColor = Color.White, FlatStyle = FlatStyle.Flat, Font = new Font("Segoe UI", 16, FontStyle.Bold), Cursor = Cursors.Hand };
            btnPagar.Click += BotonPagar_Click;

            panelAbajoDer.Controls.Add(lblTotal);
            panelAbajoDer.Controls.Add(btnPagar);

            split.Panel2.Controls.Add(gridCarrito);
            split.Panel2.Controls.Add(pRightTop);
            split.Panel2.Controls.Add(panelAbajoDer);

            this.Load += async (s, e) => await CargarCatalogo();
        }

        private async Task CargarCatalogo()
        {
            gridProductos.DataSource = await api.GetProductos();
        }

        private void BotonAgregar_Click(object sender, EventArgs e)
        {
            if (gridProductos.SelectedRows.Count == 0) return;

            var fila = gridProductos.SelectedRows[0];

            if (fila.Cells["Id"].Value == null) return; // Protección extra

            int id = (int)fila.Cells["Id"].Value;
            string nombre = fila.Cells["Nombre"].Value?.ToString() ?? "Producto";
            float precio = 0;
            float.TryParse(fila.Cells["Precio"].Value?.ToString(), out precio);
            int cantidad = (int)numCantidad.Value;

            carrito.Add(new ItemVenta { ProductoId = id, Cantidad = cantidad });

            float subtotal = precio * cantidad;
            gridCarrito.Rows.Add(nombre, cantidad, subtotal.ToString("0.00") + " €");

            numCantidad.Value = 1;
            CalcularTotal();
        }

        private void BotonEliminar_Click(object sender, EventArgs e)
        {
            if (gridCarrito.SelectedRows.Count == 0)
            {
                MessageBox.Show("Selecciona una fila del carrito para borrarla.");
                return;
            }

            int indice = gridCarrito.SelectedRows[0].Index;

            gridCarrito.Rows.RemoveAt(indice);
            if (indice < carrito.Count) carrito.RemoveAt(indice);

            CalcularTotal();
        }

        private void CalcularTotal()
        {
            float total = 0;
            foreach (DataGridViewRow fila in gridCarrito.Rows)
            {
                if (fila.Cells[2].Value == null) continue; // Protección contra nulls

                string texto = fila.Cells[2].Value.ToString().Replace(" €", "").Trim();
                if (float.TryParse(texto, out float valor))
                {
                    total += valor;
                }
            }
            lblTotal.Text = "TOTAL: " + total.ToString("0.00") + " €";
        }

        private async void BotonPagar_Click(object sender, EventArgs e)
        {
            if (carrito.Count == 0) { MessageBox.Show("Carrito vacío."); return; }

            Venta v = new Venta { Items = carrito };

            if (await api.PostVenta(v))
            {
                MessageBox.Show("¡Venta realizada! 🥖");
                carrito.Clear();
                gridCarrito.Rows.Clear();
                CalcularTotal();
                await CargarCatalogo();
            }
            else MessageBox.Show("Error: Stock insuficiente.");
        }
    }
}