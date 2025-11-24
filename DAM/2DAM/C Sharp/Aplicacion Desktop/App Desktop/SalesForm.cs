using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace App_Desktop
{
    public class SalesForm : Form
    {
        private readonly ApiClient _api;
        private readonly List<Producto> _productos = new();
        private readonly ComboBox _cmbProductos = new() { Width = 300 };
        private readonly NumericUpDown _nudKilos = new() { DecimalPlaces = 2, Maximum = 1000, Width = 100 };
        private readonly DataGridView _cartGrid = new() { Size = new Size(560, 200) };
        private readonly List<(Producto producto, double kilos)> _cart = new();
        private readonly Label _lblTotal = new() { AutoSize = true };

        public SalesForm(ApiClient api)
        {
            _api = api ?? throw new ArgumentNullException(nameof(api));
            Text = "Registro de Ventas";
            Size = new Size(600, 420);
            BuildUi();
            Load += async (s, e) => await LoadProductosAsync();
        }

        private void BuildUi()
        {
            var lblProd = new Label { Text = "Producto:", Location = new Point(10, 15), AutoSize = true };
            _cmbProductos.Location = new Point(80, 12);

            var lblKilos = new Label { Text = "Kilos:", Location = new Point(400, 15), AutoSize = true };
            _nudKilos.Location = new Point(445, 12);

            var btnAdd = new Button { Text = "Añadir al carrito", Location = new Point(10, 45) };
            btnAdd.Click += (s, e) =>
            {
                if (_cmbProductos.SelectedItem is Producto p && _nudKilos.Value > 0)
                {
                    _cart.Add((p, (double)_nudKilos.Value));
                    RefreshCart();
                }
            };

            _cartGrid.Location = new Point(10, 80);
            _cartGrid.Size = new Size(560, 200);
            _cartGrid.AutoGenerateColumns = false;
            _cartGrid.AllowUserToAddRows = false;
            _cartGrid.SelectionMode = DataGridViewSelectionMode.FullRowSelect;

            _cartGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Producto", Width = 220 });
            _cartGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Kilos", Width = 120 });
            _cartGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Precio €/Kg", Width = 120 });
            _cartGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Subtotal", Width = 120 });

            var btnConfirm = new Button { Text = "Confirmar venta", Location = new Point(10, 300) };
            btnConfirm.Click += async (s, e) => await ConfirmSaleAsync();

            _lblTotal.Location = new Point(400, 304);

            Controls.AddRange(new Control[] { lblProd, _cmbProductos, lblKilos, _nudKilos, btnAdd, _cartGrid, btnConfirm, _lblTotal });
        }

        private async Task LoadProductosAsync()
        {
            try
            {
                var list = await _api.GetProductosAsync();
                _productos.Clear();
                _productos.AddRange(list);
                _cmbProductos.DataSource = new BindingSource(_productos, null);
                _cmbProductos.DisplayMember = "nombre";
                _cmbProductos.ValueMember = "id";
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error cargando productos: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void RefreshCart()
        {
            _cartGrid.Rows.Clear();
            double total = 0;
            foreach (var item in _cart)
            {
                var subtotal = item.kilos * item.producto.precioKg;
                _cartGrid.Rows.Add(item.producto.nombre, item.kilos, item.producto.precioKg, subtotal);
                total += subtotal;
            }
            _lblTotal.Text = $"Total: {total:0.00} €";
        }

        private async Task ConfirmSaleAsync()
        {
            if (_cart.Count == 0) { MessageBox.Show("El carrito está vacío."); return; }

            try
            {
                foreach (var item in _cart)
                {
                    var req = new VentaRequest { productoId = item.producto.id, kilos = item.kilos };
                    await _api.RegisterVentaAsync(req);
                }
                MessageBox.Show("Venta registrada correctamente.");
                _cart.Clear();
                RefreshCart();
                await LoadProductosAsync(); // refrescar stocks
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al confirmar venta: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}