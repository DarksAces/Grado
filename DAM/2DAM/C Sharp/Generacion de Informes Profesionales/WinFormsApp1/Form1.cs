using System.Data;
using Microsoft.Data.SqlClient;
using Microsoft.Reporting.WinForms;

namespace WinFormsApp1
{
    public partial class Form1 : Form
    {
        // Connection string - adjust if necessary for your SQL instance
        private string connectionString = "Server=(localdb)\\MSSQLLocalDB;Database=InformeProDB;Trusted_Connection=True;TrustServerCertificate=True;";

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            CargarCategorias();
            this.reportViewer1.RefreshReport();
        }

        private void CargarCategorias()
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    string query = "SELECT DISTINCT Categoria FROM Productos";
                    SqlDataAdapter da = new SqlDataAdapter(query, conn);
                    DataTable dt = new DataTable();
                    da.Fill(dt);

                    cmbCategoria.Items.Clear();
                    cmbCategoria.Items.Add("Todas");
                    foreach (DataRow row in dt.Rows)
                    {
                        cmbCategoria.Items.Add(row["Categoria"].ToString());
                    }
                    cmbCategoria.SelectedIndex = 0;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al cargar categorías: " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void btnInformeBasico_Click(object sender, EventArgs e)
        {
            GenerarInforme("Reports/ReporteBasico.rdlc", false);
        }

        private void btnInformeAgrupado_Click(object sender, EventArgs e)
        {
            GenerarInforme("Reports/ReporteAgrupado.rdlc", false);
        }

        private void btnFiltrar_Click(object sender, EventArgs e)
        {
            GenerarInforme("Reports/ReporteBasico.rdlc", true);
        }

        private void GenerarInforme(string reportPath, bool filtrar)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    string query = "SELECT * FROM Productos";
                    if (filtrar && cmbCategoria.SelectedItem?.ToString() != "Todas")
                    {
                        query += " WHERE Categoria = @cat";
                    }

                    SqlCommand cmd = new SqlCommand(query, conn);
                    if (filtrar && cmbCategoria.SelectedItem?.ToString() != "Todas")
                    {
                        cmd.Parameters.AddWithValue("@cat", cmbCategoria.SelectedItem.ToString());
                    }

                    SqlDataAdapter da = new SqlDataAdapter(cmd);
                    DataTable dt = new DataTable();
                    da.Fill(dt);

                    reportViewer1.LocalReport.DataSources.Clear();
                    reportViewer1.LocalReport.ReportPath = reportPath;
                    reportViewer1.LocalReport.DataSources.Add(new ReportDataSource("DataSet1", dt));

                    // Add parameter if basic report
                    if (reportPath.Contains("ReporteBasico"))
                    {
                        string catParam = cmbCategoria.SelectedItem?.ToString() ?? "Todas";
                        reportViewer1.LocalReport.SetParameters(new ReportParameter("pCategoria", catParam));
                    }

                    reportViewer1.RefreshReport();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al generar informe: " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
