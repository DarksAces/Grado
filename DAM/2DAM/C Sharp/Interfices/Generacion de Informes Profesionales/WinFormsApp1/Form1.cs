using System.Data;
using Microsoft.Data.SqlClient;
using Microsoft.Reporting.WinForms;

namespace WinFormsApp1
{
    /// <summary>
    /// Ventana principal de la aplicación de generación de informes profesionales.
    /// </summary>
    public partial class Form1 : Form
    {
        // Configuración centralizada para fácil ajuste
        private const string ServerInstance = "(localdb)\\MSSQLLocalDB";
        private string connectionString = $"Server={ServerInstance};Database=InformeProDB;Trusted_Connection=True;TrustServerCertificate=True;";

        /// <summary>
        /// Inicializa una nueva instancia de la clase <see cref="Form1"/>.
        /// </summary>
        public Form1()
        {
            InitializeComponent();
            // Asegurar que el ReportViewer esté en modo local explícitamente
            this.reportViewer1.ProcessingMode = ProcessingMode.Local;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            CargarCategorias();
        }

        /// <summary>
        /// Inicializa la base de datos de los informes al cargar la pantalla principal utilizando un script SQL y carga el desplegable.
        /// </summary>
        private void CargarCategorias()
        {
            try
            {
                // 1. Asegurar que la base de datos existe conectando a 'master'
                string masterConn = $"Server={ServerInstance};Database=master;Trusted_Connection=True;TrustServerCertificate=True;";
                using (SqlConnection conn = new SqlConnection(masterConn))
                {
                    conn.Open();
                    string checkDb = "IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'InformeProDB') CREATE DATABASE InformeProDB";
                    using (SqlCommand cmd = new SqlCommand(checkDb, conn))
                    {
                        cmd.ExecuteNonQuery();
                    }
                }

                // 2. Conectar a la DB y asegurar que la tabla existe con datos de prueba
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    conn.Open();
                    string setupSchema = @"
                        IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Productos')
                        BEGIN
                            CREATE TABLE Productos (
                                Id INT PRIMARY KEY IDENTITY,
                                Nombre NVARCHAR(50),
                                Precio DECIMAL(10,2),
                                Categoria NVARCHAR(50),
                                Stock INT
                            );
                            INSERT INTO Productos (Nombre, Precio, Categoria, Stock) VALUES
                            ('Laptop Pro 15', 1200.00, 'Electrónica', 10),
                            ('Smartphone X', 850.50, 'Electrónica', 25),
                            ('Monitor 4K', 350.00, 'Electrónica', 15),
                            ('Silla Ergonómica', 250.00, 'Muebles', 12),
                            ('Cuaderno A4', 5.50, 'Papelerería', 100),
                            ('Teclado Mecánico', 80.00, 'Electrónica', 50),
                            ('Escritorio Pro', 300.00, 'Muebles', 5);
                        END";
                    
                    using (SqlCommand cmd = new SqlCommand(setupSchema, conn))
                    {
                        cmd.ExecuteNonQuery();
                    }

                    // 3. Cargar las categorías en el ComboBox
                    string query = "SELECT DISTINCT Categoria FROM Productos";
                    using (SqlDataAdapter da = new SqlDataAdapter(query, conn))
                    {
                        DataTable dt = new DataTable();
                        da.Fill(dt);

                        cmbCategoria.Items.Clear();
                        cmbCategoria.Items.Add("Todas");
                        foreach (DataRow row in dt.Rows)
                        {
                            cmbCategoria.Items.Add(row["Categoria"].ToString());
                        }
                        if (cmbCategoria.Items.Count > 0) cmbCategoria.SelectedIndex = 0;
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al inicializar la base de datos: " + ex.Message, "Error Crítico", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /// <summary>
        /// Genera el reporte en su estructura básica llamando a la configuración correspondiente.
        /// </summary>
        private void btnInformeBasico_Click(object sender, EventArgs e)
        {
            GenerarInforme("ReporteBasico.rdlc", false);
        }

        /// <summary>
        /// Crea una vista del reporte donde los datos se muestran agrupados de forma organizada.
        /// </summary>
        private void btnInformeAgrupado_Click(object sender, EventArgs e)
        {
            GenerarInforme("ReporteAgrupado.rdlc", false);
        }

        private void btnFiltrar_Click(object sender, EventArgs e)
        {
            // El filtrado normalmente se aplica sobre el informe básico solicitado
            GenerarInforme("ReporteBasico.rdlc", true);
        }

        /// <summary>
        /// Método principal que contiene la lógica para renderizar el informe solicitado.
        /// Comprueba las rutas, inyecta los datos de SQL y sincroniza con el ReportViewer.
        /// </summary>
        /// <param name="reportFileName">El nombre del archivo .rdlc que se va a procesar.</param>
        /// <param name="filtrar">Bandera que indica si se debe aplicar la capa de filtros por categoría.</param>
        private void GenerarInforme(string reportFileName, bool filtrar)
        {
            try
            {
                // Construcción robusta de la ruta del informe
                string reportPath = Path.Combine(Application.StartupPath, "Reports", reportFileName);

                if (!File.Exists(reportPath))
                {
                    MessageBox.Show($"No se encuentra el archivo de informe: {reportFileName}\n\nSe esperaba en: {reportPath}", 
                        "Error de Ruta", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }

                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    string query = "SELECT * FROM Productos";
                    string filtroLabel = "Todas";

                    if (filtrar && cmbCategoria.SelectedItem != null && cmbCategoria.SelectedItem.ToString() != "Todas")
                    {
                        query += " WHERE Categoria = @cat";
                        filtroLabel = cmbCategoria.SelectedItem.ToString();
                    }

                    SqlCommand cmd = new SqlCommand(query, conn);
                    if (query.Contains("@cat"))
                    {
                        cmd.Parameters.AddWithValue("@cat", filtroLabel);
                    }

                    SqlDataAdapter da = new SqlDataAdapter(cmd);
                    DataTable dt = new DataTable();
                    da.Fill(dt);

                    // Limpiar y cargar datos
                    reportViewer1.LocalReport.DataSources.Clear();
                    reportViewer1.LocalReport.ReportPath = reportPath;
                    reportViewer1.LocalReport.DataSources.Add(new ReportDataSource("DataSet1", dt));

                    // Intentar pasar el parámetro pCategoria si el informe lo admite
                    try
                    {
                        var reportParams = reportViewer1.LocalReport.GetParameters();
                        if (reportParams.Any(p => p.Name == "pCategoria"))
                        {
                            reportViewer1.LocalReport.SetParameters(new ReportParameter("pCategoria", filtroLabel));
                        }
                    }
                    catch { /* Silencioso si el informe no soporta parámetros */ }

                    reportViewer1.RefreshReport();
                }
            }
            catch (Exception ex)
            {
                string errorMsg = ex.Message;
                if (ex.InnerException != null) errorMsg += "\nDetalle: " + ex.InnerException.Message;
                MessageBox.Show("Error al procesar el informe:\n" + errorMsg, "Error de ReportViewer", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
