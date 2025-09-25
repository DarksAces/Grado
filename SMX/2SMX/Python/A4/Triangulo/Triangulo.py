def is_a_triangle(a, b, c):
    return a + b > c and b + c > a and c + a > b

def is_a_right_triangle(a, b, c):
    if not is_a_triangle(a, b, c):
        return False
    if c > a and c > b:
        return c ** 2 == a ** 2 + b ** 2
    if a > b and a > c:
        return a ** 2 == b ** 2 + c ** 2
    if b > c and b > a:
        return b ** 2 == a ** 2 + c ** 2
        
a = float(input('Enter the first side\'s length: '))
b = float(input('Enter the second side\'s length: '))
c = float(input('Enter the third side\'s length: '))

if is_a_triangle(a, b, c):
    if is_a_right_triangle(a, b, c):
        print('Sí, puede ser un triángulo y es un triángulo rectángulo.')
    else:
        print('Sí, puede ser un triángulo, pero no es un triángulo rectángulo.')
else:
    print('No, no puede ser un triángulo.')