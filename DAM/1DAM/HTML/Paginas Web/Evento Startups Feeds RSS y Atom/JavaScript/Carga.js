// Función para cargar el archivo JSON
function cargarJSON() {
    fetch('../JSON/ConceptosS.json')
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
                    img.className = 'imagen-ods';
                    li.appendChild(img);
                }

                listaConceptos.appendChild(li);
            });
        })
        .catch(error => console.error('Error al cargar JSON:', error));
}

// Función para cargar el archivo XML
function cargarXML() {
    fetch('../XML/ODS.xml')
        .then(response => response.text())
        .then(str => (new window.DOMParser()).parseFromString(str, "text/xml"))
        .then(data => {
            const listaObjetivos = document.getElementById('lista-objetivos');
            const objetivos = data.getElementsByTagName('objetivo');
            Array.from(objetivos).forEach(objetivo => {
                const numero = objetivo.getElementsByTagName('numero')[0].textContent;
                const nombre = objetivo.getElementsByTagName('nombre')[0].textContent;
                const descripcion = objetivo.getElementsByTagName('descripcion')[0].textContent;
                const imagen = objetivo.getElementsByTagName('imagen')[0].textContent;
                const li = document.createElement('li');
                li.innerHTML = `<strong  class="imagen-objetivo">Objetivo ${numero}:</strong> ${nombre} - ${descripcion} <br> <img src="${imagen}" alt="Imagen del objetivo" class="imagen-objetivo "/>`;
                listaObjetivos.appendChild(li);
            });
        })
        .catch(error => console.error('Error al cargar XML:', error));
}

// Llamar a las funciones cuando la página cargue
window.onload = function () {
    cargarJSON();
    cargarXML();
};
