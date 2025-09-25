
const campo1 = document.getElementById('texto1');
const campo2 = document.getElementById('texto2');

campo1.addEventListener('input', compararTexto);
campo2.addEventListener('input', compararTexto);

function compararTexto() {
    if (campo1.value === campo2.value) {
        resultado.innerHTML = '==';
    } else {
        resultado.innerHTML = '!=';
    }
}


const texto1 = document.getElementById('texto');
const contador = document.getElementById('contador');

texto1.addEventListener('input', contarLetras);

function contarLetras() {
    const letras = texto1.value.replace(/[^a-zA-Z]/g, '');
    const letrasCount = letras.length > 30 ? 30 : letras.length;
    contador.innerHTML = letrasCount;
}


function colorHeader() {
    let selected = document.querySelector('input[name="colorH"]:checked');
    if (selected) {
        document.querySelector('header').style.backgroundColor = selected.value;
    }
}

function colorFooter() {
    let selected = document.querySelector('input[name="colorF"]:checked');
    if (selected) {
        document.querySelector('footer').style.backgroundColor = selected.value;
    }
}

const headerRadios = document.querySelectorAll('input[name="colorH"]');
headerRadios.forEach(radio => {
    radio.addEventListener('change', colorHeader);
});

const footerRadios = document.querySelectorAll('input[name="colorF"]');
footerRadios.forEach(radio => {
    radio.addEventListener('change', colorFooter);
});


const alturaInput = document.getElementById('altura');
const anchuraInput = document.getElementById('anchura');
const valorAltura = document.getElementById('valor-altura');
const valorAnchura = document.getElementById('valor-anchura');
const sizePreview = document.getElementById('size_preview');

function updateSize() {

    sizePreview.style.height = `${alturaInput.value}px`;
    sizePreview.style.width = `${anchuraInput.value}px`;


    valorAltura.textContent = alturaInput.value;
    valorAnchura.textContent = anchuraInput.value;
}

alturaInput.addEventListener('input', updateSize);
anchuraInput.addEventListener('input', updateSize);


const numeroInput = document.getElementById('numero');
const resultadoRaiz = document.getElementById('resultado_raiz');

numeroInput.addEventListener('input', function () {
    const num = parseFloat(numeroInput.value);
    if (!isNaN(num) && num >= 0) {
        resultadoRaiz.value = Math.sqrt(num).toFixed(2);
    } else {
        resultadoRaiz.value = 'error';
    }
});


const bebidaOptions = document.querySelectorAll('input[name="bebida"]');
const complementoOptions = document.querySelectorAll('input[name="complemento"]');
const extrasOptions = document.querySelectorAll('input[name="extras"]');
const vasoOptions = document.querySelectorAll('input[name="vaso"]');
const totalInput = document.getElementById('total');

function calculateTotal() {
    let basePrice = 0;


    const bebida = document.querySelector('input[name="bebida"]:checked').value;
    basePrice += bebida === 'te' ? 1.50 : 2.00;


    const complemento = document.querySelector('input[name="complemento"]:checked').value;
    if (complemento === 'leche') basePrice += 0.30;
    if (complemento === 'leche_sin_lactosa') basePrice += 0.50;


    const extras = document.querySelector('input[name="extras"]:checked').value;
    if (extras === 'muffin') basePrice += 2.00;
    if (extras === 'cookie') basePrice += 1.50;

    // Adjust for cup type
    const vaso = document.querySelector('input[name="vaso"]:checked').value;
    if (vaso === 'propio') basePrice *= 0.95;
    if (vaso === 'plastico') basePrice *= 1.05;

    totalInput.value = `€${basePrice.toFixed(2)}`;
}


bebidaOptions.forEach(option => option.addEventListener('change', calculateTotal));
complementoOptions.forEach(option => option.addEventListener('change', calculateTotal));
extrasOptions.forEach(option => option.addEventListener('change', calculateTotal));
vasoOptions.forEach(option => option.addEventListener('change', calculateTotal));


document.addEventListener('DOMContentLoaded', function () {

    updateSize();
    calculateTotal();
});