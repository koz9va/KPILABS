import math
import sys


def f1(x):
    global count
    count += 1
    return 2 * math.log(x*x - x + 1) - x

def f2(x):
    
    global count
    count += 1
    return 3 * math.log(x * x - x) - 3 * x + 7


def bisection(func, a, b, eps):
    i = int(1)

    while True:
        f.write(f'<----Number of iteration: {str(i)} ----> \nA = {str(a)} B = {str(b)}  \n')
        xi = (a + b) / 2
        f.write(f'x{str(i)} = ({str(a)} + {str(b)}) / 2 = {str(xi)}\n')
        fx = func(xi)
        fa = func(a)
        fabfa = fa / math.fabs(fa)
        if fabfa * fx < 0:
            b = xi
        else:
            a = xi

        f.write(f'f(x{str(i)}) = {str(fx)}\n')

        if b == xi:
            f.write(f'f(a) * f(x{str(i)}) = {str(fa)} * {str(fx)} < 0\n')
            f.write(f'b = x{str(i)} = {str(xi)}\n')
        else:
            f.write(f'f(a) * f(x{str(i)}) = {str(fa)} * {str(fx)} > 0\n')
            f.write(f'a = x{str(i)} = {str(xi)}\n')

        if math.fabs(b - a) > eps * math.fabs(a):
            f.write(f'|{str(b)} - {str(a)}| >= eps * |(a + b) / 2| \n|{math.fabs(b - a)}| > {str(eps)} * |({math.fabs(a + b)})/ 2|\n')
        i += int(1)

        if math.fabs(b - a) < eps * math.fabs(a):
            f.write(
                f'|{str(b)} - {str(a)}| < eps * |(a + b) / 2| \n|{math.fabs(b - a)}| < {str(eps)} * |({math.fabs(a + b)})/ 2|\n')
            break
    f.write(f'x = (a + b)/2 = ({str(a)} + {str(b)})/2 = {(a+b)/2}\n\n')
    return (a+b)/2


def newton(func, a, eps):
    i = int(1)
    xt = float(a)
    x1 = float(a)
    fx1 = float(func(a))
    while True:
        x = x1
        fx = fx1
        f.write(f'Number of iteration: {str(i)} X = {str(x)} f(x) = {str(fx)} \n')

        if math.fabs(x) >= math.fabs(xt):
            h = math.sqrt(sys.float_info.epsilon) * x
        else:
            h = math.sqrt(sys.float_info.epsilon) * xt
        x1 = x - h * fx / (func(x + h) - fx)
        fx1 = func(x1)
        f.write(f'x{str(i)} = x{str(i - 1)} - f(x{str(i - 1)})/f\'(x{str(i - 1)}) = {str(x)} - {str((h * fx))}/{str((func(x + h) - fx))} = {x - h * fx / (func(x + h) - fx)}\n')
        f.write(f'|x{str(i)} - x{str(i-1)}| = {x1 - x}\n')
        f.write(f'eps * |x{str(i-1)}| = {eps * x}\n')
        if math.fabs(x1 - x) > eps:
            f.write(f'|x{str(i)} - x{str(i-1)}| > eps * |x{str(i-1)}|\n')
        i += int(1)
        if math.fabs(x1 - x) < eps:
            f.write(f'|x{str(i)} - x{str(i-1)}| < eps * |x{str(i-1)}|\n')
            break
    return (x1 + x) / 2


def sichnyh(func, x0, eps):
    i = int(1)
    x1 = x0
    hn = math.sqrt(sys.float_info.epsilon) * x0
    deriv1 = float(func(x1))
    x2 = x1 - (hn * deriv1) / (func(x1 + hn) - deriv1)
    deriv2 = float(func(x2))
    f.write(f'Number of iteration: {str(i)}  X = {str(x2)} f(x) = {str(deriv2)} \n')
    f.write(f'x0 = x{i-1} - f(x{i-1})/f\'(x{i-1}) = {x1} - {(hn * deriv1) / (func(x1 + hn) - deriv1)} = {x2}\n')
    f.write(f'|x{str(i)} - x{str(i-1)}| = {math.fabs(x2 - x1)}\n')
    f.write(f'eps * |x{str(i - 1)}| = {eps * x0}\n')
    i += int(1)

    while True:
        x0 = x1
        x1 = x2
        deriv0 = deriv1
        deriv1 = deriv2
        x2 = x1 - ((x1 - x0) * deriv1) / (deriv1 - deriv0)
        deriv2 = func(x2)

        f.write(f'Number of iteration: {str(i)}  X = {str(x2)} f(x) = {str(deriv2)} \n')
        f.write(f'x{i} = x{i-1} - f(x{i-1})*(x{i-1} - x{i-2})/(f(x{i-1}) - f(x{i-2})) = {x1} - {((x1 - x0) * deriv1) / (deriv1 - deriv0)} = {x2}\n')
        f.write(f'|x{str(i)} - x{str(i-1)}| = {math.fabs(x2 - x1)}\n')
        f.write(f'eps * |x{str(i - 1)}| = {eps * x1}\n')
        if math.fabs(x2 - x1) > eps * math.fabs(x1):
            f.write(f'|x{i} - x{i-1}| > eps * |x{i-1}|\n')
        i += int(1)
        if math.fabs(x2 - x1) < eps * math.fabs(x1):
            f.write(f'|x{i-1} - x{i - 2}| < eps * |x{i - 2}|\n\n')
            break
    return x2


f = open('text.txt', 'w')

count = 0
x1 = bisection(f1, 2, 6, 1e-2)
f.write(f'<----- x = {str(x1)}  ----->\n\n')
count = 0
x2 = newton(f1, 1, 1e-2)
f.write(f'<----- x = {str(x2)}  ----->\n\n')
count = 0
x3 = sichnyh(f1, 1, 1e-2)
f.write(f'<----- x = {str(x3)}  ----->\n\n')



