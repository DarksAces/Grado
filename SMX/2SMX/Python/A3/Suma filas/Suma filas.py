a = [[1, 2 , 3, 4], [5, 6], [7, 8, 9]]
s = 0
for i  in range(len(a)):
    fila = a[i]
    sum_fila = 0
    sum_fila += s
    for j in range(len(a[i])):
        s += a[i][j]
    print ("Suma fila",i + 1,s - sum_fila)
