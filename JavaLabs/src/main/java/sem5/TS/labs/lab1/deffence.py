import matplotlib.pyplot as plt
import numpy as np


randArray = []
for i in range(0, 100):
    randArray.append(np.random.normal(loc=0.0, scale=0.1, size=None))
plt.grid()
plt.title("Histogram of random numbers")
x = np.linspace(0, 1, 100)
y = 3 * np.sin(2 * np.pi * 10 * x)
arr = y + randArray
plt.plot(x, arr)
plt.show()
