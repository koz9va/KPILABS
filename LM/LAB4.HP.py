import math
from math import sqrt
import sys


def fun1(u):
    global count
    eds=10
    R=1.8e3
    i0=6e-9
    m=1.8
    ft=26e-3
    id = i0 * (math.exp(u / (m * ft)) - 1)
    count += 1
    y = -eds + u + R * id;

    return y
def fun2(x):
    global count
    count += 1
    return x*x-3

def bisection(func, a, b, eps):
    i=0
    c = func(a) / (abs(func(a)))
    while True:
        xi = (a+b) / 2
        i += 1
        f1=func(xi)
        print('i = '+ str(i) ,str(xi)+' '+str(f1))
        if (c * f1 < 0 ):
            b = xi
        else:
            a = xi
        if (abs(b-a) <= eps * (abs(b+a) / 2)):
            return (a+b) / 2
count=0
Result1 = bisection(fun1, 0.6, 1, 1e-6)
print('count = ' + str(count) + ' ' + 'Result1 = ' + str(Result1) + '\n\n')

def newt(func, x0, xt, eps):
    i = 0
     # машинный Эпсилон для значений с плавающей точкой
    h = sqrt(sys.float_info.epsilon) * max(abs(x0), abs(xt))
    fx0 = func(x0)
    x1 = x0 - (fx0 * h) / (func(x0+h) - fx0)

    while True:
        x0 = x1
        h = sqrt(sys.float_info.epsilon) * max(abs(x0), abs(xt))
        fx0 = func(x0)
        x1 = x0 - (fx0 * h) / (func(x0 + h) - fx0)
        i += 1
        print('i = '+ str(i) , str(x1) + ' ' + str(fx0))
        if abs(x1-x0)/x0 < eps * x0:
            break
    return x1

count=0
Result2 = newt(fun1, 1, 2, 1e-6)
print('count = ' + str(count) + ' ' + 'Result2 = ' + str(Result2) + '\n\n')

def secant(func, x1, x2, eps):
    error = 1
    i= 0
    funcx1=func(x1)
    while error > eps:
        funcx2=func(x2)

        x_new = x2 - (funcx2 * (x2 - x1)) / (funcx2 - funcx1)
        error = abs(x_new - x2)
        x1 = x2
        x2 = x_new
        i += 1
        print(f'i = {i} {x2} {funcx1}')
        funcx1=funcx2
    return x2
count = 0
Result3 = secant(fun1, 0.6, 1, 1e-6)
print('count = ' + str(count), 'Result3 = ' + str(Result3))