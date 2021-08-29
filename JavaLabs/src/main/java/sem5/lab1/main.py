import matplotlib.pyplot as plt
import numpy as np
import random as r


def mean(numbers):
    return float(sum(numbers)) / max(len(numbers), 1)


def funcLinearOneDem(value, scale, offsetX, offsetY):
    return (scale * value + offsetX) + offsetY


def sinFrequency(amplitude, scaleFrequent, value):
    return amplitude * np.sin(scaleFrequent * value)


def plotSin(x, Amp, freq):
    fig, ax1 = plt.subplots()
    fig.subplots_adjust(right=0.75)

    ax2 = ax1.twinx()
    ax3 = ax1.twinx()
    ax3.spines.right.set_position(("axes", 1.2))

    p1, = ax1.plot(x, sinFrequency(Amp[0], freq[0], x), "b-")
    p2, = ax2.plot(x, sinFrequency(Amp[1], freq[1], x), "r-")
    p3, = ax3.plot(x, sinFrequency(Amp[2], freq[2], x), "g-")

    ax1.set_xlabel("TimeLine")
    ax1.set_ylabel("Amplitude " + str(freq[0]) + " Hz")
    ax2.set_ylabel("Amplitude " + str(freq[1]) + " Hz")
    ax3.set_ylabel("Amplitude " + str(freq[2]) + " Hz")

    ax1.yaxis.label.set_color(p1.get_color())
    ax2.yaxis.label.set_color(p2.get_color())
    ax3.yaxis.label.set_color(p3.get_color())
    # ax.grid()
    # twin1.grid()
    # twin2.grid()
    ax1.legend([str(freq[0]) + " Hz"])
    ax2.legend([str(freq[1]) + " Hz"])
    ax3.legend([str(freq[2]) + " Hz"])
    plt.title("Graphics sinus of " + str(freq[0]) + ", " + str(freq[1]) + ", " + str(freq[2]) + " Hz")
    plt.show()


# # point 3
# downBorder = 10
# upBorder = 50
# midDensity = mean([downBorder, upBorder])
# randArray = []
# for i in range(0, 500):
#     randArray.append(r.triangular(downBorder, upBorder, midDensity))
# plt.hist(randArray)
# plt.grid()
# plt.title("Histogram of random numbers")
# plt.show()
# # point 3 done

# # point 4
# x = np.linspace(-5, 5)
# y = funcLinearOneDem(x, 1, 2, 0)
# plt.plot(x, y)
# plt.grid()
# plt.title("Linear Function")
# plt.xlabel("X")
# plt.ylabel("Y")
# plt.show()
# # pint 4 done

# point 5
# Amp = [r.random(), r.random(), r.random()]
# freq = [1, 10, 50]
# x = np.linspace(0, 1, 256)
# plotSin(x, Amp, freq)
# # point 5 done

# # point 5.2
# boolean = True
# while (boolean):
#     Amp1 = input("Amplitude1 = ")
#     try:
#         Amp1 = float(Amp1)
#         boolean = False
#     except:
#         print("Number Format Exception, try one more time")
# boolean = True
# while (boolean):
#     Amp2 = input("Amplitude2 = ")
#     try:
#         Amp2 = float(Amp2)
#         boolean = False
#     except:
#         print("Number Format Exception, try one more time")
# boolean = True
# while (boolean):
#     Amp3 = input("Amplitude3 = ")
#     try:
#         Amp3 = float(Amp3)
#         boolean = False
#     except:
#         print("Number Format Exception, try one more time")
# Amp = [Amp1, Amp2, Amp3]
# freq = [1, 10, 50]
# x = np.linspace(0, 1, 256)
# plotSin(x, Amp, freq)
# # point 5.2 done

