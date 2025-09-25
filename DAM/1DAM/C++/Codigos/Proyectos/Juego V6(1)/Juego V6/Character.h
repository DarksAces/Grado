#pragma once
#include <iostream>
using namespace std;


class Character
{


	// Atributos
private:
	string name;
	int hp;
	int damage;


	// Prototipos
public:


	// Constructor sin parametros
	Character();


	// Constructor con parametros
	Character(string name, int pHp, int pDamage);


	// Getters
	string getName();
	int getHP();
	int getDamage();


	//Setters
	void setName(string pName);
	void setHP(int pHp);
	void setDamage(int pDamage);


	//Metodos Propios
	string getStatus();
};

