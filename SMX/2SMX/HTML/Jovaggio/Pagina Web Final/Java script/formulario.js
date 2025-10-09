document.getElementById('contactForm').addEventListener('submit', function(event) {
    event.preventDefault(); 
  
    let nombre = document.getElementById('nombre').value;
    let apellido = document.getElementById('apellido').value;
    let telefono = document.getElementById('telefono').value;
    let email = document.getElementById('email').value;
    let Mensajes = document.getElementById('Mensajes').value;
  
    alert("Nombre: " + nombre +    
          "\nApellido: " + apellido +
          "\nTeléfono: " + telefono +
          "\nCorreo electrónico: " + email +
          "\nMensajes: " + Mensajes);
});

 