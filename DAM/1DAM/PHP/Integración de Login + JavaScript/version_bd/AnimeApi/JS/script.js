const formulario = document.getElementById('formulario');
const resultado = document.getElementById('resultado');

// Función principal de búsqueda
formulario?.addEventListener('submit', async (e) => {
  e.preventDefault();
  const busqueda = document.getElementById('busqueda').value.trim();

  if (busqueda === '') {
    resultado.textContent = 'Por favor, escribe un nombre.';
    return;
  }

  try {
    const res = await fetch(`https://api.jikan.moe/v4/anime?q=${busqueda}`);
    if (!res.ok) {
      throw new Error('Error en la búsqueda');
    }
    const datos = await res.json();

    if (datos.data.length === 0) {
      resultado.textContent = 'No se encontró ningún anime con ese nombre.';
      return;
    }

    // Limpiar resultados anteriores
    resultado.innerHTML = '';

    // Mostrar hasta 5 resultados
    const animes = datos.data.slice(0, 5);

    animes.forEach(anime => {
      const generos = anime.genres.map(g => g.name).join(', ');
      const animeDiv = document.createElement('div');
      animeDiv.className = 'anime-card';
      animeDiv.innerHTML = `
        <h2>${anime.title}</h2>
        <img src="${anime.images.jpg.image_url}" alt="${anime.title}" width="200">
        <p><strong>Géneros:</strong> ${generos}</p>
        <p>${anime.synopsis ? anime.synopsis.substring(0, 200) + '...' : 'Sin descripción disponible'}</p>
        <button class="favorito-btn" data-nombre="${anime.title}" data-genero="${anime.genres[0]?.name || 'Sin clasificar'}">
          Añadir a Favoritos
        </button>
      `;
      resultado.appendChild(animeDiv);
    });

    // Añadir evento a los botones de favoritos
    document.querySelectorAll('.favorito-btn').forEach((btn) => {
      btn.addEventListener('click', function () {
        const nombre = this.getAttribute('data-nombre');
        const genero = this.getAttribute('data-genero');
        añadirAFavoritos(nombre, genero);
      });
    });

  } catch (error) {
    resultado.textContent = 'Hubo un error al buscar el anime. ' + error.message;
    console.error(error);
  }
});

// Función para añadir a favoritos
function añadirAFavoritos(nombre, genero) {
  const favoritos = JSON.parse(localStorage.getItem('favoritos')) || {};

  if (!favoritos[genero]) {
    favoritos[genero] = [];
  }

  if (!favoritos[genero].includes(nombre)) {
    favoritos[genero].push(nombre);
    localStorage.setItem('favoritos', JSON.stringify(favoritos));
    alert(`${nombre} añadido a favoritos en la categoría ${genero}`);
  } else {
    alert(`${nombre} ya está en favoritos.`);
  }
}