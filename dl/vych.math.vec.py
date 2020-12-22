A = [
    [12, -6, -4],
    [-6, 18, -3],
    [-4, -3, 7]
]

B = [-16, 18, 18]
X = [1, 2, 4]
R = [0, 0, 0]

def MBV(a, b, r, X):
    for i in range(0, len(b)):
        r[i] = 0
        for j in range(0,len(b)):
            r[i] += a[i][j] * X[j]
        r[i] -= b[i]

def Norm(R, b):
    sum = 0
    for i in range(len(b)):
        sum += R[i] ** 2
    return pow(sum, .5)

MBV(A, B, R, X)
Norm(R,B)
for i in R:
    print(i)