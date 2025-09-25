const apiUrl = "https://pokeapi.co/api/v2/";
let paginaActual = 1;

document.addEventListener('DOMContentLoaded', () => {
    const path = window.location.pathname;
    if (path.includes('index.html') || path.endsWith('/')) cargarPaginaInicio();
    else if (path.includes('habitad.html')) getHabitats();
    else if (path.includes('objetos.html')) obtenerObjetos?.();
    else if (path.includes('Buscador.html')) buscarPokemon('1');
});

const corregirNombre = (nombre) => {
    const especiales = {
        deoxys: "deoxys-normal",
        wormadam: "wormadam-plant",
        shaymin: "shaymin-land"
    };
    return especiales[nombre] || nombre;
};

async function cargarPaginaInicio() {
    const tabla = document.getElementById('pokemon-table-body');
    if (!tabla) return;

    const inicio = (paginaActual - 1) * 30 + 1;
    const fin = paginaActual * 30;

    for (let i = inicio; i <= fin; i++) {
        try {
            const res = await fetch(`${apiUrl}pokemon/${i}`);
            const poke = await res.json();

            const nombreCorregido = corregirNombre(poke.name);
            const finalRes = await fetch(`${apiUrl}pokemon/${nombreCorregido}`);
            const finalPoke = await finalRes.json();

            const tipos = poke.types.map(t => t.type.name).join(', ');

            const speciesRes = await fetch(poke.species.url);
            const speciesData = await speciesRes.json();
            const juegos = speciesData.game_indices?.map(g => g.version.name).join(', ') || 'No disponible';

            const evoluciones = await obtenerCadenaEvolutiva(poke.id);

            const fila = document.createElement('tr');
            fila.innerHTML = `
                <td>${poke.name.toUpperCase()}</td>
                <td>#${poke.id}</td>
                <td>${tipos}</td>
                <td><img src="${poke.sprites.other["official-artwork"].front_default || poke.sprites.front_default}" alt="${poke.name}"></td>
                <td><div class="cadena-evolutiva">
                    ${evoluciones.map((e, i) => `
                        ${i > 0 ? '<span class="flecha">➡️</span>' : ''}
                        <img src="${e.imagen}" alt="${e.nombre}">`
            ).join('')}
                </div></td>
            `;
            tabla.appendChild(fila);
        } catch (err) {
            console.error(`Error al cargar Pokémon ${i}:`, err);
        }
    }

    document.getElementById('verMasBtn')?.remove();

    const btn = document.createElement('button');
    btn.id = 'verMasBtn';
    btn.textContent = 'Ver más';
    Object.assign(btn.style, {
        display: 'block', margin: '20px auto', padding: '10px 20px',
        backgroundColor: '#007bff', color: 'white', border: 'none',
        cursor: 'pointer', borderRadius: '5px'
    });
    btn.onclick = () => { paginaActual++; cargarPaginaInicio(); };
    document.querySelector('.container').appendChild(btn);
}

async function obtenerCadenaEvolutiva(id) {
    try {
        const speciesRes = await fetch(`${apiUrl}pokemon-species/${id}`);
        const speciesData = await speciesRes.json();
        const evoRes = await fetch(speciesData.evolution_chain.url);
        const evoData = await evoRes.json();

        const resultado = [];
        const imagenes = new Set();

        const procesar = async (data, nombre = null, nivel = 0) => {
            const especie = data.species.name;
            const res = await fetch(`${apiUrl}pokemon/${especie}`);
            const poke = await res.json();
            const imagen = poke.sprites.front_default;

            if (!nombre || especie === nombre) {
                if (!imagenes.has(imagen)) {
                    resultado.push({ nombre: especie, imagen });
                    imagenes.add(imagen);
                }

                if (nivel === 1 && data.evolves_to[0]) {
                    const next = data.evolves_to[0];
                    const nextRes = await fetch(`${apiUrl}pokemon/${next.species.name}`);
                    resultado.push({
                        nombre: next.species.name,
                        imagen: (await nextRes.json()).sprites.front_default
                    });
                }
                return;
            }

            for (const sub of data.evolves_to) await procesar(sub, nombre, nivel + 1);
        };

        const currentRes = await fetch(`${apiUrl}pokemon/${id}`);
        const nombreActual = (await currentRes.json()).name;

        const cadena = evoData.chain;
        await procesar(cadena, nombreActual === cadena.species.name ? null : nombreActual);
        return resultado;
    } catch (err) {
        console.error("Error en cadena evolutiva:", err);
        return [];
    }
}

