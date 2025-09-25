document.addEventListener('DOMContentLoaded', function () {
  const favoritos = JSON.parse(localStorage.getItem('favoritos')) || {};
  const favoritosDiv = document.getElementById('favoritos');
  const filtroInput = document.getElementById('filtro');

  function mostrarFavoritos(filtro = '') {
    favoritosDiv.innerHTML = '';

    // Si no hay favoritos, mostrar mensaje
    if (Object.keys(favoritos).length === 0) {
      const noFavoritosP = document.createElement('p');
      noFavoritosP.className = 'no-favoritos';
      noFavoritosP.textContent = 'No tienes animes favoritos. ¡Busca y agrega algunos!';
      favoritosDiv.appendChild(noFavoritosP);
      return;
    }

    Object.keys(favoritos).forEach((genero) => {
      // Filtrar los animes de este género que coincidan con el filtro
      const animesFiltrados = favoritos[genero]
        .filter((nombre) => nombre.toLowerCase().includes(filtro.toLowerCase()));

      // Si no hay animes que coincidan con el filtro en este género, no mostrar el género
      if (animesFiltrados.length === 0) return;

      const generoDiv = document.createElement('div');
      generoDiv.innerHTML = `<h2>${genero}</h2>`;
      const lista = document.createElement('ul');

      animesFiltrados.forEach((nombre) => {
        const item = document.createElement('li');
        item.innerHTML = `
            ${nombre} 
            <button class="eliminar-btn" data-genero="${genero}" data-nombre="${nombre}">
              Eliminar
            </button>
          `;
        lista.appendChild(item);
      });

      generoDiv.appendChild(lista);
      favoritosDiv.appendChild(generoDiv);
    });

    // Añadir eventos a los botones de eliminar
    document.querySelectorAll('.eliminar-btn').forEach((btn) => {
      btn.addEventListener('click', function () {
        const genero = this.getAttribute('data-genero');
        const nombre = this.getAttribute('data-nombre');
        eliminarDeFavoritos(genero, nombre);
      });
    });
  }

  // Función para eliminar un anime de favoritos
  function eliminarDeFavoritos(genero, nombre) {
    if (favoritos[genero]) {
      const index = favoritos[genero].indexOf(nombre);
      if (index !== -1) {
        favoritos[genero].splice(index, 1);

        // Si el género queda vacío, eliminarlo
        if (favoritos[genero].length === 0) {
          delete favoritos[genero];
        }

        localStorage.setItem('favoritos', JSON.stringify(favoritos));
        mostrarFavoritos(filtroInput.value);
        alert(`${nombre} eliminado de favoritos.`);
      }
    }
  }

  // Evento para el filtro
  filtroInput?.addEventListener('input', function () {
    mostrarFavoritos(this.value);
  });

  // Mostrar favoritos al cargar la página
  mostrarFavoritos();
});