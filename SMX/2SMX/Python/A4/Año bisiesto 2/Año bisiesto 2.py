def is_year_leap(year):


    if year % 4 != 0:
        return False  # Año común

    elif year % 100 != 0:
        return True  # Año bisiesto

    elif year % 400 != 0:
        return False  # Año común

    else:
        return True  # Año bisiesto

test_data = [1900, 2000, 2016, 1987]
test_results = [False, True, True, False]

for i in range(len(test_data)):
    yr = test_data[i]
    print(yr, "->", end="")
    result = is_year_leap(yr)
    if result == test_results[i]:
        print("OK")
    else:
        print("Failed")
