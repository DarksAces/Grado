let sequence = [];
let humanSequence = [];
let level = 0;
let timer; // Temporizador
const timeLimit = 5000; // Tiempo límite para el turno del jugador (en ms)

// Referencias a los elementos DOM
const startButton = document.getElementById('startButton');
const heading = document.getElementById('heading');
const info = document.getElementById('info');
const tileContainer = document.getElementById('tileContainer');

function resetGame(text) {
    alert(text);
    sequence = [];
    humanSequence = [];
    level = 0;
    startButton.classList.remove('hidden');
    heading.textContent = 'Simon Game';
    info.classList.add('hidden');
    tileContainer.classList.add('unclickable');
    stopCountdown(); // Asegurarse de detener el temporizador
}

function humanTurn(level) {
    tileContainer.classList.remove('unclickable');
    startCountdown(); // Inicia la cuenta regresiva
}

function activateTile(color) {
    const tile = document.querySelector(`[data-tile='${color}']`);
    if (!tile) return;

    tile.classList.add('activated');
    setTimeout(() => {
        tile.classList.remove('activated');
    }, 300);
}

function playRound(nextSequence) {
    tileContainer.classList.add('unclickable');
    nextSequence.forEach((color, index) => {
        setTimeout(() => {
            activateTile(color);
        }, (index + 1) * 600);
    });

    setTimeout(() => {
        humanTurn(level);
    }, nextSequence.length * 600 + 1000);
}

function nextStep() {
    const tiles = ['red', 'green', 'blue', 'yellow'];
    const randomIndex = Math.floor(Math.random() * tiles.length);
    return tiles[randomIndex];
}

function nextRound() {
    level += 1;
    humanSequence = [];
    const newColor = nextStep();
    sequence.push(newColor);
    updateLevelDisplay(level);
    playRound(sequence);
}

function updateLevelDisplay(level) {
    heading.textContent = `Nivel ${level}`;
}

function handleMistake(reason = '¡Juego terminado! Has cometido un error.') {
    stopCountdown(); // Detener temporizador
    resetGame(reason);
}

function handleSequenceCompletion() {
    stopCountdown(); // Detener temporizador
    setTimeout(() => {
        nextRound();
    }, 1000);
}

function startGame() {
    sequence = [];
    humanSequence = [];
    level = 0;
    startButton.classList.add('hidden');
    info.classList.remove('hidden');
    info.textContent = 'Espera a la computadora';
    nextRound();
}

// Temporizador
function startCountdown() {
    let remainingTime = timeLimit / 1000; // Convertir a segundos
    info.textContent = `Tu turno: Tienes ${remainingTime} segundos`;

    timer = setInterval(() => {
        remainingTime -= 1;
        info.textContent = `Tu turno: Tienes ${remainingTime} segundos`;

        if (remainingTime <= 0) {
            clearInterval(timer);
            handleMistake('¡Se agotó el tiempo! Juego terminado.'); // Mensaje personalizado
        }
    }, 1000); // Actualiza cada segundo
}

function stopCountdown() {
    clearInterval(timer); // Detiene el temporizador
}

// Listeners
startButton.addEventListener('click', startGame);
tileContainer.addEventListener('click', event => {
    const { tile } = event.target.dataset;
    if (!tile) return;

    const correctTile = sequence[humanSequence.length];
    humanSequence.push(tile);
    activateTile(tile);

    if (tile !== correctTile) {
        handleMistake();
        return;
    }

    if (humanSequence.length === sequence.length) {
        handleSequenceCompletion();
    }
});
