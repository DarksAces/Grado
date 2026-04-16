using System;
using System.Collections.Generic;
using System.Drawing;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace PanaderiaPOS
{
    // TEMA VISUAL
    public static class Tema
    {
        public static Color Naranja = Color.FromArgb(242, 139, 52);
        public static Color Marron = Color.FromArgb(93, 53, 24);
        public static Color Fondo = Color.FromArgb(248, 248, 248);
        public static Color Rojo = Color.FromArgb(231, 76, 60);
        public static Color Verde = Color.FromArgb(46, 204, 113);
    }

    // --- MODELOS DE DATOS (Arreglados para coincidir con tus Errores) ---
    public class Producto
    {
        public int Id { get; set; }
        public string Nombre { get; set; }
        public string Categoria { get; set; }
        public float Precio { get; set; }
        public int Stock { get; set; }
    }

    // Tu error pedía 'ItemVenta', así que le ponemos ese nombre
    public class ItemVenta
    {
        [JsonProperty("producto_id")]
        public int ProductoId { get; set; }

        [JsonProperty("cantidad")]
        public int Cantidad { get; set; }
    }

    public class Venta
    {
        // Tu error pedía 'Items' (Mayúscula)
        [JsonProperty("items")]
        public List<ItemVenta> Items { get; set; }
    }

    public class Informe
    {
        // Tu error pedía 'total' y 'cantidad'
        [JsonProperty("total")]
        public float Total { get; set; }

        [JsonProperty("cantidad")]
        public int Cantidad { get; set; }
    }

    // --- CLIENTE API ---
    public class ApiClient
    {
        HttpClient client = new HttpClient();
        string url = "http://127.0.0.1:8000";

        public async Task<List<Producto>> GetProductos()
        {
            try
            {
                var json = await client.GetStringAsync(url + "/productos");
                return JsonConvert.DeserializeObject<List<Producto>>(json);
            }
            catch { return new List<Producto>(); }
        }

        public async Task<bool> Crear(Producto p) => await Enviar(p, "/productos", "POST");
        public async Task<bool> Editar(Producto p) => await Enviar(p, "/productos/" + p.Id, "PUT");
        public async Task<bool> Eliminar(int id)
        {
            try { return (await client.DeleteAsync(url + "/productos/" + id)).IsSuccessStatusCode; }
            catch { return false; }
        }

        // Tu error pedía 'PostVenta', así que renombramos 'Vender' a 'PostVenta'
        public async Task<bool> PostVenta(Venta v) => await Enviar(v, "/ventas", "POST");

        public async Task<Informe> GetInforme()
        {
            try
            {
                var json = await client.GetStringAsync(url + "/informes");
                return JsonConvert.DeserializeObject<Informe>(json);
            }
            catch { return new Informe(); }
        }

        private async Task<bool> Enviar(object data, string ruta, string metodo)
        {
            try
            {
                var json = new StringContent(JsonConvert.SerializeObject(data), Encoding.UTF8, "application/json");
                var res = (metodo == "POST") ? await client.PostAsync(url + ruta, json) : await client.PutAsync(url + ruta, json);
                return res.IsSuccessStatusCode;
            }
            catch { return false; }
        }
    }
}