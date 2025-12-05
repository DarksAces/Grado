using System;
using System.Linq;
using System.Drawing;
using System.Windows.Forms;
using System.Collections.Generic;

namespace PanaderiaPOS
{
    public partial class FormCatalogo : Form
    {
        ApiClient api = new ApiClient();
        DataGridView grid;
        TextBox txtNom, txtCat, txtPre, txtStk;
        int idSel = 0;

        public FormCatalogo()
        {
            this.Size = new Size(900, 600);
            this.Text = "Gestión Catálogo";

            // Campos
            txtNom = new TextBox { Location = new Point(20, 20), Width = 150, PlaceholderText = "Nombre" };
            txtCat = new TextBox { Location = new Point(180, 20), Width = 100, PlaceholderText = "Categoría" };
            txtPre = new TextBox { Location = new Point(290, 20), Width = 80, PlaceholderText = "Precio" };
            txtStk = new TextBox { Location = new Point(380, 20), Width = 80, PlaceholderText = "Stock" };

            Button btnG = new Button { Text = "Guardar", Location = new Point(480, 18), BackColor = Tema.Naranja, ForeColor = Color.White, Width = 100 };
            btnG.Click += Guardar;

            Button btnB = new Button { Text = "Borrar", Location = new Point(590, 18), BackColor = Tema.Rojo, ForeColor = Color.White, Width = 100 };
            btnB.Click += Borrar;

            this.Controls.AddRange(new Control[] { txtNom, txtCat, txtPre, txtStk, btnG, btnB });

            // Grid
            grid = new DataGridView { Top = 60, Left = 20, Width = 840, Height = 480, AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill, ReadOnly = true, SelectionMode = DataGridViewSelectionMode.FullRowSelect };
            grid.SelectionChanged += (s, e) => {
                if (grid.SelectedRows.Count > 0)
                {
                    var r = grid.SelectedRows[0];
                    idSel = (int)r.Cells["Id"].Value;
                    txtNom.Text = r.Cells["Nombre"].Value.ToString();
                    txtCat.Text = r.Cells["Categoria"].Value.ToString();
                    txtPre.Text = r.Cells["Precio"].Value.ToString();
                    txtStk.Text = r.Cells["Stock"].Value.ToString();
                }
            };
            this.Controls.Add(grid);
            this.Load += async (s, e) => await Cargar();
        }

        async System.Threading.Tasks.Task Cargar()
        {
            grid.DataSource = await api.GetProductos();
            idSel = 0;
        }

        async void Guardar(object s, EventArgs e)
        {
            var p = new Producto { Id = idSel, Nombre = txtNom.Text, Categoria = txtCat.Text, Precio = float.Parse(txtPre.Text), Stock = int.Parse(txtStk.Text) };
            if (idSel == 0) await api.Crear(p); else await api.Editar(p);
            MessageBox.Show("Guardado"); await Cargar();
        }

        async void Borrar(object s, EventArgs e)
        {
            if (idSel != 0) { await api.Eliminar(idSel); MessageBox.Show("Borrado"); await Cargar(); }
        }
    }
}