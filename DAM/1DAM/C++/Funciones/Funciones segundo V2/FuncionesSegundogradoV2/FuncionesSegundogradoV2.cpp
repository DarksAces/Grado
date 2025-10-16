
// Sugerencias para primeros pasos: 1. Use la ventana del Explorador de soluciones para agregar y administrar archivos
//   2. Use la ventana de Team Explorer para conectar con el control de código fuente
//   3. Use la ventana de salida para ver la salida de compilación y otros mensajes
//   4. Use la ventana Lista de errores para ver los errores
//   5. Vaya a Proyecto > Agregar nuevo elemento para crear nuevos archivos de código, o a Proyecto > Agregar elemento existente para agregar archivos de código existentes al proyecto
//   6. En el futuro, para volver a abrir este proyecto, vaya a Archivo > Abrir > Proyecto y seleccione el archivo .sln
#include <iostream>
#include <cmath> 
using namespace std;


double a, b, c, discriminante, X1, X2;


void pedirDatos() {
    do {
        cout << "Ingresa un valor mayor que 0 para a: ";
        cin >> a;
    } while (a == 0);

    cout << "Ingresa un valor para b: ";
    cin >> b;

    cout << "Ingresa un valor para c: ";
    cin >> c;
}


void calcularDiscriminante() {
    discriminante = (b * b) - 4 * a * c;
}


void calcularRaices() {
    double raiz = sqrt(discriminante);
    X1 = (-b + raiz) / (2 * a);
    X2 = (-b - raiz) / (2 * a);
}


void mostrarResultados() {
    if (discriminante < 0) {
        cout << "No hay soluciones reales." << endl;
    }
    else if (discriminante == 0) {
        cout << "Hay una solución real: X = " << X1 << endl;
    }
    else {
        cout << "Hay dos soluciones reales: X1 = " << X1 << ", X2 = " << X2 << endl;
    }
}


int main() {
    pedirDatos();            
    calcularDiscriminante(); 
    if (discriminante >= 0) {
        calcularRaices();  
    }
    mostrarResultados();     
    return 0;
}