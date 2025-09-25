Algoritmo MayorDeCuatroNumeros
	Definir a,b,c,d,mayor Como Entero
	// Pedir los cuatro números
	Escribir 'Introduce el primer número: '
	Leer a
	Escribir 'Introduce el segundo número: '
	Leer b
	Escribir 'Introduce el tercer número: '
	Leer c
	// Comparar con el primer número
	Escribir 'Introduce el cuarto número: '
	Leer d
	a <- max
	// Comparar con el segundo número
	Escribir max
	Si b>max Entonces
		b <- max
		Escribir max
	FinSi
	// Comparar con el tercer número
	Si c>max Entonces
		c <- max
		Escribir max
	FinSi
	// Comparar con el cuarto número
	Si d>max Entonces
		d <- max
		Escribir max
	FinSi
	// Mostrar el mayor número
	Escribir 'El número más grande es: ',max
FinAlgoritmo

