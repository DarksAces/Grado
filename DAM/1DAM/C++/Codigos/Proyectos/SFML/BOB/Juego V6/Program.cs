#include <iostream>
using namespace std;

struct Personaje
{
    string nombre;
    int vida;
};

void introduccion()
{
    cout << "La ciudad esta siendo atacada y no hay mas heroes disponibles, eres la ultima esperanza. Derrota al villano que esta asolando la ciudad." << endl;
}

void ataque(Personaje& atacante, Personaje& objetivo, const string& mensaje)
{
    objetivo.vida--;
    cout << mensaje << endl;
    if (objetivo.vida <= 0)
    {
        cout << (objetivo.nombre == "Villano" ? "Felicidades, has ganado el combate" : "Has sido derrotado y la ciudad destruida") << endl;
    }
}

void status(const Personaje& heroe, const Personaje& villano)
{
    cout << "A tu enemigo le quedan " << villano.vida << " puntos de vida." << endl;
    cout << "A tu heroe le quedan " << heroe.vida << " puntos de vida." << endl;
}

string opciones()
{
    string opcion;
    cout << "Quieres atacar, esquivar, ver el estado o huir?" << endl;
    cin >> opcion;
    return opcion;
}

void logicacombate(Personaje& heroe, Personaje& villano)
{
    while (heroe.vida > 0 && villano.vida > 0)
    {
        string opcion = opciones();
        if (opcion == "atacar" || opcion == "Atacar" || opcion == "ATACAR")
        {
            ataque(heroe, villano, "El enemigo pierde un punto de vida.");
            if (villano.vida <= 0) break;
            ataque(villano, heroe, "Tu enemigo ataca.");
            if (heroe.vida <= 0)
            {
                cout << "Has perdido, pero no pasa nada, intentalo de nuevo." << endl;
                break;
            }
        }
        else if (opcion == "esquivar" || opcion == "Esquivar" || opcion == "ESQUIVAR")
        {
            cout << "Tu enemigo intenta atacarte, pero lo esquivas." << endl;
        }
        else if (opcion == "estado" || opcion == "Estado" || opcion == "ESTADO")
        {
            status(heroe, villano);
        }
        else if (opcion == "huir" || opcion == "Huir" || opcion == "HUIR")
        {
            cout << "Huyes; en consecuencia, la ciudad ha sido destruida y miles de personas masacradas." << endl;
            break;
        }
        else
        {
            cout << "Opcion no valida. Intentalo de nuevo." << endl;
        }
    }
}

int main()
{
    Personaje heroe = { "Heroe", 2 };
    Personaje villano = { "Villano", 2 };

    introduccion();
    logicacombate(heroe, villano);
    return 0;
}

