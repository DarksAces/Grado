using System;
using System.Drawing;
using System.Globalization;
using System.Speech.Recognition;
using System.Windows.Forms;

namespace RA2_Voz_WinForms
{
    public partial class Form1 : Form
    {
        private SpeechRecognitionEngine _rec;
        private CultureInfo _culture;
        private Grammar _cmdGrammar;
        private DictationGrammar _dictationGrammar;
        private Grammar _stopGrammar;

        public Form1()
        {
            InitializeComponent();
            Shown += Form1_Shown;
            FormClosing += Form1_FormClosing;
            btnStart.Click += (_, __) => StartRecognition();
            btnStop.Click += (_, __) => StopRecognition();
        }

        private void Form1_Shown(object sender, EventArgs e)
        {
            try
            {
                // 1. Check for installed recognizers for Spanish if possible, otherwise default
                // Attempt to find a Spanish recognizer
                foreach (RecognizerInfo ri in SpeechRecognitionEngine.InstalledRecognizers())
                {
                    if (ri.Culture.TwoLetterISOLanguageName.Equals("es", StringComparison.InvariantCultureIgnoreCase))
                    {
                        _culture = ri.Culture;
                        break;
                    }
                }

                // If no Spanish recognizer, fallback to current or English
                if (_culture == null)
                {
                    _culture = CultureInfo.CurrentCulture;
                }

                _rec = new SpeechRecognitionEngine(_culture);

                // 2. Create grammar
                var commands = new Choices();
                commands.Add(new string[] {
                    "hola",
                    "limpiar",
                    "salir",
                    "color rojo",
                    "color verde",
                    "color azul",
                    "color amarillo",
                    "color negro",
                    "color blanco",
                    "abrir bloc de notas",
                    "abrir navegador",
                    "abrir calculadora",
                    "abrir paint",
                    "minimizar ventana",
                    "maximizar ventana",
                    "restaurar ventana",
                    "qué hora es",
                    "qué día es",
                    "abrir teams",
                    "buscar contacto",
                    "enviar mensaje",
                    "aceptar",
                    "borrar",
                    "tabulador",
                    "activar dictado",
                    "modo escritura",
                    "abrir explorador",
                    "abrir google",
                    "abrir youtube",
                    "color naranja",
                    "color violeta",
                    "color rosa",
                    "color gris",
                    "copiar",
                    "pegar",
                    "cortar",
                    "deshacer",
                    "seleccionar todo"
                });

                var gb = new GrammarBuilder();
                gb.Culture = _culture;
                gb.Append(commands);

                _cmdGrammar = new Grammar(gb);
                _cmdGrammar.Name = "Commands";

                // 3. Load Main grammar
                _rec.LoadGrammar(_cmdGrammar);

                // 4. Setup Dictation Grammar (Disabled by default)
                _dictationGrammar = new DictationGrammar();
                _dictationGrammar.Name = "Dictation";
                _dictationGrammar.Enabled = false;
                _rec.LoadGrammar(_dictationGrammar);

                // 5. Setup Stop Grammar (Disabled by default, enabled when dictating)
                var stopChoices = new Choices();
                stopChoices.Add(new string[] { "detener dictado", "modo comando" });
                var stopGb = new GrammarBuilder();
                stopGb.Culture = _culture;
                stopGb.Append(stopChoices);
                _stopGrammar = new Grammar(stopGb);
                _stopGrammar.Name = "StopDictation";
                _stopGrammar.Enabled = false;
                _rec.LoadGrammar(_stopGrammar);

                // 4. Configure input to default microphone
                _rec.SetInputToDefaultAudioDevice();

                // Events
                _rec.SpeechRecognized += Rec_SpeechRecognized;
                _rec.SpeechRecognitionRejected += Rec_SpeechRecognitionRejected;

                AppendLog($"Reconocedor inicializado. Cultura: {_culture.DisplayName}");
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al inicializar el reconocimiento de voz: {ex.Message}");
            }
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (_rec != null)
            {
                _rec.Dispose();
            }
        }

        private void StartRecognition()
        {
            try
            {
                // RecognizeAsync(RecognizeMode.Multiple) starts the continuous recognition
                _rec.RecognizeAsync(RecognizeMode.Multiple);
                btnStart.Enabled = false;
                btnStop.Enabled = true;
                AppendLog("Escuchando...");
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al iniciar: {ex.Message}");
            }
        }

        private void StopRecognition()
        {
            try
            {
                _rec.RecognizeAsyncStop();
                btnStart.Enabled = true;
                btnStop.Enabled = false;
                AppendLog("Reconocimiento detenido.");
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al parar: {ex.Message}");
            }
        }

