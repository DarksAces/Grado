//Juego V3
#include <iostream>
using namespace std;

void introduccion(string mensaje) {
    cout << mensaje << endl;
}

void ataqueEnemigo(int& heroh) {
    heroh--;
    cout << "Tu enemigo ataca." << endl;
    if (heroh <= 0) {
        cout << "Has sido derrotado y la ciudad destruida." << endl;
    }
}

void ataqueHeroe(int& enemyh) {
    enemyh--;
    cout << "El enemigo pierde un punto de vida." << endl;
    if (enemyh <= 0) {
        cout << "Felicidades, has ganado el combate!" << endl;
    }
}

void status(int enemyh, int heroh) {
    cout << "A tu enemigo le quedan " << enemyh << " puntos de vida." << endl;
    cout << "A tu héroe le quedan " << heroh << " puntos de vida." << endl;
}

void opciones(string& opcion) {
    cout << "¿Quieres atacar, esquivar, ver el estado o huir?" << endl;
    cin >> opcion;
}

void logicacombate(int heroh, int enemyh) {
    string opcion;
    while (heroh > 0 && enemyh > 0) {
        opciones(opcion);
        if (opcion == "atacar" || opcion == "Atacar" || opcion == "ATACAR") {
            ataqueHeroe(enemyh);
            if (enemyh <= 0) {
                break;
            }
            ataqueEnemigo(heroh);
            if (heroh <= 0) {
                cout << "Has perdido, pero no pasa nada, inténtalo de nuevo!" << endl;
                break;
            }
        }
        else if (opcion == "esquivar" || opcion == "Esquivar" || opcion == "ESQUIVAR") {
            cout << "Tu enemigo intenta atacarte, pero lo esquivas." << endl;
        }
        else if (opcion == "estado" || opcion == "Estado" || opcion == "ESTADO") {
            status(enemyh, heroh);
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
    string mensaje = "La ciudad está siendo atacada y no hay más héroes disponibles, eres la última esperanza. Derrota al villano que está asolando la ciudad.";
    int heroeVida = 2;
    int enemigoVida = 2;

    introduccion(mensaje);
    logicacombate(heroeVida, enemigoVida);

    return 0;
}
