import sys
from math import *
from QR import QR


def rgr(X, Y):
    Y[0] = exp(2 * X[0]) + X[1] - 2
    Y[1] = exp(X[0]) - 2 * X[1] - 1


def lab(X, Y):
    global count
    e = 6.0
    r = 1.5e3
    i01 = 2.0e-9
    i02 = 5.0e-9
    m1 = 1.5
    m2 = 1.7
    phi = 26.0e-3

    id1 = i01 * (exp(X[0] / (m1 * phi)) - 1)
    id2 = i02 * (exp(X[1] / (m2 * phi)) - 1)

    Y[0] = id1 - id2
    Y[1] = e - X[0] - X[1] - id1 * r

    count += 1


def newton(fun, X, eps):
    f.write(f'<----- Newton ----->\n')
    k = int(0)
    dX = [float() for x in range(len(X))]
    Y = [float() for x in range(len(X))]
    Yp = [float() for x in range(len(X))]
    J = [[float() for y in range(len(X))]
         for x in range(len(X))]

    while True:
        fun(X, Y)
        for j in range(len(X)):
            h = sqrt(sys.float_info.epsilon) * X[j]
            X[j] += h
            fun(X, Yp)
            for i in range(len(X)):
                J[i][j] = (Yp[i] - Y[i]) / h
            X[j] -= h
        QR(J, Y, dX)
        nx = 0.0
        ndx = 0.0
        for i in range(len(X)):
            X[i] -= dX[i]
            ndx += dX[i] * dX[i]
            nx += X[i] * X[i]
        k += int(1)
        f.write(f'Number of iteration: {str(k)}; X = {X}; Y = {Y} \n')
        if sqrt(ndx) < eps * sqrt(nx):
            break


def broiden(fun, X, eps):
    f.write(f'<----- Broiden ----->\n')
    k = 0
    dX = [float() for x in range(len(X))]
    Y = [float() for x in range(len(X))]
    Yp = [float() for x in range(len(X))]
    J = [[float() for y in range(len(X))]
         for x in range(len(X))]
    J1 = [[float() for y in range(len(X))]
          for x in range(len(X))]

    fun(X, Y)
    for j in range(len(X)):
        h = sqrt(sys.float_info.epsilon) * X[j]
        X[j] += h
        fun(X, Yp)
        for i in range(len(X)):
            J[i][j] = (Yp[i] - Y[i]) / h
        X[j] -= h
    while True:
        for i in range(len(X)):
            for j in range(len(X)):
                J1[i][j] = J[i][j]
        QR(J1, Y, dX)
        nx = 0.0
        ndx = 0.0
        for i in range(len(X)):
            X[i] -= dX[i]
            nx += X[i] * X[i]
            ndx += dX[i] * dX[i]
        fun(X, Y)
        for i in range(len(X)):
            for j in range(len(X)):
                J[i][j] -= (Y[i] * dX[j]) / ndx
        k += int(1)
        f.write(f'Number of iteration: {str(k)}; X = {X}; Y = {Y} \n')
        if sqrt(ndx) < eps * sqrt(nx):
            break


f = open('sne.txt', 'w')

count = 0
X = [0.6, 0.6]
newton(lab, X, 1e-6)

f.write(f'<----- Number of function calls: {str(count)}; X = {str(X)}  ----->\n\n')
count = 0
X = [0.6, 0.6]
broiden(lab, X, 1e-6)
f.write(f'<----- Number of function calls: {str(count)}; X = {str(X)}  ----->\n\n')
