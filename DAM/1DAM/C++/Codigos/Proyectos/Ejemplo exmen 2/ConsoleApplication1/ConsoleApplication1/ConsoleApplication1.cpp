// ConsoleApplication1.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//

#include <iostream>
// Prototipos

void leerCoeficientes(double& a, double& b, double& c);
int calcularSoluciones(double iA, double iB, double iC, double& oX1, double& oX2);
void mostrarSoluciones(int iNumSoluciones, double iA, double iB, double iC, double iX1, double iX2);



int main()
{
    double a = 0.0, b = 0.0, c = 0.0, X1 = 0.0, X2 = 0.0, discriminante;
    int numSoluciones = 0;

    leerCoeficientes(a, b, c);
    numSoluciones = calcularSoluciones(a, b, c, X1, X2);

}

int calcularSoluciones(double iA, double iB, double iC, double& oX1, double& oX2){
    int numSols;



    return numSols;
}


void leerCoeficientes(double& oA, double& oB, double& oC) {
    oA = 1.0;
    oB = 2.0;
    oC = 3.0;
}


void mostrarSoluciones(int iNumSoluciones, double iA, double iB, double iC, double iX1, double iX2) {

}

// Ejecutar programa: Ctrl + F5 o menú Depurar > Iniciar sin depurar
// Depurar programa: F5 o menú Depurar > Iniciar depuración

// Sugerencias para primeros pasos: 1. Use la ventana del Explorador de soluciones para agregar y administrar archivos
//   2. Use la ventana de Team Explorer para conectar con el control de código fuente
//   3. Use la ventana de salida para ver la salida de compilación y otros mensajes
//   4. Use la ventana Lista de errores para ver los errores
//   5. Vaya a Proyecto > Agregar nuevo elemento para crear nuevos archivos de código, o a Proyecto > Agregar elemento existente para agregar archivos de código existentes al proyecto
//   6. En el futuro, para volver a abrir este proyecto, vaya a Archivo > Abrir > Proyecto y seleccione el archivo .sln
