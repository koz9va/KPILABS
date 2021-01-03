from math import pow
A=[
    [12,-6,-4],
    [-6,15,-2],
    [-4,-2,6]
]

AA=[
    [12, -6, -4],
    [-6, 15, -2],
    [-4, -2, 6]
]
AAA=[
    [12, -6, -4],
    [-6, 15, -2],
    [-4, -2, 6]
]
AAAA=[
    [12, -6, -4],
    [-6, 15, -2],
    [-4, -2, 6]
]
B= [-22, 31, 14]
BB= [-22, 31, 14]
BBB= [-22, 31, 14]
BBBB= [-22, 31, 14]
X= [0, 0, 0]
F= [0,0,0]


def normL2(a,f):
    n=len(a)
    h=0
    for i in range(0,n):
        h += f[i] * f[i]
    h= pow(h, 1./2) #Евклідова норма
    print('norma', h)

def vector(a,b,x,f):
    n=len(a)
    for i in range(0,n):

        f[i] = b[i]
        for j in range(0,n):

            f[i] -= a[j][i] * x[j] #F(R)  += A*X
        print(f[i],"вектор")              #R= B-AX

def LU(a, b, x):
    n = len(a)
    for j in range(n):
        for i in range(j, n):
            for k in range(j):# -1
                a[i][j] -= a[i][k] * a[k][j]
        for i in range(j + 1, n):
            for k in range(j):
                a[j][i] -= a[j][k] * a[k][i]
            a[j][i] /= a[j][j]
    for i in range(n):
        x[i] = b[i]
        for j in range(0, i):
            x[i] -= a[i][j] * x[j]
        x[i] /= a[i][i]
    for i in range(n - 1,-1, -1):
        for j in range(i + 1, n):
            x[i] -= a[i][j] * x[j]

def QR(a,b,x):

    n=len(a)
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
        t=0
        for i in range(j,n):
            t += a[i][j] * b[i]
        for i in range(j,n):
            b[i] -= k*a[i][j] * t
        a[j][j] = alpha

#RX-B
    for i in range(n-1,-1,-1):
        x[i]=b[i]
        for j in range(i + 1, n):
            x[i] -= a[i][j]*x[j]
        x[i] /= a[i][i]


def LL(a, b, x):
    n = len(a)
    for j in range(n):
        for k in range(j):
            #
            a[j][j] -= a[j][k] * a[j][k] * a[k][k]
        for i in range(j + 1, n):
            for k in range(j):
                a[i][j] -= a[i][k] * a[k][k] * a[j][k]
            a[i][j] /= a[j][j]
    for i in range(n):
        x[i] = b[i]
        for j in range(i):
            x[i] -= a[i][j] * x[j]
    for i in range(n):
        x[i] /= a[i][i]
    for i in range(n - 1, -1, -1):
        for j in range(i + 1, n):
            x[i] -= a[j][i] * x[j]

if __name__ == '__main__':

    LU(A, B, X)
    for i in X:
        print(i)
    vector(AAAA, BBBB, X, F)
    normL2(AAAA,F)
    print(A)

    QR(AA, BB, X)
    for i in X:
        print(i)
    vector(AAAA, BBBB, X, F)
    normL2(AAAA,F)
    print(AA)

    LL(AAA, BBB, X)
    for i in X:
        print(i)
    print(AAA)

    vector(AAAA, BBBB, X, F)
    normL2(AAAA,F)
