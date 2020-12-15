import sys
from math import *
def test1(X,Y):
    Y[0] = exp(X[0]) + cos(X[1]) - 3
    Y[1] = cos(X[0]) + exp(X[1]) - 4
def test(UA,Y):
    global count
    ea = 200
    es1 = 3
    es2 = 70
    r = 50e3
    g1 = 10e-4
    g2 = 2e-4
    k1 = 3.5
    k2 = 4
    d1 = 0.05
    d2 = 0.01
    us2 = es2 - UA[0]
    us1 = -es1

    if (d1 * UA[0] + us1) / (1.0 + k1 * d1) < 0:
        dok1 = 0
    else:
        dok1 = pow((d1 * UA[0] + us1) / (1.0 + k1 * d1), 3. / 2)
        # 2 proverka
    if (us2 + d2 * UA[1]) / (1.0 + k2 * d2) < 0:
        dok2 = 0
    else:
        dok2 = pow((d2 * UA[1] + us2) / (1.0 + k2 * d2), 3. / 2)


    if d1 * ea + us1 <= 0:
        ia1 = 0.0
    else:
        ia1 = g1 * dok1
    if us2 + d2 * ea <= 0:
        ia2 = 0.0
    else:
        ia2 = g2 * dok2
    Y[0] = UA[1] - r * ia2 - ea + UA[0]
    Y[1] = ia1 - ia2
    count += 1

def QR(a,b,x):
    n = len(a)
    for j in range(n-1):
        alpha = 0
        for i in range(j,n):
            alpha += a[i][j]*a[i][j]

        if a[j][j] < 0:
            alpha = pow(alpha, 1./2)
        else:
            alpha = -pow(alpha, 1./2)
        k = 1 / (alpha * alpha - alpha * a[j][j])
        a[j][j] -= alpha

        for i in range(j+1, n):
            t = 0
            for l in range(j,n):
                t += a[l][j] * a[l][i]
            for l in range(j,n):
                a[l][i] -= k*a[l][j]*t
        t = 0
        for i in range(j,n):
            t += a[i][j] * b[i]
        for i in range(j,n):
            b[i] -= k*a[i][j] * t
        a[j][j] = alpha
        for i in range(n - 1, -1, -1):
            x[i] = b[i]
            for j in range(i + 1, n):
                x[i] -= a[i][j] * x[j]
            x[i] /= a[i][i]

def newt(func, X, eps):
    print('Метод Ньютона')
    p = 0
    dX = [float() for x in range(len(X))]
    Y = [float() for x in range(len(X))]
    Yp = [float() for x in range(len(X))]
    J = [[float() for y in range(len(X))]
         for x in range(len(X))]
    while True:
        func(X, Y)
        for j in range(len(X)):
            h = sqrt(sys.float_info.epsilon) * X[j]
            X[j] += h
            func(X, Yp)
            for i in range(len(X)):
                J[i][j] = (Yp[i] - Y[i]) / h
            X[j] -= h
        QR(J, Y, dX)
        nx = 0.0
        ndx = 0.0
        for i in range(len(X)):
            X[i] -= dX[i]
            nx += X[i] * X[i]  # накапливаем суму для того что бы найти вектор
            ndx += dX[i] * dX[i]
        p += 1
        print('iter = ' + str(p),str(X) + str(Y))
        if sqrt(ndx) < eps * sqrt(nx):
            break

def BROD(func, X, eps):
    print('Метод Бройдена')
    dX = [float() for x in range(len(X))]
    Y = [float() for x in range(len(X))]
    Yp = [float() for x in range(len(X))]
    J = [[float() for y in range(len(X))]
         for x in range(len(X))]
    JC = [[float() for y in range(len(X))]
          for x in range(len(X))]

    func(X, Y)
    for j in range(len(X)):
        p = 0
        h = sqrt(sys.float_info.epsilon) * X[j]
        X[j] += h
        func(X, Yp)
        for i in range(len(X)):
            J[i][j] = (Yp[i] - Y[i]) / h
        X[j] -= h
    while True:
        for i in range(len(X)):
            for j in range(len(X)):
                JC[i][j] = J[i][j]
        QR(JC, Y, dX)
        nx = 0.0
        ndx = 0.0
        for i in range(len(X)):
            X[i] -= dX[i]
            nx += X[i] * X[i]  # накапливаем суму для того что бы найти вектор
            ndx += dX[i] * dX[i]
        func(X, Y)
        for i in range(len(X)):
            for j in range(len(X)):
                J[i][j] -= (Y[i] * dX[j])/ ndx
        p += 1
        print('iter = ' + str(p),str(X) + str(Y))

        if sqrt(ndx) < eps * sqrt(nx):
            break
count = 0
X = [1, 130]

newt(test, X, 1e-6)
print( X, count)
count = 0
X = [1, 130]
BROD(test, X, 1e-6)
print(X, count)

