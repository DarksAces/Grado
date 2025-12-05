using System;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;

namespace PanaderiaPOS
{
    public partial class FormStock : Form
    {
        ApiClient api = new ApiClient();
        DataGridView grid;
        CheckBox chk;

        public FormStock()
        {
            this.Text = "Control Stock"; this.Size = new Size(800, 500);
            chk = new CheckBox { Text = "Ver solo Stock Bajo (< 5)", Top = 20, Left = 20, AutoSize = true };
            chk.CheckedChanged += async (s, e) => await Cargar();
            this.Controls.Add(chk);

            grid = new DataGridView { Top = 50, Dock = DockStyle.Bottom, Height = 400, AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill };
            grid.CellFormatting += (s, e) => {
                if (grid.Columns[e.ColumnIndex].Name == "Stock" && int.Parse(e.Value.ToString()) < 5)
                    e.CellStyle.BackColor = Tema.Rojo;
            };
            this.Controls.Add(grid);
            this.Load += async (s, e) => await Cargar();
        }

        async System.Threading.Tasks.Task Cargar()
        {
            var lista = await api.GetProductos();
            if (chk.Checked) lista = lista.Where(x => x.Stock < 5).ToList();
            grid.DataSource = lista;
        }
    }
}