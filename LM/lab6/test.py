from math import log, exp
from my_qrlull import LL

def table(Uf, C_one, sense):

    i = 0
    c0 = float(C_one[0])
    while Uf[i] < sense:
        del Uf[i]
        del C_one[i]
        ++i

    for j in range(len(Uf)):
        Uf[j] = log(Uf[j])
        C_one[j] = log(c0 / C_one[j])

def amaxim(U, C, X, n):
    n = n+1
    m = [[float() for y in range(n)]
         for x in range(n)]
    L = [float() for x in range(len(X))]

    for i in range(n):
        for k in range(len(U)):
            stepen = 1.0
            for f in range(i):
                stepen *= C[k]
            L[i] += stepen * U[k]

        for j in range(n):
            for k in range(len(U)):
                stepen = 1.0
                for f in range(0, i + j):
                    stepen *= C[k]
                m[i][j] += stepen
    LL(m, L, X)
if __name__ == '__main__':
    f = open("omurga.txt")

    u = list(map(float, f.readline().split(" ")))
    c = list(map(float, f.readline().split(" ")))
    # l = [line.strip() for line in f ]
    f.close()


    table(u, c, 6.0)
    h = [float() for x in range(len(u))]
    amaxim(u, c, h, 1)
    h[0] = exp(h[0])
    h[1] = 1.0 / h[1]

    print('фи = ' + str(h[0]) + ' N = ' + str(h[1]))



