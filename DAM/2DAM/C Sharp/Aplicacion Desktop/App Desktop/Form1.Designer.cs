namespace App_Desktop
{
    partial class Form1
    {
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.Button btnProducts;
        private System.Windows.Forms.Button btnStock;
        private System.Windows.Forms.Button btnSales;
    
        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.btnProducts = new System.Windows.Forms.Button();
            this.btnStock = new System.Windows.Forms.Button();
            this.btnSales = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // btnProducts
            // 
            this.btnProducts.Location = new System.Drawing.Point(40, 20);
            this.btnProducts.Name = "btnProducts";
            this.btnProducts.Size = new System.Drawing.Size(340, 30);
            this.btnProducts.TabIndex = 0;
            this.btnProducts.Text = "Catálogo de Productos";
            this.btnProducts.UseVisualStyleBackColor = true;
            this.btnProducts.Click += new System.EventHandler(this.BtnProducts_Click);
            // 
            // btnStock
            // 
            this.btnStock.Location = new System.Drawing.Point(40, 60);
            this.btnStock.Name = "btnStock";
            this.btnStock.Size = new System.Drawing.Size(340, 30);
            this.btnStock.TabIndex = 1;
            this.btnStock.Text = "Control de Stock";
            this.btnStock.UseVisualStyleBackColor = true;
            this.btnStock.Click += new System.EventHandler(this.BtnStock_Click);
            // 
            // btnSales
            // 
            this.btnSales.Location = new System.Drawing.Point(40, 100);
            this.btnSales.Name = "btnSales";
            this.btnSales.Size = new System.Drawing.Size(340, 30);
            this.btnSales.TabIndex = 2;
            this.btnSales.Text = "Registro de Ventas";
            this.btnSales.UseVisualStyleBackColor = true;
            this.btnSales.Click += new System.EventHandler(this.BtnSales_Click);
            // 
            // Form1
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(420, 180);
            this.Controls.Add(this.btnProducts);
            this.Controls.Add(this.btnStock);
            this.Controls.Add(this.btnSales);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.Name = "Form1";
            this.Text = "Frutería - Menú Principal";
            this.ResumeLayout(false);
        }

        #endregion
    }
}
