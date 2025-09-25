def is_year_leap(year):
    if year % 4 != 0:
        return False  
    elif year % 100 != 0:
        return True 
    elif year % 400 != 0:
        return False 
    else:
        return True  

def days_in_month(year, month):
    month_lengths = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]

    if month < 1 or month > 12:
        return None  

    if month == 2:
        if is_year_leap(year):
            return 29
        else:
            return 28
    elif month in [4, 6, 9, 11]:
        return 30
    else:
        return month_lengths[month]


test_years = [1900, 2000, 2016, 1987]
test_months = [2, 2, 1, 11]
test_results = [28, 29, 31, 30]

for i in range(len(test_years)):
    yr = test_years[i]
    mo = test_months[i]
    result = days_in_month(yr, mo)
    if result == test_results[i]:
        print(f"{yr}, {mo} -> OK")
    else:
        print(f"{yr}, {mo} -> Failed")