window.addEventListener('load', () => {
    if (window.location.pathname.includes('Buscador.html')) buscarPokemon('1');
});

function buscarPokemon(input = document.getElementById("pokemonInput")?.value?.toLowerCase()) {
    if (!input) return;
    fetch(`${apiUrl}pokemon/${input}`)
        .then(res => {
            if (!res.ok) throw new Error("Pokémon no encontrado");
            return res.json();
        })
        .then(data => {
            document.getElementById("nombre").innerText = data.name.toUpperCase();
            document.getElementById("numero").innerText = data.id;
            document.getElementById("tipo").innerText = data.types.map(t => t.type.name).join(", ");
            document.getElementById("imagen").src = data.sprites.other["official-artwork"].front_default;
            document.getElementById("sonido").src = `https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/${data.id}.ogg`;
        })
        .catch(err => alert("Error: " + err.message));
}

async function getHabitats() {
    try {
        const res = await fetch(`${apiUrl}pokemon-habitat/`);
        const data = await res.json();
        mostrarHabitats(data.results);
    } catch (err) {
        console.error("Error al obtener hábitats:", err);
    }
}

function mostrarHabitats(habitats) {
    const contenedor = document.getElementById("habitats-list");
    if (!contenedor) return;
    contenedor.innerHTML = "";
    habitats.forEach(h => {
        const btn = document.createElement("button");
        btn.textContent = h.name;
        btn.onclick = () => getPokemonsByHabitat(h.url);
        contenedor.appendChild(btn);
    });
}

async function getPokemonsByHabitat(habitatUrl) {
    try {
        const res = await fetch(habitatUrl);
        const data = await res.json();
        const promises = data.pokemon_species.map(async s => {
            const nombre = corregirNombre(s.name);
            const pokeRes = await fetch(`${apiUrl}pokemon/${nombre}`);
            return pokeRes.json();
        });
        const pokemons = await Promise.all(promises);
        mostrarPokemonsHabitat(pokemons);
    } catch (err) {
        console.error("Error al obtener Pokémon por hábitat:", err);
    }
}

async function mostrarPokemonsHabitat(pokemons) {
    const contenedor = document.getElementById("pokemons-list");
    if (!contenedor) return;
    contenedor.innerHTML = "";
    for (const poke of pokemons.slice(0, 20)) {
        try {
            const tipos = poke.types.map(t => t.type.name).join(', ');
            const speciesRes = await fetch(poke.species.url);
            const species = await speciesRes.json();
            const juegos = species.game_indices?.map(g => g.version.name).join(', ') || 'No disponible';
            const imagen = poke.sprites.other["official-artwork"].front_default || poke.sprites.front_default;

            const card = document.createElement("div");
            card.classList.add("pokemon-card");
            card.innerHTML = `
                <h3>${poke.name.toUpperCase()}</h3>
                <p><strong>Número:</strong> ${poke.id}</p>
                <p><strong>Tipo(s):</strong> ${tipos}</p>
                <img src="${imagen}" alt="${poke.name}">
                <p><strong>Juegos:</strong> ${juegos}</p>
                <audio controls>
                    <source src="https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/${poke.id}.ogg" type="audio/ogg">
                    Tu navegador no soporta audio.
                </audio>
            `;
            contenedor.appendChild(card);
        } catch (err) {
            console.error(`Error con el Pokémon ${poke.name}:`, err);
        }
    }
}
