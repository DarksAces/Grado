Algoritmo MayorDeCuatroNumeros
	Definir a,b,c,d,mayor Como Entero
	// Pedir los cuatro n�meros
	Escribir 'Introduce el primer n�mero: '
	Leer a
	Escribir 'Introduce el segundo n�mero: '
	Leer b
	Escribir 'Introduce el tercer n�mero: '
	Leer c
	// Comparar con el primer n�mero
	Escribir 'Introduce el cuarto n�mero: '
	Leer d
	a <- max
	// Comparar con el segundo n�mero
	Escribir max
	Si b>max Entonces
		b <- max
		Escribir max
	FinSi
	// Comparar con el tercer n�mero
	Si c>max Entonces
		c <- max
		Escribir max
	FinSi
	// Comparar con el cuarto n�mero
	Si d>max Entonces
		d <- max
		Escribir max
	FinSi
	// Mostrar el mayor n�mero
	Escribir 'El n�mero m�s grande es: ',max
FinAlgoritmo

