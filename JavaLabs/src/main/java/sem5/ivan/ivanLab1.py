import numpy as np
import matplotlib.pyplot as plt

ki = 350e-3
ke = 30e-3
nai = 6e-3
nae = 400e-3
cli = 30e-3
cle = 450e-3
fit = 25.8e-3
pk = 1
pna = 0.004
pcl = 0.6
#point 1

def Goldman(kae, kai, a, fit, nae, nai):
    return fit * np.log((kae + a * nae)/(kai + a * nai))

def Nernst(ke, ki, fit):
    return fit * np.log(ke / ki)

print("K :" + str(fit * np.log(ke / ki)))
print("Na :" + str(fit * np.log(nae / nai)))
print("Cl :" + str(fit * np.log(cli / cle)))

Um = fit * np.log((pk * ke + pna * nae + pcl * cli) / (pk * ki + pna * nai + pcl * cle))
print("Um :" + str(Um))
# point 1 end

# point 2

cli2 = cle * np.exp(Um / fit)
a = pna / pk
Um2 = fit * np.log((ke + a * nae)/(ki + a * nai))
print("Um2 :" + str(Um2))
# point 2 end

#point 3

x = np.linspace(ke, 2 * ki)
y = Goldman(ke, x, a, fit, nae, nai)
y1 = Nernst(ke, x, fit)
plt.plot(x, y, x, y1)
plt.grid()
plt.title("3")
fig = plt.figure()

x = np.linspace(nai, 2 * nae)
y = Goldman(ke, ki, a, fit, x, nai)
y1 = Nernst(x, nai, fit)
plt.plot(x, y, x, y1)
plt.grid()
plt.show()

plt.figure()
a = np.linspace(0.01, 100)
y = Goldman(ke, ki, a, fit, nae, nai)
plt.plot(a, y)
plt.grid()
plt.title("5")
plt.show()

Ek = - fit * np.log(ke / ki)
Ena = fit * np.log(nae / nai)
Ecl = - fit * np.log(cli / cle)

el = ((pna*Ena - pk*Ek -Um*(pna+pk+pcl))/pcl)

print(el)

def Umnew(pna, pk, pl, ena, ek ,el, fit, ke, x):
    return (pna * ena - pk * (- fit * np.log(ke / x)) - pcl * el) / (pna + pk + pcl)


x = np.linspace(ke, 2 * ki)
y = Umnew(pna, pk, pcl, Ena, Ek, Ecl, fit, ke, x)
plt.figure()
plt.plot(x, y)
plt.grid()
plt.title("7")
plt.show()

def Umnew1(pna, pk, pl, ek ,el, fit, x, ki):
    return (pna * (fit * np.log(x / nai)) - pk * ek - pcl * el) / (pna + pk + pcl)

x = np.linspace(nai, 2 * nae)
y = Umnew1(pna, pk, pcl, Ek, Ecl, fit, x, nae)
plt.figure()
plt.plot(x, y)
plt.grid()
plt.title("8")
plt.show()

Um = (a * Ena - Ek - pcl * el) / (a + 1 + pcl)
plt.figure()
plt.plot(a, Um)
plt.title("9")
plt.grid()
plt.show()

