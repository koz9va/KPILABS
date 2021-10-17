from sympy import *

g1 = 1
g2 = 1 / 3
Rn = 3
C = 2
w = symbols('w')
S = 1j * w

delta11 = g1 + S * C
delta12 = -S * C
delta21 = -S * C
delta22 = g2 + S * C

deltaab = delta21 * -1
deltaaa = delta22
delta = delta11 * delta22 - delta12 * delta21

Mod = sqrt(deltaab ** 2) / sqrt((Rn * delta) ** 2 + delta22 ** 2)
print("Expression Mod : {}".format(Mod))
limit_expr = limit(Mod, w, 0)
limit_max = limit(Mod, w, oo)
print("Expression for 0 : {}".format(limit_expr))
print("Expression for oo : {}".format(limit_max))

print(delta)


# x = symbols('x')
# expr = x / sqrt((1 / 36) + (x ** 2) * (1 / 36))
#
# print("Expression : {}".format(expr))
#

# print("Limit of the expression tends to 0 : {}".format(limit_expr))



# t = np.linspace(0, 10)
# U = t / np.sqrt(1 / 36 + t * t * 1 / 36)
#
# plt.plot(t, U)
# plt.grid()
# plt.show()
