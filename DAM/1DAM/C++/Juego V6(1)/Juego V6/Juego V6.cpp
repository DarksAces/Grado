// JuegoV6.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//
#include <iostream>
#include <string>
#include "Character.h"
using namespace std;

int main() {
    Character enemy("Sauron", 100, 20);
    Character hero("Gandalf", 150, 25);

    cout << enemy.getStatus() << endl;
    cout << hero.getStatus() << endl;

    if (enemy.getHP() > 0 && hero.getHP() > 0) {
        do {
            // Enemy ataca a hero
            cout << "El enemigo nos ataca" << endl;
            hero.setHP(hero.getHP() - enemy.getDamage());

            // Verificar si el heroe sigue vivo antes de atacar
            if (hero.getHP() > 0) {
                string opcion;
                cout << "Si quieres atacar presiona A, si quieres defenderte presiona D: ";
                cin >> opcion;

                if (opcion == "A" || opcion == "a") {
                    cout << "Atacamos al enemigo" << endl;
                    enemy.setHP(enemy.getHP() - hero.getDamage());
                }
                else if (opcion == "D" || opcion == "d") {
                    cout << "Te defiendes y reduces el danyo en 50%" << endl;
                    hero.setHP(hero.getHP() + (enemy.getDamage() / 2));  // Recupera la mitad del danyo recibido
                }
                else {
                    cout << "Opción no valida, pierdes el turno" << endl;
                }
            }

            // Mostrar el estado actual
            cout << enemy.getStatus() << endl;
            cout << hero.getStatus() << endl;

        } while (enemy.getHP() > 0 && hero.getHP() > 0); // Sale cuando uno muere

        cout << "La batalla ha terminado." << endl;
        if (hero.getHP() > 0) {
            cout << "Gandalf ha ganado!" << endl;

        }
        else {
            cout << "Sauron ha ganado..." << endl;
        }
    }
    else {
        cout << "Uno de los personajes ya está muerto, no pueden pelear." << endl;
    }

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
