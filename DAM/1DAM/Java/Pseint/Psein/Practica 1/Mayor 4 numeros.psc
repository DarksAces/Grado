Algoritmo MayorDeCuatroNumeros
	Definir a,b,c,d,mayor Como Entero
	// Pedir los cuatro números
	Escribir 'Introduce el primer número: '
	Leer a
	Escribir 'Introduce el segundo número: '
	Leer b
	Escribir 'Introduce el tercer número: '
	Leer c
	Escribir 'Introduce el cuarto número: '
	Leer d
	// Comparar con el primer número
	mayor <- a
	// Comparar con el segundo número
	Si b>mayor Entonces
		mayor <- b
	FinSi
	// Comparar con el tercer número
	Si c>mayor Entonces
		mayor <- c
	FinSi
	// Comparar con el cuarto número
	Si d>mayor Entonces
		mayor <- d
	FinSi
	// Mostrar el mayor número
	Escribir 'El número más grande es: ',mayor
FinAlgoritmo

