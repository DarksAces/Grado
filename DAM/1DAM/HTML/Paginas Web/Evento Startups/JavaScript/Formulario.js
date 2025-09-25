document.getElementById('formulario').addEventListener('submit', function (event) {
    event.preventDefault();

    let nombre = document.getElementById('nombre').value;
    let email = document.getElementById('email').value;
    let telefono = document.getElementById('telefono').value;
    let mensaje = document.getElementById('mensaje').value;

    alert("Formulario enviado con éxito.\n\n" +
        "Nombre: " + nombre +
        "\nCorreo Electrónico: " + email +
        "\nTeléfono: " + telefono +
        "\nMensaje: " + mensaje);
});