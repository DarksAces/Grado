using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace App_Desktop
{
    public class ProductsForm : Form
    {
        private readonly ApiClient _api;
        private readonly BindingList<Producto> _productos = new();
        private readonly BindingSource _bs = new();
        private readonly DataGridView _grid = new();
        private readonly TextBox _txtSearch = new();
        private readonly ComboBox _cmbCategoria = new();

        public ProductsForm(ApiClient api)
        {
            _api = api ?? throw new ArgumentNullException(nameof(api));
            Text = "Catálogo de Productos";
            Size = new Size(800, 500);
            BuildUi();
            Load += async (s, e) => await LoadProductosAsync();
        }

        private void BuildUi()
        {
            var btnRefresh = new Button { Text = "Cargar/Refrescar", Location = new Point(10, 10) };
            btnRefresh.Click += async (s, e) => await LoadProductosAsync();

            var btnNuevo = new Button { Text = "Nuevo", Location = new Point(130, 10) };
            btnNuevo.Click += async (s, e) =>
            {
                using var f = new ProductEditForm();
                if (f.ShowDialog(this) == DialogResult.OK)
                {
                    await _api.CreateProductoAsync(f.Producto);
                    await LoadProductosAsync();
                }
            };

            var btnEditar = new Button { Text = "Editar", Location = new Point(200, 10) };
            btnEditar.Click += async (s, e) =>
            {
                if (_bs.Current is Producto p)
                {
                    using var f = new ProductEditForm(p);
                    if (f.ShowDialog(this) == DialogResult.OK)
                    {
                        await _api.UpdateProductoAsync(p.id, f.Producto);
                        await LoadProductosAsync();
                    }
                }
            };

            var btnEliminar = new Button { Text = "Eliminar", Location = new Point(270, 10) };
            btnEliminar.Click += async (s, e) =>
            {
                if (_bs.Current is Producto p && MessageBox.Show($"Eliminar {p.nombre}?", "Confirmar", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    await _api.DeleteProductoAsync(p.id);
                    await LoadProductosAsync();
                }
            };

            _txtSearch.Location = new Point(360, 12);
            _txtSearch.Width = 200;
            _txtSearch.TextChanged += (s, e) => ApplyFilters();

            _cmbCategoria.Location = new Point(570, 12);
            _cmbCategoria.Width = 120;
            _cmbCategoria.DropDownStyle = ComboBoxStyle.DropDownList;
            _cmbCategoria.SelectedIndexChanged += (s, e) => ApplyFilters();

            _grid.Location = new Point(10, 50);
            _grid.Size = new Size(760, 380);
            _grid.AutoGenerateColumns = false;
            _grid.AllowUserToAddRows = false;
            _grid.SelectionMode = DataGridViewSelectionMode.FullRowSelect;

            _grid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "ID", DataPropertyName = "id", Width = 50 });
            _grid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Nombre", DataPropertyName = "nombre", Width = 220 });
            _grid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Categoría", DataPropertyName = "categoria", Width = 120 });
            _grid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Precio €/Kg", DataPropertyName = "precioKg", Width = 120 });
            _grid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Stock Kg", DataPropertyName = "stockKg", Width = 120 });

            _bs.DataSource = _productos;
            _grid.DataSource = _bs;

            Controls.AddRange(new Control[] { btnRefresh, btnNuevo, btnEditar, btnEliminar, _txtSearch, _cmbCategoria, _grid });
        }

        private async Task LoadProductosAsync()
        {
            try
            {
                var list = await _api.GetProductosAsync();
                _productos.Clear();
                foreach (var p in list) _productos.Add(p);
                PopulateCategorias();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error cargando productos: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void PopulateCategorias()
        {
            var cats = _productos.Select(p => p.categoria).Distinct().OrderBy(x => x).ToList();
            cats.Insert(0, "(Todas)");
            _cmbCategoria.DataSource = cats;
        }

        private void ApplyFilters()
        {
            var search = _txtSearch.Text?.Trim().ToLowerInvariant();
            var cat = _cmbCategoria.SelectedItem as string;
            IEnumerable<Producto> q = _productos;
            if (!string.IsNullOrEmpty(search))
                q = q.Where(p => p.nombre.ToLowerInvariant().Contains(search) || p.categoria.ToLowerInvariant().Contains(search));
            if (!string.IsNullOrEmpty(cat) && cat != "(Todas)")
                q = q.Where(p => p.categoria == cat);
            _bs.DataSource = new BindingList<Producto>(q.ToList());
            _grid.DataSource = _bs;
        }
    }
}