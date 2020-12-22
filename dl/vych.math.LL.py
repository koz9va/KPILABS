A = [
    [12, -6, -4],
    [-6, 18, -3],
    [-4, -3, 7]
]

B = [-16, 18, 18]
X = [0, 0, 0]




def LL(a, b, X):
    for j in range(len(b)):
        for k in range(0,j):
            a[j][j] -= a[j][k] * a[j][k] * a[k][k]

        for i in range (j+1,len(b)):
            for k in range(0,j):
                a[i][j] -= a[i][k] * a[k][k] * a[j][k]
            a[i][j] /= a[j][j]

    for i in range (len(b)):
        X[i] = B[i]
        for j in range (i):
            X[i] -= a[i][j] * X[j]

    for i in range (len(b)):
        X[i] /= a[i][i]
    for i in range (len(b) - 1, -1, -1):
        for j in range (i+1, len(b)):
            X[i] -= a[j][i] * X[j]

LL(A,B,X)
for i in X:
    print(i)


