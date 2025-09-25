// Agregamos un event listener para cuando se cargue completamente la página
window.addEventListener('load', function () {
    buscarPokemon('1');  // Busca el Pokémon número 1
});

function buscarPokemon(input = document.getElementById("pokemonInput").value.toLowerCase()) {
    const url = `https://pokeapi.co/api/v2/pokemon/${input}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Pokémon no encontrado");
            }
            return response.json();
        })
        .then(data => {
            document.getElementById("nombre").innerText = data.name.toUpperCase();
            document.getElementById("numero").innerText = data.id;

            // Obtener tipos con map
            const tipos = data.types.map(t => t.type.name).join(", ");
            document.getElementById("tipo").innerText = tipos;

            // Obtener imagen oficial
            document.getElementById("imagen").src = data.sprites.other["official-artwork"].front_default;

            // Obtener sonido
            document.getElementById("sonido").src = `https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/${data.id}.ogg`;
        })
        .catch(error => {
            alert("Error: " + error.message);
        });
}