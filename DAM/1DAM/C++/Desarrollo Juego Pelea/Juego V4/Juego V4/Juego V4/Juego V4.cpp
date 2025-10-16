// Juego V4.cpp 
//

#include <iostream>
using namespace std;

string opcion;
int heroh = 2;
int enemyh = 2;

void introduccion() {
    cout << "La ciudad está siendo atacada y no hay más héroes disponibles, eres la última esperanza. Derrota al villano que está asolando la ciudad." << endl;
}

int ataqueEnemigo(int heroh) {
    heroh--;
    cout << "Tu enemigo ataca." << endl;
    if (heroh <= 0) {
        cout << "Has sido derrotado y la ciudad destruida." << endl;
    }
    return heroh;
}

int ataqueHeroe(int enemyh) {
    enemyh--;
    cout << "El enemigo pierde un punto de vida." << endl;
    if (enemyh <= 0) {
        cout << "Felicidades, has ganado el combate!" << endl;
    }
    return enemyh;
}

void status(int heroh, int enemyh) {
    cout << "A tu enemigo le quedan " << enemyh << " puntos de vida." << endl;
    cout << "A tu héroe le quedan " << heroh << " puntos de vida." << endl;
}

string opciones() {
    cout << "¿Quieres atacar, esquivar, ver el estado o huir?" << endl;
    cin >> opcion;
    return opcion;
}

void logicacombate(int heroh, int enemyh) {
    while (heroh > 0 && enemyh > 0) {
        string opcion = opciones();
        if (opcion == "atacar" || opcion == "Atacar" || opcion == "ATACAR") {
            enemyh = ataqueHeroe(enemyh);
            if (enemyh <= 0) {
                break;
            }
            heroh = ataqueEnemigo(heroh);
            if (heroh <= 0) {
                cout << "Has perdido, pero no pasa nada, inténtalo de nuevo!" << endl;
                break;
            }
        }
        else if (opcion == "esquivar" || opcion == "Esquivar" || opcion == "ESQUIVAR") {
            cout << "Tu enemigo intenta atacarte, pero lo esquivas." << endl;
        }
        else if (opcion == "estado" || opcion == "Estado" || opcion == "ESTADO") {
            status(heroh, enemyh);
        }
        else if (opcion == "huir" || opcion == "Huir" || opcion == "HUIR") {
            cout << "Huyes; en consecuencia, la ciudad ha sido destruida y miles de personas masacradas." << endl;
            break;
        }
        else {
            cout << "Opción no válida. Inténtalo de nuevo." << endl;
        }
    }
}

int main() {
    introduccion();
    logicacombate(heroh, enemyh);
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
