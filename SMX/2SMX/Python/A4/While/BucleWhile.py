num_correcto = False

while num_correcto == False:
    try:
        value = int(input("Enter a natural number: "))
        print ("The reciprocal of", value, "is", 1/value)
        num_correcto = True
    except:
        print("Invalid format" )