#include "Character.h"
#include <iostream>
#include <string>
using namespace std;


Character::Character()
{
	name = "";
	hp = 0;
	damage = 0;
}

Character::Character(string name, int pHp, int pDamage)
{
	//Se puede hacer con this como en Java o nombres distintos
	this->name = name;
	hp = pHp;
	damage = pDamage;
}

string Character::getName()
{
	return name;
}

int Character::getHP()
{
	return hp;
}

int Character::getDamage()
{
	return damage;
}

void Character::setName(string pName)
{
	name = pName;
}

void Character::setHP(int pHp)
{
	hp = pHp;
}

void Character::setDamage(int pDamage)
{
	damage = pDamage;
}

string Character::getStatus()
{
	return (" Character Status: \n   Name: " + name + "\n   HP: " + to_string(hp) + "\n Damage:" + to_string(damage) + "\n");
}
