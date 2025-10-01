// Juego V2.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//
#include <iostream>
using namespace std;

string opcion;
int heroh = 2;
int enemyh = 2;

void introduccion() {
    cout << "La ciudad esta siendo atacada y no hay mas heroes disponibles, eres la ultima esperanza. Derrota al villano que esta asolando la ciudad." << endl;
}

void ataqueEnemigo() {
    heroh--;
    cout << "Tu enemigo ataca." << endl;
    if (heroh <= 0) {
        cout << "Has sido derrotado y la ciudad destruida." << endl;
    }
}

void ataqueHeroe() {
    enemyh--;
    cout << "El enemigo pierde un punto de vida." << endl;
    if (enemyh <= 0) {
        cout << "Felicidades, has ganado el combate!" << endl;
    }
}

void status() {
    cout << "A tu enemigo le quedan " << enemyh << " puntos de vida." << endl;
    cout << "A tu heroe le quedan " << heroh << " puntos de vida." << endl;
}

void opciones() {
    cout << "Quieres atacar, esquivar, ver el estado o huir?" << endl;
    cin >> opcion;
}

void logicacombate() {
    while (heroh > 0 && enemyh > 0) {
        opciones();
        if (opcion == "atacar" || opcion == "Atacar" || opcion == "ATACAR") {
            ataqueHeroe();
            if (enemyh <= 0) {
                break;
            }
            ataqueEnemigo();
            if (heroh <= 0) {
                cout << "Has perdido, pero no pasa nada, inténtalo de nuevo!" << endl;
                break;
            }
        }
        else if (opcion == "esquivar" || opcion == "Esquivar" || opcion == "ESQUIVAR") {
            cout << "Tu enemigo intenta atacarte, pero lo esquivas." << endl;
        }
        else if (opcion == "estado" || opcion == "Estado" || opcion == "ESTADO") {
            status();
        }
        else if (opcion == "huir" || opcion == "Huir" || opcion == "HUIR") {
            cout << "Huyes; en consecuencia, la ciudad ha sido destruida y miles de personas masacradas." << endl;
            break;
        }
        else {
            cout << "Opción no valida. Intentalo de nuevo." << endl;
        }
    }
}

int main() {
    introduccion();
    logicacombate();
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
