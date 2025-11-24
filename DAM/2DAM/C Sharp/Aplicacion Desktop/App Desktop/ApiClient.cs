using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Json;
using System.Threading.Tasks;

namespace App_Desktop
{
    public class ApiClient
    {
        private readonly HttpClient _http;

        public ApiClient(string baseUrl)
        {
            if (string.IsNullOrWhiteSpace(baseUrl)) throw new ArgumentNullException(nameof(baseUrl));
            if (!baseUrl.EndsWith("/")) baseUrl += "/";
            _http = new HttpClient { BaseAddress = new Uri(baseUrl) };
        }

        public async Task<List<Producto>> GetProductosAsync()
        {
            try
            {
                return await _http.GetFromJsonAsync<List<Producto>>("productos") ?? new List<Producto>();
            }
            catch
            {
                return new List<Producto>();
            }
        }

        public async Task<Producto?> CreateProductoAsync(Producto p)
        {
            var resp = await _http.PostAsJsonAsync("productos", p);
            resp.EnsureSuccessStatusCode();
            return await resp.Content.ReadFromJsonAsync<Producto>();
        }

        public async Task<Producto?> UpdateProductoAsync(int id, Producto p)
        {
            var resp = await _http.PutAsJsonAsync($"productos/{id}", p);
            resp.EnsureSuccessStatusCode();
            return await resp.Content.ReadFromJsonAsync<Producto>();
        }

        public async Task<bool> DeleteProductoAsync(int id)
        {
            var resp = await _http.DeleteAsync($"productos/{id}");
            return resp.IsSuccessStatusCode;
        }

        public async Task<List<Venta>> GetVentasAsync()
        {
            try
            {
                return await _http.GetFromJsonAsync<List<Venta>>("ventas") ?? new List<Venta>();
            }
            catch
            {
                return new List<Venta>();
            }
        }

        public async Task<Venta?> RegisterVentaAsync(VentaRequest req)
        {
            var resp = await _http.PostAsJsonAsync("ventas", req);
            resp.EnsureSuccessStatusCode();
            return await resp.Content.ReadFromJsonAsync<Venta>();
        }
    }
}