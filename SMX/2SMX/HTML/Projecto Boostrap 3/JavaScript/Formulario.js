document.getElementById('formulario').addEventListener('submit', function (event) {
  event.preventDefault();

  let nombre = document.getElementById('nombre').value;
  let apellido = document.getElementById('apellido').value;
  let fechaNacimiento = document.getElementById('fechaNacimiento').value;
  let genero = document.querySelector('input[name="genero"]:checked').value;
  let gusto = document.getElementById('gusto').value;
  let faccion = document.getElementById('faccion').value;

  alert("Nombre: " + nombre +
    "\nApellido: " + apellido +
    "\nFecha de Nacimiento: " + fechaNacimiento +
    "\nGénero: " + genero +
    "\nGusto: " + gusto +
    "\nFacción: " + faccion);
});
