a = [[1, 2 , 3, 4], [5, 6], [7, 8, 9]]
long_fila=[]

for i in range(len(a)):
    long_fila.append(len(a[i]))

long_larga=max(long_fila)

num_columnas=long_larga

suma_columnas=[]

for i in range(num_columnas):
    suma_columnas.append(0)

for i in range(len(a)):
    for j in range(len(a[i])):
        suma_columnas[j]+=a[i] [j]

for j in range(len(suma_columnas)):
    print ("Suma de la columna",j,":",suma_columnas[j])

