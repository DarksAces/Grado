my_list = [1, 2, 4, 4, 1, 4, 2, 6, 2, 9]

my_list2 = []

for i in my_list:
    if i not in my_list2:
        my_list2.append(i)

print("The list with unique elements only:")
print(my_list2)
