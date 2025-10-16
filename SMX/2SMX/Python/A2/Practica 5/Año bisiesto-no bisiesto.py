year = int(input("Enter a year: "))
if year <= 1582:
    print("No esta en el calendario gregoriano")
    
else:
    if year % 4 != 0:
        print("Año comun")
 
    elif year % 100 != 0:
        print("Año bisiesto")
 
    elif year % 400 != 0:
        print("Año comun")
 
    else:
        print("Año bisiesto")
