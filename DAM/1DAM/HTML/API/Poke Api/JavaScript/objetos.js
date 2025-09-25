const apiUrl = "https://pokeapi.co/api/v2/item";
let offset = 0;
const limit = 20;

// Cargar los objetos al iniciar la página
document.addEventListener('DOMContentLoaded', cargarObjetos);

async function cargarObjetos() {
    try {
        const contenedor = crearContenedor();
        const objetosGrid = crearGrid(contenedor);

        await cargarYMostrarObjetos(apiUrl, objetosGrid);

        const btn = crearBoton("Cargar más objetos", async () => {
            offset += limit;
            const nuevaUrl = `${apiUrl}?offset=${offset}&limit=${limit}`;
            await cargarYMostrarObjetos(nuevaUrl, objetosGrid);
        });

        contenedor.appendChild(btn);
    } catch (error) {
        console.error("Error:", error);
        alert("Error al cargar los objetos: " + error.message);
    }
}

function crearContenedor() {
    const contenedor = document.createElement("div");
    contenedor.className = "container";
    document.body.appendChild(contenedor);
    return contenedor;
}

function crearGrid(contenedor) {
    const grid = document.createElement("div");
    grid.id = "objetos-grid";
    Object.assign(grid.style, {
        display: "flex",
        flexWrap: "wrap",
        justifyContent: "center",
        gap: "20px"
    });
    contenedor.appendChild(grid);
    return grid;
}

function crearBoton(texto, onClick) {
    const btn = document.createElement("button");
    btn.textContent = texto;
    Object.assign(btn.style, {
        padding: "10px 20px",
        margin: "20px auto",
        display: "block",
        backgroundColor: "#007bff",
        color: "white",
        border: "none",
        cursor: "pointer"
    });
    btn.addEventListener("click", onClick);
    return btn;
}

async function cargarYMostrarObjetos(url, contenedor) {
    const response = await fetch(url);
    if (!response.ok) throw new Error("No se pudieron obtener los objetos");

    const data = await response.json();
    mostrarObjetos(data.results, contenedor);
}

async function mostrarObjetos(objetos, contenedor) {
    for (const obj of objetos) {
        try {
            const detalles = await (await fetch(obj.url)).json();

            const nombreES = detalles.names.find(n => n.language.name === "es")?.name || detalles.name;
            const descripcionES = detalles.flavor_text_entries.find(t => t.language.name === "es")?.text ||
                detalles.flavor_text_entries.find(t => t.language.name === "en")?.text ||
                "No hay descripción disponible";

            const esConsumible = detalles.attributes.some(attr => attr.name === "consumable");
            const usableEnBatalla = detalles.attributes.some(attr => attr.name === "usable-in-battle");

            const tarjeta = document.createElement("div");
            tarjeta.className = "objeto-card";
            Object.assign(tarjeta.style, {
                width: "200px",
                border: "1px solid #ddd",
                borderRadius: "8px",
                padding: "15px",
                textAlign: "center",
                backgroundColor: "#f9f9f9"
            });

            tarjeta.innerHTML = `
                <h3>${nombreES.toUpperCase()}</h3>
                <img src="${detalles.sprites.default}" alt="${nombreES}" style="width:80px; height:80px; margin: 10px auto;">
                <p style="height: 80px; overflow: auto;">${descripcionES}</p>
                <div style="margin-top: 10px;">
                    <p><strong>Consumible:</strong> ${esConsumible ? "Sí" : "No"}</p>
                    <p><strong>Usable en batalla:</strong> ${usableEnBatalla ? "Sí" : "No"}</p>
                </div>
            `;

            contenedor.appendChild(tarjeta);
        } catch (error) {
            console.error(`Error al obtener detalles del objeto ${obj.name}:`, error);
        }
    }
}
