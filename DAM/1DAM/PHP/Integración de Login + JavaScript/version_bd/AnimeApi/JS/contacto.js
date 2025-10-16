document.addEventListener('DOMContentLoaded', function () {
  const contactoForm = document.getElementById('contacto-form');

  contactoForm?.addEventListener('submit', function (e) {
    e.preventDefault();

    const nombre = document.getElementById('nombre').value;
    const email = document.getElementById('email').value;
    const mensaje = document.getElementById('mensaje').value;

    if (!nombre || !email || !mensaje) {
      alert('Por favor completa todos los campos');
      return;
    }

    // Aquí podrías enviar los datos a un servidor
    // Como este es un ejemplo, solo mostraremos un mensaje
    alert(`Gracias ${nombre} por tu mensaje. Te contactaremos pronto en ${email}`);

    // Limpiamos el formulario
    contactoForm.reset();
  });
});