shoplist = ['яблоки', 'манго', 'морковь', 'бананы']
name = 'swaroop'

#Операция индексирования
print('El 0 -', shoplist[0])
print('El 1 -', shoplist[1])
print('El 2 -', shoplist[2])
print('El 3 -', shoplist[3])
print('El -1 -', shoplist[-1])
print('El -2 -', shoplist[-2])
print('Symb 0 -', name[0])

#Вырезка из списка
print('El from 1 to 3:', shoplist[1:3])
print('El from 2 to the end:', shoplist[2:])
print('El from 1 to -1:', shoplist[1:-1])
print('El from the begin to the end:', shoplist[:])

#Вырезка из строки
print('Symb from 1 to 3:', name[1:3])
print('Symb from 2 to the end:', name[2:])
print('Symb from 1 to -1:', name[1:-1])
print('Symb from the begin to the end', name[:])