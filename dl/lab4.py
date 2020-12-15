import math
import sys


def f1(x):
    global count
    count += 1
    return x * x - 3

def f2(x):
    
    global count
    count += 1
    return 4-3*x-math.exp(x)-1


def bisection(func, a, b, eps):
    i = int(0)
    fa = func(a)
    fabfa = fa / math.fabs(fa)
    while True:
        xi = (a + b) / 2
        fx = func(xi)
        if fabfa * fx < 0:
            b = xi
        else:
            a = xi
        i += int(1)
        f.write(f'Number of iteration: {str(i)} A = {str(a)} B = {str(b)} f(x) = {str(fx)}\n')
        if math.fabs(b - a) < eps * math.fabs(a):
            break
    return (a + b) / 2


def newton(func, a, eps):
    i = int(0)
    xt = float(a)
    x1 = float(a)
    fx1 = float(func(a))

    while True:
        x = x1
        fx = fx1
        if math.fabs(x) >= math.fabs(xt):
            h = math.sqrt(sys.float_info.epsilon) * x
        else:
            h = math.sqrt(sys.float_info.epsilon) * xt
        x1 = x - h * fx / (func(x + h) - fx)
        fx1 = func(x1)
        i += int(1)
        f.write(f'Number of iteration: {str(i)} X = {str(x)} f(x) = {str(fx)} \n')
        if math.fabs(x1 - x) < eps:
            break
    return (x1 + x) / 2


def sichnyh(func, x0, eps):
    i = int(0)
    x1 = x0
    hn = math.sqrt(sys.float_info.epsilon) * x0
    deriv1 = float(func(x1))
    x2 = x1 - (hn * deriv1) / (func(x1 + hn) - deriv1)
    deriv2 = float(func(x2))
    i += int(1)
    f.write(f'Number of iteration: {str(i)}  X = {str(x2)} f(x) = {str(deriv2)} \n')
    while True:
        x0 = x1
        x1 = x2
        deriv0 = deriv1
        deriv1 = deriv2
        x2 = x1 - ((x1 - x0) * deriv1) / (deriv1 - deriv0)
        deriv2 = func(x2)
        i += int(1)
        f.write(f'Number of iteration: {str(i)}  X = {str(x2)} f(x) = {str(deriv2)} \n')
        if math.fabs(x2 - x1) < eps * math.fabs(x1):
            break
    return x2


f = open('text.txt', 'w')

count = 0
x1 = bisection(f2, -5, 5, 1e-2)
f.write(f'<----- Number of function calls: {str(count)} x1 = {str(x1)}  ----->\n\n')
count = 0
x2 = newton(f2, 1, 1e-2)
f.write(f'<----- Number of function calls: {str(count)} x1 = {str(x2)}  ----->\n\n')
count = 0
x3 = sichnyh(f2, 1, 1e-2)
f.write(f'<----- Number of function calls: {str(count)} x1 = {str(x3)}  ----->\n\n')



