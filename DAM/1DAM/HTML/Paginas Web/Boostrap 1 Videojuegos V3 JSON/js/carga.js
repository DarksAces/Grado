document.addEventListener('DOMContentLoaded', function () {
    const gameCardsContainer = document.getElementById('game-cards-container');
    const modalsContainer = document.getElementById('modals-container');

    if (!gameCardsContainer || !modalsContainer) {
        console.error('No se encontraron los contenedores necesarios en el DOM.');
        return;
    }

    // Ruta correcta al JSON según tu estructura
    fetch('./JSON/games.json')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el archivo JSON');
            }
            return response.json();
        })
        .then(data => {
            // Asegurarse de acceder a "games"
            const games = data.games;

            games.forEach(game => {
                // Crear tarjeta
                const card = document.createElement('div');
                card.className = 'col-md-4 mb-4';
                card.innerHTML = `
                    <div class="card h-100">
                        <img src="${game.image}" class="card-img-top" alt="${game.title}">
                        <div class="card-body">
                            <h5 class="card-title"> ${game.title} </h5>
                            <p class="card-text">${game.description}</p>
                            
                            <p class="card-text">${game.calificacion}</p>
                            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#${game.modal.id}">Ver más</button>
                        </div>
                    </div>
                `;
                gameCardsContainer.appendChild(card);

                // Crear modal
                const modal = document.createElement('div');
                modal.className = 'modal fade';
                modal.id = game.modal.id;
                modal.tabIndex = -1;
                modal.setAttribute('aria-labelledby', `${game.modal.id}Label`);
                modal.setAttribute('aria-hidden', 'true');
                modal.innerHTML = `
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="${game.modal.id}Label"><i class="fa-solid fa-desktop"></i> ${game.modal.title} <i class="fa-solid fa-desktop"></i></h5> 
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body"> 
                                ${game.modal.body}
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            </div>
                        </div>
                    </div>
                `;
                modalsContainer.appendChild(modal);
            });
        })
        .catch(error => console.error('Error al cargar los datos:', error));
});
