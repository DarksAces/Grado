Algoritmo MayorDeCuatroNumeros
	Definir a,b,c,d,mayor Como Entero
	// Pedir los cuatro n�meros
	Escribir 'Introduce el primer n�mero: '
	Leer a
	Escribir 'Introduce el segundo n�mero: '
	Leer b
	Escribir 'Introduce el tercer n�mero: '
	Leer c
	Escribir 'Introduce el cuarto n�mero: '
	Leer d
	// Comparar con el primer n�mero
	mayor <- a
	// Comparar con el segundo n�mero
	Si b>mayor Entonces
		mayor <- b
	FinSi
	// Comparar con el tercer n�mero
	Si c>mayor Entonces
		mayor <- c
	FinSi
	// Comparar con el cuarto n�mero
	Si d>mayor Entonces
		mayor <- d
	FinSi
	// Mostrar el mayor n�mero
	Escribir 'El n�mero m�s grande es: ',mayor
FinAlgoritmo

