import math
A = [
    [12, -6, -4],
    [-6, 15, -2],
    [-4, -2, 6]
]

B = [-22, 31, 14]
X = [0, 0, 0]
R = [0, 0, 0]


def QR(a, b, X):

    for j in range(len(b)-1):
        alf = 0
        for i in range(j, len(b)):
            alf += (a[i][j]) * (a[i][j])
        if a[j][j] < 0:
            alf = math.sqrt(alf)
        else:
            alf = -math.sqrt(alf)
        k = 1 / (alf * alf - alf * a[j][j])
        a[j][j] -= alf

        for i in range(j+1, len(b)):
            t = 0
            for l in range(j,len(b)):
                t += a[l][j] * a[l][i]
            for l in range (j,len(b)):
                a[l][i] -= k * a[l][j] * t
        t = 0
        for i in range(j,len(b)):
            t += a[i][j] * b[i]
        for i in range(j,len(b)):
            b[i] -= k * a[i][j] * t
        a[j][j] = alf

    for i in range(len(b) - 1, -1, -1):
        X[i] = b[i]
        for j in range(i + 1, len(b)):
            X[i] -= a[i][j] * X[j]
        X[i] /= a[i][i]

QR(A, B, X)
'''for i in X:
    print(i)'''




