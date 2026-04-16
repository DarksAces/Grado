using System;
using System.Globalization;
using System.Windows.Forms;

namespace WinFormsApp1
{
    /// <summary>
    /// Ventana principal de la calculadora de precios.
    /// Contiene la lógica para calcular precios finales aplicando descuentos e impuestos.
    /// </summary>
    public partial class Form1 : Form
    {
        /// <summary>
        /// Inicializa una nueva instancia de la clase <see cref="Form1"/>.
        /// </summary>
        public Form1()
        {
            InitializeComponent();
        }

        // --- Método de validación ---
        /// <summary>
        /// Valida y convierte una entrada de texto a un valor de tipo decimal.
        /// Lanza excepciones si la entrada está vacía, no tiene el formato correcto o es un valor negativo.
        /// </summary>
        /// <param name="input">El texto introducido por el usuario a convertir.</param>
        /// <param name="fieldName">El nombre del campo, utilizado para generar mensajes de error descriptivos.</param>
        /// <returns>El valor numérico validado de tipo <c>decimal</c>.</returns>
        private decimal ParseDecimal(string input, string fieldName)
        {
            if (string.IsNullOrWhiteSpace(input))
                throw new ArgumentException($"{fieldName} no puede estar vacío.");

            if (!decimal.TryParse(input, NumberStyles.Number, CultureInfo.InvariantCulture, out decimal value))
                throw new ArgumentException($"{fieldName} tiene un formato no válido.");

            if (value <= 0)
                throw new ArgumentException($"{fieldName} debe ser mayor que cero.");

            return value;
        }

        // --- Botón Calcular ---
        /// <summary>
        /// Evento que se ejecuta al hacer clic en el botón Calcular.
        /// Obtiene el precio y la cantidad, aplica el descuento correspondiente y añade el IVA,
        /// mostrando el subtotal libre de impuestos y el total final formateados a moneda española.
        /// </summary>
        /// <param name="sender">El origen del evento, típicamente el botón de calcular.</param>
        /// <param name="e">Argumentos del evento.</param>
        private void btnCalculate_Click(object sender, EventArgs e)
        {
            try
            {
                // 1️⃣ Obtener datos base
                decimal price = ParseDecimal(txtPrice.Text, "Precio");
                decimal quantity = ParseDecimal(txtQuantity.Text, "Cantidad");
                if (quantity < 1)
                    throw new ArgumentException("La cantidad debe ser al menos 1.");

                decimal subtotal = price * quantity;

                // 2️⃣ Calcular descuento
                decimal discount = 0m;
                if (chkPartner.Checked)
                {
                    discount = 0.30m; // socio 30 %
                }
                else
                {
                    if (rbD5.Checked) discount = 0.05m;
                    else if (rbD10.Checked) discount = 0.10m;
                    else if (rbD20.Checked) discount = 0.20m;
                    // rbD0 → 0%
                }

                decimal afterDiscount = subtotal * (1 - discount);

                // 3️⃣ Calcular IVA
                decimal iva = 0m;
                if (rbIva4.Checked) iva = 0.04m;
                else if (rbIva10.Checked) iva = 0.10m;
                else if (rbIva21.Checked) iva = 0.21m;
                // rbIva0 → 0%

                decimal totalWithIva = afterDiscount * (1 + iva);

                // 4️⃣ Mostrar resultados formateados (cultura española)
                var es = new CultureInfo("es-ES");
                txtNoIva.Text = afterDiscount.ToString("C2", es);
                txtWithIva.Text = totalWithIva.ToString("C2", es);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        // --- CheckBox Socio ---
        /// <summary>
        /// Evento que se activa al cambiar el estado de la casilla de verificación de "Socio".
        /// Si está marcada, desactiva el grupo de descuentos adicionales, 
        /// ya que un socio tiene un descuento fijo y exclusivo (30%).
        /// </summary>
        /// <param name="sender">El origen del evento, típicamente el CheckBox de socio.</param>
        /// <param name="e">Argumentos del evento.</param>
        private void chkPartner_CheckedChanged(object sender, EventArgs e)
        {
            // Desactivar descuentos si es socio
            grpDiscount.Enabled = !chkPartner.Checked;
        }
    }
}
