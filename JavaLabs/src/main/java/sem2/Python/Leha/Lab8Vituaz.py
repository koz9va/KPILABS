import time
from math import pi, atan2

import matplotlib.pyplot as plt
import numpy as np
from numpy.lib import math

r = 4e3
c = 5e-6


def calc_ku(c_p, w_p, r_p):
    return 1 / (
        np.sqrt((1 - 5 * (w_p * c_p * r_p) ** 2) ** 2 + (w_p * c_p * r_p) ** 2 * (6 - (w_p * c_p * r_p) ** 2) ** 2))


w = np.sqrt(6) / (c * r)
Fi = (-pi - atan2((w * c * r * ((w * c * r) ** 2 - 5)), (6 * (w * c * r) ** 2 - 1))) * 180 / pi
wQ = w / (2 * pi)
print("Omega = " + str(w))
print("fi = " + str(wQ))
print("Ku = " + str(calc_ku(c, w, r)))
print("Fi = " + str(Fi))

f = np.linspace(1, 50000, 25000)

Fi = []
for a in f:
    Fi.append((-pi - math.atan2((a * c * r * ((a * c * r) ** 2 - 5)), (6 * (a * c * r) ** 2 - 1))) * 180 / pi)
plt.plot(np.log10(f), Fi)
plt.title('Fi')
plt.grid()
plt.savefig('Fi1.png', dpi=500, bbox_inches='tight')
plt.show()


plt.plot(np.log10(f), calc_ku(c, f, r))
plt.title('Ku')
plt.grid()
plt.savefig('Ku1.png', dpi=500, bbox_inches='tight')
plt.show()



def calc_ku2(c_p, w_p, r_p):
    return ((w_p * c_p * r_p) ** 3) / (
        np.sqrt((6 * (w_p * c_p * r_p) ** 2 - 1) ** 2 + (w_p * c_p * r_p) ** 2 * ((w_p * c_p * r_p) ** 2 - 5) ** 2))


w = 1 / (c * r * np.sqrt(6))
Fi = -3 * pi / 2 - atan2((w * c * r * ((w * c * r) ** 2 - 5)), (6 * (w * c * r) ** 2 - 1))
wQ = w / (2 * pi)
print("Omega2 = " + str(w))
print("fi2 = " + str(wQ))
print("Ku2 = " + str(calc_ku2(c, w, r)))
print("Fi2 = " + str(Fi))

f = np.linspace(1, 50000, 25000)

Fi = []
for a in f:
    Fi.append((-3 * pi / 2 - atan2((a * c * r * ((a * c * r) ** 2 - 5)), (6 * (a * c * r) ** 2 - 1))) * 180 / pi)
plt.plot(np.log10(f), Fi)
plt.title('Fi2')
plt.grid()
plt.savefig('Fi2.png', dpi=500, bbox_inches='tight')
plt.show()


plt.plot(np.log10(f), calc_ku2(c, f, r))
plt.title('Ku2')
plt.grid()
plt.savefig('Ku2.png', dpi=500, bbox_inches='tight')
plt.show()



w = 1 / (c * r) + 1
wQ = w / (2 * pi)


def calc_ku3(c_p, w_p, r_p):
    return (w_p * c_p * r_p) / (np.sqrt((1 - (w_p * c_p * r_p) ** 2) ** 2 + 9 * w_p ** 2 * c_p ** 2 * r_p ** 2))


Fi = (pi / 2 - atan2((3 * w * r * c), (1 - (w * r * c) ** 2)))
print("Omega3 = " + str(w))
print("fi3 = " + str(wQ))
print("Ku3 = " + str(calc_ku3(c, w, r)))
print("Fi3 = " + str(Fi))

f = np.linspace(1, 50000, 25000)

Fi = []
for a in f:
    Fi.append((pi / 2 - atan2((3 * a * r * c), (1 - (a * r * c) ** 2))) * 180 / pi)
plt.plot(np.log10(f), Fi)
plt.title('Fi3')
plt.grid()
plt.savefig('Fi3.png', dpi=500, bbox_inches='tight')
plt.show()



plt.plot(np.log10(f), calc_ku3(c, f, r))
plt.title('Ku3')
plt.grid()
plt.savefig('Ku3.png', dpi=500, bbox_inches='tight')
plt.show()




def calc_ku4(c_p, w_p, r_p):
    return (np.sqrt((1 - (w_p * c_p * r_p) ** 2) ** 2 + (w_p * c_p * r_p) * (1 - (w_p * c_p * r_p) ** 2) ** 2)) / \
           (np.sqrt((1 - 5 * (w_p * c_p * r_p) ** 2) ** 2 + (w_p * c_p * r_p) * (5 - (w_p * c_p * r_p) ** 2) ** 2))


def calc_fi4(c_p, r_p, w_p):
    return atan2(((w_p * c_p * r_p) * (5 - (w_p * c_p * r_p) ** 2)), 1 - (w_p * c_p * r_p) ** 2) - atan2(
        (w_p * c_p * r_p) * (5 - (w_p * c_p * r_p) ** 2), (1 - 5 * (w_p * c_p * r_p) ** 2))


def calc_fi5(c_p, r_p, w_p):
    return atan2(((w_p * c_p * r_p) * (1 - (w_p * c_p * r_p) ** 2)), 1 - (w_p * c_p * r_p) ** 2) - atan2(
        (w_p * c_p * r_p) * (5 - (w_p * c_p * r_p) ** 2), (1 - 5 * (w_p * c_p * r_p) ** 2))


Fi = calc_fi5(c, r, w)
print("Omega = " + str(w))
print("fi = " + str(wQ))
print("Ku = " + str(calc_ku4(c, w, r)))
print("Fi = " + str(Fi + 3 * pi / 2))

f = np.linspace(1, 50000, 50000)

Fi = []
for a in f:
    Fi.append(((calc_fi4(c, r, a)) * 180 / pi))
plt.plot(np.log10(f), Fi)
plt.title('Fi4')
plt.grid()
plt.savefig('Fi4.png', dpi=500, bbox_inches='tight')
plt.show()


plt.plot(np.log10(f), calc_ku4(c, f, r))
plt.title('Ku4')
plt.grid()
plt.savefig('Ku4.png', dpi=500, bbox_inches='tight')
plt.show()
