import math
import copy

A = [
    [12, -6, -4],
    [-6, 18, -3],
    [-4, -3, 7]
]
A1 = copy.deepcopy(A)
A2 = copy.deepcopy(A)
A3 = copy.deepcopy(A)

B = [-16, 18, 18]
B1 = copy.deepcopy(B)
B2 = copy.deepcopy(B)
B3 = copy.deepcopy(B)

X = [0, 0, 0]
R = [0, 0, 0]


def LU(a, b, x):
    for j in range(len(b)):
        for i in range(j, len(b)):
            for k in range(0, j):
                a[i][j] -= a[i][k] * a[k][j]

        for i in range(j + 1, len(b)):
            for k in range(0, j):
                a[j][i] -= a[j][k] * a[k][i]
            a[j][i] /= a[j][j]

    for i in range(len(b)):
        x[i] = b[i]
        for j in range(0, i):
            x[i] -= a[i][j] * x[j]
        x[i] /= a[i][i]

    for i in range(len(b) - 1, -1, -1):
        for j in range(i + 1, len(b)):
            x[i] -= a[i][j] * x[j]
    return


def LL(a, b, x):
    for j in range(len(b)):
        for k in range(0, j):
            a[j][j] -= a[j][k] * a[j][k] * a[k][k]

        for i in range(j + 1, len(b)):
            for k in range(0, j):
                a[i][j] -= a[i][k] * a[k][k] * a[j][k]
            a[i][j] /= a[j][j]

    for i in range(len(b)):
        x[i] = B[i]
        for j in range(i):
            x[i] -= a[i][j] * x[j]

    for i in range(len(b)):
        x[i] /= a[i][i]
    for i in range(len(b) - 1, -1, -1):
        for j in range(i + 1, len(b)):
            x[i] -= a[j][i] * x[j]
    return


def QR(a, b, x):
    for j in range(len(b) - 1):
        alf = 0
        for i in range(j, len(b)):
            alf += (a[i][j]) * (a[i][j])
        if a[j][j] < 0:
            alf = math.sqrt(alf)
        else:
            alf = -math.sqrt(alf)
        k = 1 / (alf * alf - alf * a[j][j])
        a[j][j] -= alf

        for i in range(j + 1, len(b)):
            t = 0.0
            for l in range(j, len(b)):
                t += a[l][j] * a[l][i]
            for l in range(j, len(b)):
                a[l][i] -= k * a[l][j] * t
        t = 0
        for i in range(j, len(b)):
            t += a[i][j] * b[i]
        for i in range(j, len(b)):
            b[i] -= k * a[i][j] * t
        a[j][j] = alf

    for i in range(len(b) - 1, -1, -1):
        x[i] = b[i]
        for j in range(i + 1, len(b)):
            x[i] -= a[i][j] * x[j]
        x[i] /= a[i][i]
    return

def MBV(a, b, R, X):
    for i in range(0, len(b)):
        R[i] = 0
        for j in range(0, len(b)):
            R[i] += a[i][j] * X[j]
        R[i] -= b[i]


def Norm(R, b):
    sum = 0
    for i in range(len(b)):
        sum += R[i] ** 2
    return math.sqrt(sum)

f = open('text.txt', 'w')

f.write('<LU-Factorization>: ' + '\n' )
LU(A1, B1, X)
for i in X:
    f.write(str(i) + ' ' + '\n')
f.write('- Vector:' + '\n')
MBV(A, B, R, X)
n = Norm(R, B)
for i in R:
    f.write(str(i) + ' ' + '\n')
f.write("- Norm: " + str(n) + '\n')


f.write('<LL-Factorization>: ' + '\n' )
LL(A2, B2, X)
for i in X:
    f.write(str(i) + ' ' + '\n')
f.write('- Vector:' + '\n')
MBV(A, B, R, X)
n = Norm(R, B)
for i in R:
    f.write(str(i) + ' ' + '\n')
f.write("- Norm: " + str(n) + '\n')


f.write('<QR-Factorization>: ' + '\n' )
QR(A3, B3, X)
for i in X:
    f.write(str(i) + ' ' + '\n')
f.write('- Vector:' + '\n')
MBV(A, B, R, X)
n = Norm(R, B)
for i in R:
    f.write(str(i) + ' ' + '\n')
f.write("- Norm: " + str(n) + '\n')

