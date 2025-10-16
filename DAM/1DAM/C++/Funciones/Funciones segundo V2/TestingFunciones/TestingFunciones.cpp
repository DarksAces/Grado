#include "pch.h"
#include "CppUnitTest.h"
#include "../FuncionesSegundogradoV2/FuncionesSegundogradoV2.cpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace TestingFunciones {
    TEST_CLASS(TestingFunciones) {
public:

    // Prueba para discriminante negativo
    TEST_METHOD(DiscriminanteNegativo) {
        a = 5;
        b = 6;
        c = 8;

        calcularDiscriminante(); 
        Assert::IsTrue(discriminante < 0, L"El discriminante debería ser negativo.");
    }

    // Prueba para discriminante igual a cero
    TEST_METHOD(DiscriminanteCero) {
        a = 1;
        b = 2;
        c = 1;

        calcularDiscriminante(); 
        Assert::IsTrue(discriminante == 0, L"El discriminante debería ser cero.");

        calcularRaices();
        Assert::AreEqual(X1, X2, L"Las raíces deberían ser iguales.");
    }

    // Prueba para discriminante positivo
    TEST_METHOD(DiscriminantePositivo) {
        a = 1;
        b = -3;
        c = 2;

        calcularDiscriminante(); 
        Assert::IsTrue(discriminante > 0, L"El discriminante debería ser positivo.");

        calcularRaices(); 
        Assert::AreNotEqual(X1, X2, L"Las raíces deberían ser diferentes.");
    }
    };
}
