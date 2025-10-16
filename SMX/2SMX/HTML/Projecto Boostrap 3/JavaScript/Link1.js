function copiarEnlace(enlaceId) {
    const enlace = document.getElementById(enlaceId).getAttribute("href");
    const resto = enlace.substring(1);
    navigator.clipboard.writeText(resto)
        .then(() => {
            alert("Enlace copiado al portapapeles: " + resto);
        });
}