        private void Rec_SpeechRecognized(object sender, SpeechRecognizedEventArgs e)
        {
            float confidenceThreshold = 0.55f;
            if (e.Result.Grammar.Name == "Dictation")
            {
                // Lower threshold for free speech usually needed, or just accept it
                confidenceThreshold = 0.0f;
            }

            if (e.Result.Confidence < confidenceThreshold)
            {
                AppendLog($"Reconocido (Baja confianza {e.Result.Confidence:0.00}): {e.Result.Text}");
                return;
            }

            string text = e.Result.Text;
            lblInfo.Text = $"Último: {text} ({e.Result.Confidence:0.00})";

            // Handle specific Grammars
            if (e.Result.Grammar.Name == "Dictation")
            {
                // In dictation mode, we type what we hear
                SendKeys.SendWait(text + " ");
                AppendLog($"Escribiendo: {text}");
                return;
            }
            else if (e.Result.Grammar.Name == "StopDictation")
            {
                // Switch back to command mode
                _dictationGrammar.Enabled = false;
                _stopGrammar.Enabled = false;
                _cmdGrammar.Enabled = true;
                AppendLog("MODO COMANDO ACTIVADO");
                return;
            }

            // Normal Commands
            AppendLog($"Comando reconocido: {text} (Confianza: {e.Result.Confidence:0.00})");

            switch (text.ToLower())
            {
                case "activar dictado":
                case "modo escritura":
                    _cmdGrammar.Enabled = false;
                    _dictationGrammar.Enabled = true;
                    _stopGrammar.Enabled = true;
                    AppendLog("MODO DICTADO ACTIVADO - Di 'Detener dictado' para salir.");
                    break;

                case "abrir teams":
                    try { System.Diagnostics.Process.Start("msteams:"); }
                    catch
                    {
                        // Fallback or generic error
                        try { System.Diagnostics.Process.Start("ms-teams:"); }
                        catch (Exception ex) { AppendLog($"No se pudo abrir Teams: {ex.Message}"); }
                    }
                    break;

                case "buscar contacto":
                    SendKeys.SendWait("^e"); // Ctrl + E for Search
                    break;

                case "enviar mensaje":
                case "aceptar":
                    SendKeys.SendWait("{ENTER}");
                    break;

                case "borrar":
                    SendKeys.SendWait("{BACKSPACE}");
                    break;

                case "tabulador":
                    SendKeys.SendWait("{TAB}");
                    break;

                case "hola":
                    MessageBox.Show("¡Hola! Saludos desde WinForms.");
                    break;

                case "limpiar":
                    txtLog.Clear();
                    break;

                case "salir":
                    Application.Exit();
                    break;

                case "color rojo":
                    pnlColor.BackColor = Color.Red;
                    break;

                case "color verde":
                    pnlColor.BackColor = Color.Green;
                    break;

                case "color azul":
                    pnlColor.BackColor = Color.Blue;
                    break;

                case "abrir bloc de notas":
                    try { System.Diagnostics.Process.Start("notepad.exe"); }
                    catch (Exception ex) { AppendLog($"Error abriendo notepad: {ex.Message}"); }
                    break;

                case "abrir navegador":
                    try { System.Diagnostics.Process.Start("https://es.wikipedia.org"); }
                    catch (Exception ex) { AppendLog($"Error abriendo navegador: {ex.Message}"); }
                    break;

                case "abrir calculadora":
                    try { System.Diagnostics.Process.Start("calc.exe"); }
                    catch (Exception ex) { AppendLog($"Error abriendo calculadora: {ex.Message}"); }
                    break;

                case "abrir paint":
                    try { System.Diagnostics.Process.Start("mspaint.exe"); }
                    catch (Exception ex) { AppendLog($"Error abriendo paint: {ex.Message}"); }
                    break;

                case "minimizar ventana":
                    this.WindowState = FormWindowState.Minimized;
                    break;

                case "maximizar ventana":
                    this.WindowState = FormWindowState.Maximized;
                    break;

                case "restaurar ventana":
                    this.WindowState = FormWindowState.Normal;
                    break;

                case "qué hora es":
                    string hora = DateTime.Now.ToShortTimeString();
                    AppendLog($"Hora: {hora}");
                    MessageBox.Show($"Son las {hora}");
                    break;

                case "qué día es":
                    string fecha = DateTime.Now.ToLongDateString();
                    AppendLog($"Fecha: {fecha}");
                    MessageBox.Show($"Hoy es {fecha}");
                    break;

                case "color amarillo":
                    pnlColor.BackColor = Color.Yellow;
                    break;

                case "color negro":
                    pnlColor.BackColor = Color.Black;
                    break;

                case "color blanco":
                    pnlColor.BackColor = Color.White;
                    break;

                case "abrir explorador":
                    try { System.Diagnostics.Process.Start("explorer.exe"); }
                    catch (Exception ex) { AppendLog($"Error abriendo explorador: {ex.Message}"); }
                    break;

                case "abrir google":
                    try { System.Diagnostics.Process.Start("https://www.google.com"); }
                    catch (Exception ex) { AppendLog($"Error abriendo Google: {ex.Message}"); }
                    break;

                case "abrir youtube":
                    try { System.Diagnostics.Process.Start("https://www.youtube.com"); }
                    catch (Exception ex) { AppendLog($"Error abriendo YouTube: {ex.Message}"); }
                    break;

                case "color naranja":
                    pnlColor.BackColor = Color.Orange;
                    break;

                case "color violeta":
                    pnlColor.BackColor = Color.Violet;
                    break;

                case "color rosa":
                    pnlColor.BackColor = Color.Pink;
                    break;

                case "color gris":
                    pnlColor.BackColor = Color.Gray;
                    break;

                case "copiar":
                    SendKeys.SendWait("^c");
                    break;

                case "pegar":
                    SendKeys.SendWait("^v");
                    break;

                case "cortar":
                    SendKeys.SendWait("^x");
                    break;

                case "deshacer":
                    SendKeys.SendWait("^z");
                    break;

                case "seleccionar todo":
                    SendKeys.SendWait("^a");
                    break;
            }
        }

        private void Rec_SpeechRecognitionRejected(object sender, SpeechRecognitionRejectedEventArgs e)
        {
            // Optional: handle rejected speech
            // AppendLog("No entendido / Ruido");
        }

        private void AppendLog(string message)
        {
            string logEntry = $"[{DateTime.Now.ToLongTimeString()}] {message}{Environment.NewLine}";
            txtLog.AppendText(logEntry);
        }
    }
}
