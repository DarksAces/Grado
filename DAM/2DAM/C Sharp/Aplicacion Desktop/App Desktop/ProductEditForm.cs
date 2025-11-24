using System;
using System.Drawing;
using System.Windows.Forms;

namespace App_Desktop
{
    public class ProductEditForm : Form
    {
        public Producto Producto { get; private set; } = new Producto();

        private readonly TextBox txtNombre = new() { Width = 250 };
        private readonly TextBox txtCategoria = new() { Width = 150 };
        private readonly NumericUpDown nudPrecio = new() { DecimalPlaces = 2, Maximum = 10000, Width = 100 };
        private readonly NumericUpDown nudStock = new() { DecimalPlaces = 2, Maximum = 100000, Width = 100 };

        public ProductEditForm() : this(null) { }

        public ProductEditForm(Producto? p)
        {
            Text = p == null ? "Nuevo Producto" : "Editar Producto";
            Size = new Size(420, 220);
            if (p != null) Producto = new Producto
            {
                id = p.id,
                nombre = p.nombre,
                categoria = p.categoria,
                precioKg = p.precioKg,
                stockKg = p.stockKg
            };
            BuildUi();
            if (p != null) LoadFromModel();
        }

        private void BuildUi()
        {
            var lblNombre = new Label { Text = "Nombre:", Location = new Point(10, 15), AutoSize = true };
            txtNombre.Location = new Point(100, 12);

            var lblCat = new Label { Text = "Categoría:", Location = new Point(10, 50), AutoSize = true };
            txtCategoria.Location = new Point(100, 47);

            var lblPrecio = new Label { Text = "Precio €/Kg:", Location = new Point(10, 85), AutoSize = true };
            nudPrecio.Location = new Point(100, 82);

            var lblStock = new Label { Text = "Stock Kg:", Location = new Point(10, 120), AutoSize = true };
            nudStock.Location = new Point(100, 117);

            var btnOk = new Button { Text = "OK", Location = new Point(240, 150), DialogResult = DialogResult.OK };
            btnOk.Click += (s, e) => { SaveToModel(); Close(); };

            var btnCancel = new Button { Text = "Cancelar", Location = new Point(320, 150), DialogResult = DialogResult.Cancel };

            Controls.AddRange(new Control[] { lblNombre, txtNombre, lblCat, txtCategoria, lblPrecio, nudPrecio, lblStock, nudStock, btnOk, btnCancel });
        }

        private void LoadFromModel()
        {
            txtNombre.Text = Producto.nombre;
            txtCategoria.Text = Producto.categoria;
            nudPrecio.Value = (decimal)Producto.precioKg;
            nudStock.Value = (decimal)Producto.stockKg;
        }

        private void SaveToModel()
        {
            Producto.nombre = txtNombre.Text.Trim();
            Producto.categoria = txtCategoria.Text.Trim();
            Producto.precioKg = (double)nudPrecio.Value;
            Producto.stockKg = (double)nudStock.Value;
        }
    }
}