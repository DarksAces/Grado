// Función para cargar el archivo JSON
function cargarJSON() {
    fetch('../JSON/galeria.json')
        .then(response => response.json())
        .then(data => {
            const listaConceptos = document.getElementById('lista-conceptos');
            data.conceptos_sostenibilidad.forEach(concepto => {
                const li = document.createElement('li');
                li.innerHTML = `<strong>${concepto.nombre}</strong>: ${concepto.descripcion}`;

                // Si el concepto tiene una imagen, añadirla
                if (concepto.imagen) {
                    const img = document.createElement('img');
                    img.src = concepto.imagen;
                    img.alt = 'Imagen de ODS';
                    li.appendChild(img);
                }

                listaConceptos.appendChild(li);
            });
        })
        .catch(error => console.error('Error al cargar el JSON:', error));
}

// Llamar a la función para cargar el JSON cuando la página se haya cargado
document.addEventListener('DOMContentLoaded', cargarJSON);
