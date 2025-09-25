// ConsoleApplication1.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//
#include <cmath>
#include <iostream>
using namespace std;

int main()
{
    double a, b, c;

    // Solicita al usuario que introduzca los valores de a, b y c
    cout << "Introduce un valor para a: ";
    cin >> a;

    cout << "Introduce un valor para b: ";
    cin >> b;

    cout << "Introduce un valor para c: ";
    cin >> c;

    // Calcula el discriminante de la ecuación cuadrada
    double discriminante = (b * b) - 4 * a * c;

    // Verifica si el discriminante es negativo, cero o positivo
    if (discriminante < 0) {
        // Si el discriminante es negativo, no hay soluciones reales
        cout << "No hay soluciones reales." << endl;
    }
    else if (discriminante == 0) {
        // Si el discriminante es cero, hay una solución real
        double resultado = -b / (2 * a);
        cout << "La solucion es: " << endl;
        cout << "X = " << resultado << endl;
    }
    else if (b == 0) {
        //Si a es cero no puede haber soluciones debido a que no se puede elevar
        cout << "a no puede ser 0, porfavor introduce otro valor";

    }
    else {
        // Si el discriminante es positivo, hay dos soluciones reales
        double raiz = sqrt(discriminante);
        double resultado1 = (-b + raiz) / (2 * a);
        double resultado2 = (-b - raiz) / (2 * a);

        cout << "Las soluciones son: " << endl;
        cout << "X1 = " << resultado1 << endl;
        cout << "X2 = " << resultado2 << endl;
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
