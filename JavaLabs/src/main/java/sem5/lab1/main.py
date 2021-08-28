import matplotlib.pyplot as plt
import numpy as np


# point 4
def funcLinearOneDem(value, scale, offsetX, offsetY):
    return (scale * value + offsetX) + offsetY


x = np.linspace(-5, 5)
y = funcLinearOneDem(x, 1, 2, 0)
plt.plot(x, y)
plt.grid()
plt.title("Linear Function")
plt.xlabel("X")
plt.ylabel("Y")
plt.show()

