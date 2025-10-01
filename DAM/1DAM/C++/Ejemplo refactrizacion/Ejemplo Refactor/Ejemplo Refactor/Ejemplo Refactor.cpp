// Ejemplo Refactor.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//

#include <iostream>
using namespace std;

//int hambrePersona1 = 30;
//int hambrePersona2 = 30;
//int comidaPersona1 = 5;
//int comidaPersona2 = 6;

// PROTOTIPOS
int persona1Come(int);
void persona2Come(int&);
void mostrarEstado(int, int);

int main() {
    int hambrePers1 = 30;
    int hambrePers2 = 30;
    /*int comidaPersona1 = 5;*/
    //int comidaPersona2 = 6;

    cout << "¡Comienza el concurso de comer hamburguesas!\n";

    while (hambrePers1 > 0 && hambrePers2 > 0) {
        mostrarEstado(hambrePers1, hambrePers2);

        hambrePers1 = persona1Come(hambrePers1);
        if (hambrePers1 <= 0) {
            cout << "¡Persona 1 ya está llena!" << endl;
            break;
        }

        persona2Come(hambrePers2);
        if (hambrePers2 <= 0) {
            cout << "¡Persona 2 ya está llena!" << endl;
            break;
        }
    }

    return 0;
}


int persona1Come(int hambrePersona1) {
    int comidaPersona1 = 5;

    hambrePersona1 = hambrePersona1 - comidaPersona1;
    if (hambrePersona1 < 0)
        hambrePersona1 = 0;
    cout << "¡La persona1 come " << comidaPersona1 << " hamburguesas!" << endl;
    cout << "Hambre restante: " << hambrePersona1 << endl;
    cout << endl;
    return hambrePersona1;
}

void persona2Come(int& hambrePersona2) {
    int comidaPersona2 = 6;
    hambrePersona2 -= comidaPersona2;
    if (hambrePersona2 < 0)
        hambrePersona2 = 0;
    cout << "¡La persona2 come " << comidaPersona2 << " hamburguesas!" << endl;
    cout << "Hambre restante: " << hambrePersona2 << endl;
    cout << endl;
}
// PASO PARAMETROS POR VALOR
void mostrarEstado(int hambrePersona1, int hambrePersona2) {

    cout << "Hambre de Persona 1: " << hambrePersona1 << endl;
    cout << "Hambre de Persona 2: " << hambrePersona2 << endl;
}

// Ejecutar programa: Ctrl + F5 o menú Depurar > Iniciar sin depurar
// Depurar programa: F5 o menú Depurar > Iniciar depuración

// Sugerencias para primeros pasos: 1. Use la ventana del Explorador de soluciones para agregar y administrar archivos
//   2. Use la ventana de Team Explorer para conectar con el control de código fuente
//   3. Use la ventana de salida para ver la salida de compilación y otros mensajes
//   4. Use la ventana Lista de errores para ver los errores
//   5. Vaya a Proyecto > Agregar nuevo elemento para crear nuevos archivos de código, o a Proyecto > Agregar elemento existente para agregar archivos de código existentes al proyecto
//   6. En el futuro, para volver a abrir este proyecto, vaya a Archivo > Abrir > Proyecto y seleccione el archivo .sln
