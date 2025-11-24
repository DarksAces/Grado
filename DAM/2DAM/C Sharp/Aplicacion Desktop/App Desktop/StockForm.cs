using System;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace App_Desktop
{
    public class StockForm : Form
    {
        private readonly ApiClient _api;
        private readonly BindingList<Producto> _productos = new();
        private readonly DataGridView _grid = new();
        private readonly CheckBox _chkLow = new();

        public StockForm(ApiClient api)
        {
            _api = api ?? throw new ArgumentNullException(nameof(api));
            Text = "Control de Stock";
            Size = new Size(700, 450);
            BuildUi();
            Load += async (s, e) => await LoadProductosAsync();
        }

        private void BuildUi()
        {
            var btnRefresh = new Button { Text = "Refrescar", Location = new Point(10, 10) };
            btnRefresh.Click += async (s, e) => await LoadProductosAsync();

            _chkLow.Text = "Mostrar solo stock bajo (< 5 kg)";
            _chkLow.Location = new Point(100, 12);
            _chkLow.CheckedChanged += (s, e) => ApplyFilter();

            _grid.Location = new Point(10, 40);
            _grid.Size = new Size(660, 360);
            _grid.AutoGenerateColumns = false;
            _grid.AllowUserToAddRows = false;
            _grid.SelectionMode = DataGridViewSelectionMode.FullRowSelect;

            _grid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "ID", DataPropertyName = "id", Width = 50 });
            _grid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Nombre", DataPropertyName = "nombre", Width = 240 });
            _grid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Categoría", DataPropertyName = "categoria", Width = 120 });
            _grid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Stock Kg", DataPropertyName = "stockKg", Width = 120 });

            _grid.CellFormatting += (s, e) =>
            {
                if (e.ColumnIndex == 3 && e.Value is double stock)
                {
                    if (stock <= 2) e.CellStyle.BackColor = Color.Red;
                    else if (stock <= 5) e.CellStyle.BackColor = Color.Orange;
                }
            };

            Controls.AddRange(new Control[] { btnRefresh, _chkLow, _grid });
        }

        private async Task LoadProductosAsync()
        {
            try
            {
                var list = await _api.GetProductosAsync();
                _productos.Clear();
                foreach (var p in list) _productos.Add(p);
                ApplyFilter();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error cargando stock: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void ApplyFilter()
        {
            if (_chkLow.Checked)
                _grid.DataSource = new BindingList<Producto>(_productos.Where(p => p.stockKg < 5).ToList());
            else
                _grid.DataSource = _productos;
        }
    }
}