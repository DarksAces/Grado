namespace WinFormsApp1
{
    partial class Form1
    {
        /// <summary>
        ///  Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Limpiar recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados deben desecharse; false en caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        ///  Método necesario para admitir el Diseñador. No se puede modificar
        ///  el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            this.lblPrice = new System.Windows.Forms.Label();
            this.txtPrice = new System.Windows.Forms.TextBox();
            this.lblQuantity = new System.Windows.Forms.Label();
            this.txtQuantity = new System.Windows.Forms.TextBox();
            this.grpIva = new System.Windows.Forms.GroupBox();
            this.rbIva21 = new System.Windows.Forms.RadioButton();
            this.rbIva10 = new System.Windows.Forms.RadioButton();
            this.rbIva4 = new System.Windows.Forms.RadioButton();
            this.rbIva0 = new System.Windows.Forms.RadioButton();
            this.grpDiscount = new System.Windows.Forms.GroupBox();
            this.rbD20 = new System.Windows.Forms.RadioButton();
            this.rbD10 = new System.Windows.Forms.RadioButton();
            this.rbD5 = new System.Windows.Forms.RadioButton();
            this.rbD0 = new System.Windows.Forms.RadioButton();
            this.chkPartner = new System.Windows.Forms.CheckBox();
            this.btnCalculate = new System.Windows.Forms.Button();
            this.lblNoIva = new System.Windows.Forms.Label();
            this.txtNoIva = new System.Windows.Forms.TextBox();
            this.lblWithIva = new System.Windows.Forms.Label();
            this.txtWithIva = new System.Windows.Forms.TextBox();
            this.grpIva.SuspendLayout();
            this.grpDiscount.SuspendLayout();
            this.SuspendLayout();
            // 
            // lblPrice
            // 
            this.lblPrice.AutoSize = true;
            this.lblPrice.Location = new System.Drawing.Point(30, 30);
            this.lblPrice.Name = "lblPrice";
            this.lblPrice.Size = new System.Drawing.Size(43, 15);
            this.lblPrice.TabIndex = 0;
            this.lblPrice.Text = "Price €:";
            // 
            // txtPrice
            // 
            this.txtPrice.Location = new System.Drawing.Point(100, 27);
            this.txtPrice.Name = "txtPrice";
            this.txtPrice.Size = new System.Drawing.Size(100, 23);
            this.txtPrice.TabIndex = 1;
            // 
            // lblQuantity
            // 
            this.lblQuantity.AutoSize = true;
            this.lblQuantity.Location = new System.Drawing.Point(30, 70);
            this.lblQuantity.Name = "lblQuantity";
            this.lblQuantity.Size = new System.Drawing.Size(56, 15);
            this.lblQuantity.TabIndex = 2;
            this.lblQuantity.Text = "Quantity:";
            // 
            // txtQuantity
            // 
            this.txtQuantity.Location = new System.Drawing.Point(100, 67);
            this.txtQuantity.Name = "txtQuantity";
            this.txtQuantity.Size = new System.Drawing.Size(100, 23);
            this.txtQuantity.TabIndex = 3;
            // 
            // grpIva
            // 
            this.grpIva.Controls.Add(this.rbIva21);
            this.grpIva.Controls.Add(this.rbIva10);
            this.grpIva.Controls.Add(this.rbIva4);
            this.grpIva.Controls.Add(this.rbIva0);
            this.grpIva.Location = new System.Drawing.Point(250, 20);
            this.grpIva.Name = "grpIva";
            this.grpIva.Size = new System.Drawing.Size(200, 90);
            this.grpIva.TabIndex = 4;
            this.grpIva.TabStop = false;
            this.grpIva.Text = "IVA";
            // 
            // rbIva21
            // 
            this.rbIva21.AutoSize = true;
            this.rbIva21.Location = new System.Drawing.Point(110, 55);
            this.rbIva21.Name = "rbIva21";
            this.rbIva21.Size = new System.Drawing.Size(51, 19);
            this.rbIva21.TabIndex = 3;
            this.rbIva21.Text = "21 %";
            this.rbIva21.UseVisualStyleBackColor = true;
            // 
            // rbIva10
            // 
            this.rbIva10.AutoSize = true;
            this.rbIva10.Location = new System.Drawing.Point(110, 25);
            this.rbIva10.Name = "rbIva10";
            this.rbIva10.Size = new System.Drawing.Size(51, 19);
            this.rbIva10.TabIndex = 2;
            this.rbIva10.Text = "10 %";
            this.rbIva10.UseVisualStyleBackColor = true;
            // 
            // rbIva4
            // 
            this.rbIva4.AutoSize = true;
            this.rbIva4.Location = new System.Drawing.Point(20, 55);
            this.rbIva4.Name = "rbIva4";
            this.rbIva4.Size = new System.Drawing.Size(45, 19);
            this.rbIva4.TabIndex = 1;
            this.rbIva4.Text = "4 %";
            this.rbIva4.UseVisualStyleBackColor = true;
            // 
            // rbIva0
            // 
            this.rbIva0.AutoSize = true;
            this.rbIva0.Checked = true;
            this.rbIva0.Location = new System.Drawing.Point(20, 25);
            this.rbIva0.Name = "rbIva0";
            this.rbIva0.Size = new System.Drawing.Size(45, 19);
            this.rbIva0.TabIndex = 0;
            this.rbIva0.TabStop = true;
            this.rbIva0.Text = "0 %";
            this.rbIva0.UseVisualStyleBackColor = true;
            // 
            // grpDiscount
            // 
            this.grpDiscount.Controls.Add(this.rbD20);
            this.grpDiscount.Controls.Add(this.rbD10);
            this.grpDiscount.Controls.Add(this.rbD5);
            this.grpDiscount.Controls.Add(this.rbD0);
            this.grpDiscount.Location = new System.Drawing.Point(250, 120);
            this.grpDiscount.Name = "grpDiscount";
            this.grpDiscount.Size = new System.Drawing.Size(200, 90);
            this.grpDiscount.TabIndex = 5;
            this.grpDiscount.TabStop = false;
            this.grpDiscount.Text = "Discount";
            // 
            // rbD20
            // 
            this.rbD20.AutoSize = true;
            this.rbD20.Location = new System.Drawing.Point(110, 55);
            this.rbD20.Name = "rbD20";
            this.rbD20.Size = new System.Drawing.Size(54, 19);
            this.rbD20.TabIndex = 3;
            this.rbD20.Text = "20 %";
            this.rbD20.UseVisualStyleBackColor = true;
            // 
            // rbD10
            // 
            this.rbD10.AutoSize = true;
            this.rbD10.Location = new System.Drawing.Point(110, 25);
            this.rbD10.Name = "rbD10";
            this.rbD10.Size = new System.Drawing.Size(54, 19);
            this.rbD10.TabIndex = 2;
            this.rbD10.Text = "10 %";
            this.rbD10.UseVisualStyleBackColor = true;
            // 
            // rbD5
            // 
            this.rbD5.AutoSize = true;
            this.rbD5.Location = new System.Drawing.Point(20, 55);
            this.rbD5.Name = "rbD5";
            this.rbD5.Size = new System.Drawing.Size(48, 19);
            this.rbD5.TabIndex = 1;
            this.rbD5.Text = "5 %";
            this.rbD5.UseVisualStyleBackColor = true;
            // 
            // rbD0
            // 
            this.rbD0.AutoSize = true;
            this.rbD0.Checked = true;
            this.rbD0.Location = new System.Drawing.Point(20, 25);
            this.rbD0.Name = "rbD0";
            this.rbD0.Size = new System.Drawing.Size(48, 19);
            this.rbD0.TabIndex = 0;
            this.rbD0.TabStop = true;
            this.rbD0.Text = "0 %";
            this.rbD0.UseVisualStyleBackColor = true;
            // 
            // chkPartner
            // 
            this.chkPartner.AutoSize = true;
            this.chkPartner.Location = new System.Drawing.Point(30, 120);
            this.chkPartner.Name = "chkPartner";
            this.chkPartner.Size = new System.Drawing.Size(102, 19);
            this.chkPartner.TabIndex = 6;
            this.chkPartner.Text = "Partner (30 %)";
            this.chkPartner.UseVisualStyleBackColor = true;
            this.chkPartner.CheckedChanged += new System.EventHandler(this.chkPartner_CheckedChanged);
            // 
            // btnCalculate
            // 
            this.btnCalculate.Location = new System.Drawing.Point(30, 160);
            this.btnCalculate.Name = "btnCalculate";
            this.btnCalculate.Size = new System.Drawing.Size(170, 30);
            this.btnCalculate.TabIndex = 7;
            this.btnCalculate.Text = "Calculate";
            this.btnCalculate.UseVisualStyleBackColor = true;
            this.btnCalculate.Click += new System.EventHandler(this.btnCalculate_Click);
            // 
            // lblNoIva
            // 
            this.lblNoIva.AutoSize = true;
            this.lblNoIva.Location = new System.Drawing.Point(30, 220);
            this.lblNoIva.Name = "lblNoIva";
            this.lblNoIva.Size = new System.Drawing.Size(57, 15);
            this.lblNoIva.TabIndex = 8;
            this.lblNoIva.Text = "No IVA (€)";
            // 
            // txtNoIva
            // 
            this.txtNoIva.Location = new System.Drawing.Point(100, 217);
            this.txtNoIva.Name = "txtNoIva";
            this.txtNoIva.ReadOnly = true;
            this.txtNoIva.Size = new System.Drawing.Size(100, 23);
            this.txtNoIva.TabIndex = 9;
            // 
            // lblWithIva
            // 
            this.lblWithIva.AutoSize = true;
            this.lblWithIva.Location = new System.Drawing.Point(30, 260);
            this.lblWithIva.Name = "lblWithIva";
            this.lblWithIva.Size = new System.Drawing.Size(58, 15);
            this.lblWithIva.TabIndex = 10;
            this.lblWithIva.Text = "With IVA €";
            // 
            // txtWithIva
            // 
            this.txtWithIva.Location = new System.Drawing.Point(100, 257);
            this.txtWithIva.Name = "txtWithIva";
            this.txtWithIva.ReadOnly = true;
            this.txtWithIva.Size = new System.Drawing.Size(100, 23);
            this.txtWithIva.TabIndex = 11;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(480, 320);
            this.Controls.Add(this.txtWithIva);
            this.Controls.Add(this.lblWithIva);
            this.Controls.Add(this.txtNoIva);
            this.Controls.Add(this.lblNoIva);
            this.Controls.Add(this.btnCalculate);
            this.Controls.Add(this.chkPartner);
            this.Controls.Add(this.grpDiscount);
            this.Controls.Add(this.grpIva);
            this.Controls.Add(this.txtQuantity);
            this.Controls.Add(this.lblQuantity);
            this.Controls.Add(this.txtPrice);
            this.Controls.Add(this.lblPrice);
            this.Name = "Form1";
            this.Text = "IVA Calculator";
            this.grpIva.ResumeLayout(false);
            this.grpIva.PerformLayout();
            this.grpDiscount.ResumeLayout(false);
            this.grpDiscount.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblPrice;
        private System.Windows.Forms.TextBox txtPrice;
        private System.Windows.Forms.Label lblQuantity;
        private System.Windows.Forms.TextBox txtQuantity;
        private System.Windows.Forms.GroupBox grpIva;
        private System.Windows.Forms.RadioButton rbIva21;
        private System.Windows.Forms.RadioButton rbIva10;
        private System.Windows.Forms.RadioButton rbIva4;
        private System.Windows.Forms.RadioButton rbIva0;
        private System.Windows.Forms.GroupBox grpDiscount;
        private System.Windows.Forms.RadioButton rbD20;
        private System.Windows.Forms.RadioButton rbD10;
        private System.Windows.Forms.RadioButton rbD5;
        private System.Windows.Forms.RadioButton rbD0;
        private System.Windows.Forms.CheckBox chkPartner;
        private System.Windows.Forms.Button btnCalculate;
        private System.Windows.Forms.Label lblNoIva;
        private System.Windows.Forms.TextBox txtNoIva;
        private System.Windows.Forms.Label lblWithIva;
        private System.Windows.Forms.TextBox txtWithIva;
    }
}
