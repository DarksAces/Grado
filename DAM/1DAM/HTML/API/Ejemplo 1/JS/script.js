//console.log('Hello World!'); Se muestra en consola inspeccionar
//window.alert('Hello World desdev ventana emergente!'); Abre una ventana

//parrafo1.innerHTML = 'Hola Mundo!'; //inserta el texto en el parrafo
//var parrafo1 = document.getElementById('parrafo1');  //declaramos una variable pero lo ve todos (No usar mejor)
//declaramos una variable pero solo lo ve el bloque
//const parrafo1 = document.getElementById('parrafo1');
//document.getElementById('parrafo2').innerHTML = 'Hola ' + nombre;
//let parrafo1 = document.getElementById('parrafo1');
//{
//parrafo1.innerHTML = 'Hola Mundo!'; //inserta el texto en el parrafo
//}
//let num1 = 5; //Sabe interpretarse solo
//function holaMundo(){
//   console.log('Hola Mundo!');
//  }
//  function holaMundo() {
//  alert("Hola Mundo!");
// }

//function holaMundo(texto1) {
// console.log(texto1);
//}

//holaMundo('Hola Mundo!');

//function holaMundo(texto1) {
//  return texto1;
// }

//  holaMundo('Hola Mundo!');


//function suma(num1, num2) {
//    alert("La suma de " + num1 + " + " + num2 + " es: " + (num1 + num2));
//}


//function datos() {
//    var num1 = Number(document.getElementById('num1').value);
//    var num2 = Number(document.getElementById('num2').value);
//    var result = num1 + num2;
//   document.getElementById('parrafo1').innerText = 'Resultado: ' + result;
//}

//function datos() {
//    var num1 = parseInt(document.getElementById('num1').value);
//    var num2 = parseInt(document.getElementById('num2').value);
//    if (isNaN(num1) || isNaN(num2)) {
//        document.getElementById('parrafo1').innerText = 'Error: Ingrese ambos números válidos.';
//       return;
//    }
//   var result = num1 + num2;
//    document.getElementById('parrafo1').innerText = 'Resultado: ' + result;
//}

//function suma(num1, num2) {

//    alert("La suma de " + num1 + " + " + num2 + " es: " + (num1 + num2));
//}

// let nombre = 'Daniel';
// let apellido = 'Garcia';
// let apellido2 = 'Brun';




// for (let i = 0; i < persona.aficiones.length; i++) {
//     console.log(persona.nombre, persona.apellido, persona.apellido2, persona.aficiones[i]);


// }

// console.log(persona.nombre, persona.apellido, persona.apellido2, persona.aficiones[0]);

// console.log(persona.nombre);
// console.log(persona.apellido);
// console.log(persona.apellido2);

// persona.aficiones.forEach((aficion) => {console.log(persona.nombre, persona.apellido, persona.apellido2, aficion);});


// let persona = {
//     nombre: 'Daniel',
//     apellido: 'Garcia',
//     apellido2: 'Brun',
//     aficiones: ["Patinar", "Leer"],
//     direcciones : {
//         direccion1 : {
//             calle: 'Calle ',
//             numero: '123',
//             ciudad: 'Madrid',
//             pais: 'España',
//         },
//         direccion2: { 
//             calle: 'Calle ',
//             numero: '456',
//             ciudad: 'Barcelona',
//             pais: 'España',
//         },
//     }

//     };

//     for (let key in persona.direcciones) {
//         let direccion = persona.direcciones[key];
//         console.log(`${direccion.calle}, ${direccion.numero}, ${direccion.ciudad}, ${direccion.pais}`);
//     }

// document.querySelector('#parrafo2').innerHTML = 'Hola Caracolas'; //Obtenerlo por ID
//document.querySelector('.parrafo1').innerHTML = 'Hola Caracolas'; //Obtenerlo por clase pero el primero que encuentre
//document.querySelector("p").innerHTML = 'Hola Caracolas'; //por elemento
//document.querySelector("div p").innerHTML = 'Hola Caracolas'; //por elemento dentro de otro elemento

// document.querySelectorAll(".parrafo1, h1").forEach(p => {
//     p.innerHTML = 'Fururla';
//     p.style.color = 'rgb(34, 150, 243)'; // Azul vibrante
// });

// document.addEventListener('DOMContentLoaded', (event) => {
//   const radios = document.querySelectorAll('input[name="values"]');
//   radios.forEach((radio) => {
//       radio.addEventListener('click', (event) => {
//           console.log(event.target.value);
//       });
//   });
// });

function checkRadio() {
  let selected = document.querySelector('input[name="names"]:checked');

  if (selected) {
    console.log("El seleccionado es: " + selected.value);
  } else {
    console.log("No hay selección");
  }
}