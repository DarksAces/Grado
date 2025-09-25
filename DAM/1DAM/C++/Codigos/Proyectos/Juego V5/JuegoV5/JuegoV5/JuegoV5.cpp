// JuegoV5.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//
#include <iostream>
using namespace std;

struct Personaje {
    string nombre;
    int vida;
};

void introduccion() {
    cout << "La ciudad esta siendo atacada y no hay más héroes disponibles, eres la ultima esperanza. Derrota al villano que esta asolando la ciudad." << endl;
}

void ataque(Personaje& atacante, Personaje& objetivo, const string& mensaje) {
    objetivo.vida--;
    cout << mensaje << endl;
    if (objetivo.vida <= 0) {
        cout << (objetivo.nombre == "Villano" ? "Felicidades, has ganado el combate" : "Has sido derrotado y la ciudad destruida") << endl;
    }
}

void status(const Personaje& heroe, const Personaje& villano) {
    cout << "A tu enemigo le quedan " << villano.vida << " puntos de vida." << endl;
    cout << "A tu heroe le quedan " << heroe.vida << " puntos de vida." << endl;
}

string opciones() {
    string opcion;
    cout << "¿Quieres atacar, esquivar, ver el estado o huir?" << endl;
    cin >> opcion;
    return opcion;
}

void logicacombate(Personaje& heroe, Personaje& villano) {
    while (heroe.vida > 0 && villano.vida > 0) {
        string opcion = opciones();
        if (opcion == "atacar" || opcion == "Atacar" || opcion == "ATACAR") {
            ataque(heroe, villano, "El enemigo pierde un punto de vida.");
            if (villano.vida <= 0) break;
            ataque(villano, heroe, "Tu enemigo ataca.");
            if (heroe.vida <= 0) {
                cout << "Has perdido, pero no pasa nada, intentalo de nuevo." << endl;
                break;
            }
        }
        else if (opcion == "esquivar" || opcion == "Esquivar" || opcion == "ESQUIVAR") {
            cout << "Tu enemigo intenta atacarte, pero lo esquivas." << endl;
        }
        else if (opcion == "estado" || opcion == "Estado" || opcion == "ESTADO") {
            status(heroe, villano);
        }
        else if (opcion == "huir" || opcion == "Huir" || opcion == "HUIR") {
            cout << "Huyes; en consecuencia, la ciudad ha sido destruida y miles de personas masacradas." << endl;
            break;
        }
        else {
            cout << "Opción no valida. Inténtalo de nuevo." << endl;
        }
    }
}

int main() {
    Personaje heroe = { "Heroe", 2 };
    Personaje villano = { "Villano", 2 };

    introduccion();
    logicacombate(heroe, villano);
    return 0;
}

#include <iostream>
using namespace std;

struct Personaje {
    string nombre;
    int vida;
};

void introduccion() {
    cout << "La ciudad esta siendo atacada y no hay más héroes disponibles, eres la ultima esperanza. Derrota al villano que esta asolando la ciudad." << endl;
}

void ataque(Personaje& atacante, Personaje& objetivo, const string& mensaje) {
    objetivo.vida--;
    cout << mensaje << endl;
    if (objetivo.vida <= 0) {
        cout << (objetivo.nombre == "Villano" ? "Felicidades, has ganado el combate" : "Has sido derrotado y la ciudad destruida") << endl;
    }
}

void status(const Personaje& heroe, const Personaje& villano) {
    cout << "A tu enemigo le quedan " << villano.vida << " puntos de vida." << endl;
    cout << "A tu heroe le quedan " << heroe.vida << " puntos de vida." << endl;
}

string opciones() {
    string opcion;
    cout << "¿Quieres atacar, esquivar, ver el estado o huir?" << endl;
    cin >> opcion;
    return opcion;
}

void logicacombate(Personaje& heroe, Personaje& villano) {
    while (heroe.vida > 0 && villano.vida > 0) {
        string opcion = opciones();
        if (opcion == "atacar" || opcion == "Atacar" || opcion == "ATACAR") {
            ataque(heroe, villano, "El enemigo pierde un punto de vida.");
            if (villano.vida <= 0) break;
            ataque(villano, heroe, "Tu enemigo ataca.");
            if (heroe.vida <= 0) {
                cout << "Has perdido, pero no pasa nada, intentalo de nuevo." << endl;
                break;
            }
        }
        else if (opcion == "esquivar" || opcion == "Esquivar" || opcion == "ESQUIVAR") {
            cout << "Tu enemigo intenta atacarte, pero lo esquivas." << endl;
        }
        else if (opcion == "estado" || opcion == "Estado" || opcion == "ESTADO") {
            status(heroe, villano);
        }
        else if (opcion == "huir" || opcion == "Huir" || opcion == "HUIR") {
            cout << "Huyes; en consecuencia, la ciudad ha sido destruida y miles de personas masacradas." << endl;
            break;
        }
        else {
            cout << "Opción no valida. Inténtalo de nuevo." << endl;
        }
    }
}

int main() {
    Personaje heroe = { "Heroe", 2 };
    Personaje villano = { "Villano", 2 };

    introduccion();
    logicacombate(heroe, villano);
    return 0;
}



// Ejecutar programa: Ctrl + F5 o menú Depurar > Iniciar sin depurar
// Depurar programa: F5 o menú Depurar > Iniciar depuración

// Sugerencias para primeros pasos: 1. Use la ventana del Explorador de soluciones para agregar y administrar archivos
//   2. Use la ventana de Team Explorer para conectar con el control de código fuente
//   3. Use la ventana de salida para ver la salida de compilación y otros mensajes
//   4. Use la ventana Lista de errores para ver los errores
//   5. Vaya a Proyecto > Agregar nuevo elemento para crear nuevos archivos de código, o a Proyecto > Agregar elemento existente para agregar archivos de código existentes al proyecto
//   6. En el futuro, para volver a abrir este proyecto, vaya a Archivo > Abrir > Proyecto y seleccione el archivo .sln
