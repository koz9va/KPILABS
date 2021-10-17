import matplotlib.pyplot as plt
import numpy as np

def plotSinEight(freq, Amp, Duration):
    x = np.linspace(0, Duration, 256 * Duration)
    y = Amp * np.sin(freq * x)
    plt.plot(x, y)
    plt.grid()
    plt.show()
    return np.mean(y)

