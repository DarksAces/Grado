namespace WinFormsApp1
{
    partial class Form1
    {
        private System.ComponentModel.IContainer components = null;
        private Microsoft.Reporting.WinForms.ReportViewer reportViewer1;
        private System.Windows.Forms.Panel panelTop;
        private System.Windows.Forms.Button btnInformeBasico;
        private System.Windows.Forms.Button btnInformeAgrupado;
        private System.Windows.Forms.ComboBox cmbCategoria;
        private System.Windows.Forms.Label lblCategoria;
        private System.Windows.Forms.Button btnFiltrar;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        private void InitializeComponent()
        {
            this.reportViewer1 = new Microsoft.Reporting.WinForms.ReportViewer();
            this.panelTop = new System.Windows.Forms.Panel();
            this.btnInformeBasico = new System.Windows.Forms.Button();
            this.btnInformeAgrupado = new System.Windows.Forms.Button();
            this.cmbCategoria = new System.Windows.Forms.ComboBox();
            this.lblCategoria = new System.Windows.Forms.Label();
            this.btnFiltrar = new System.Windows.Forms.Button();

            this.panelTop.SuspendLayout();
            this.SuspendLayout();

            // reportViewer1
            this.reportViewer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.reportViewer1.Location = new System.Drawing.Point(0, 50);
            this.reportViewer1.Name = "reportViewer1";
            this.reportViewer1.ServerReport.BearerToken = null;
            this.reportViewer1.Size = new System.Drawing.Size(800, 400);
            this.reportViewer1.TabIndex = 0;

            // panelTop
            this.panelTop.Controls.Add(this.btnFiltrar);
            this.panelTop.Controls.Add(this.cmbCategoria);
            this.panelTop.Controls.Add(this.lblCategoria);
            this.panelTop.Controls.Add(this.btnInformeAgrupado);
            this.panelTop.Controls.Add(this.btnInformeBasico);
            this.panelTop.Dock = System.Windows.Forms.DockStyle.Top;
            this.panelTop.Height = 50;
            this.panelTop.Location = new System.Drawing.Point(0, 0);
            this.panelTop.Name = "panelTop";
            this.panelTop.Size = new System.Drawing.Size(800, 50);

            // btnInformeBasico
            this.btnInformeBasico.Location = new System.Drawing.Point(12, 12);
            this.btnInformeBasico.Name = "btnInformeBasico";
            this.btnInformeBasico.Size = new System.Drawing.Size(120, 30);
            this.btnInformeBasico.TabIndex = 0;
            this.btnInformeBasico.Text = "Informe Básico";
            this.btnInformeBasico.UseVisualStyleBackColor = true;
            this.btnInformeBasico.Click += new System.EventHandler(this.btnInformeBasico_Click);

            // btnInformeAgrupado
            this.btnInformeAgrupado.Location = new System.Drawing.Point(138, 12);
            this.btnInformeAgrupado.Name = "btnInformeAgrupado";
            this.btnInformeAgrupado.Size = new System.Drawing.Size(120, 30);
            this.btnInformeAgrupado.TabIndex = 1;
            this.btnInformeAgrupado.Text = "Informe Agrupado";
            this.btnInformeAgrupado.UseVisualStyleBackColor = true;
            this.btnInformeAgrupado.Click += new System.EventHandler(this.btnInformeAgrupado_Click);

            // lblCategoria
            this.lblCategoria.AutoSize = true;
            this.lblCategoria.Location = new System.Drawing.Point(280, 17);
            this.lblCategoria.Name = "lblCategoria";
            this.lblCategoria.Size = new System.Drawing.Size(61, 15);
            this.lblCategoria.TabIndex = 2;
            this.lblCategoria.Text = "Categoría:";

            // cmbCategoria
            this.cmbCategoria.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbCategoria.FormattingEnabled = true;
            this.cmbCategoria.Location = new System.Drawing.Point(350, 14);
            this.cmbCategoria.Name = "cmbCategoria";
            this.cmbCategoria.Size = new System.Drawing.Size(150, 23);
            this.cmbCategoria.TabIndex = 3;

            // btnFiltrar
            this.btnFiltrar.Location = new System.Drawing.Point(510, 12);
            this.btnFiltrar.Name = "btnFiltrar";
            this.btnFiltrar.Size = new System.Drawing.Size(80, 30);
            this.btnFiltrar.TabIndex = 4;
            this.btnFiltrar.Text = "Filtrar";
            this.btnFiltrar.UseVisualStyleBackColor = true;
            this.btnFiltrar.Click += new System.EventHandler(this.btnFiltrar_Click);

            // Form1
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.reportViewer1);
            this.Controls.Add(this.panelTop);
            this.Name = "Form1";
            this.Text = "Gestión de Informes de Productos";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.panelTop.ResumeLayout(false);
            this.panelTop.PerformLayout();
            this.ResumeLayout(false);
        }
    }
}
