temperatura = int(input("Que temperatura hace hoy? "))
if  temperatura < 10:
    print("Ponte un abrigo grande")
elif temperatura >=10 and temperatura <=20:
    print("Ponte una chaqueta")
elif temperatura > 20:
    print("Ponte ropa ligera")