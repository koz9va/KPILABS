import matplotlib.pyplot as plt
import numpy as np
from squareImpulse import squareImpulse
from plotSinEight import plotSinEight
import random as r


def mean(numbers):
    return float(sum(numbers)) / max(len(numbers), 1)


def funcLinearOneDem(value, scale, offsetX, offsetY):
    return (scale * value + offsetX) + offsetY


def sinFrequency(amplitude, scaleFrequent, value):
    return amplitude * np.sin(scaleFrequent * value)


def plotSin(x, Amp, freq):
    ax1 = plt.subplot(311)
    ax2 = plt.subplot(312)
    ax3 = plt.subplot(313)

    ax1.plot(x, sinFrequency(Amp[0], freq[0], x))
    ax1.grid()
    ax2.plot(x, sinFrequency(Amp[1], freq[1], x))
    ax2.grid()
    ax3.plot(x, sinFrequency(Amp[2], freq[2], x))
    ax3.grid()

    plt.show()


# point 3
downBorder = 10
upBorder = 50
midDensity = mean([downBorder, upBorder])
randArray = []
for i in range(0, 500):
    randArray.append(r.triangular(downBorder, upBorder, midDensity))
plt.hist(randArray)
plt.grid()
plt.title("Histogram of random numbers")
plt.show()
# point 3 done

# point 4
x = np.linspace(-5, 5)
y = funcLinearOneDem(x, 1, 2, 0)
plt.figure()
plt.plot(x, y)
plt.grid()
plt.title("Linear Function")
plt.xlabel("X")
plt.ylabel("Y")
plt.show()
# pint 4 done

# point 5
Amp = [r.random(), r.random(), r.random()]
freq = [1, 10, 50]
x = np.linspace(0, 1, 256)
plotSin(x, Amp, freq)
# point 5 done

# point 5.2
boolean = True
while (boolean):
    Amp1 = input("Amplitude1 = ")
    try:
        Amp1 = float(Amp1)
        boolean = False
    except:
        print("Number Format Exception, try one more time")
boolean = True
while (boolean):
    Amp2 = input("Amplitude2 = ")
    try:
        Amp2 = float(Amp2)
        boolean = False
    except:
        print("Number Format Exception, try one more time")
boolean = True
while (boolean):
    Amp3 = input("Amplitude3 = ")
    try:
        Amp3 = float(Amp3)
        boolean = False
    except:
        print("Number Format Exception, try one more time")
Amp = [Amp1, Amp2, Amp3]
freq = [1, 10, 50]
x = np.linspace(0, 1, 256)
plotSin(x, Amp, freq)
# point 5.2 done

# point 6.1
t = np.linspace(0, 10, 256 * 10, endpoint=False)
sig = squareImpulse(1, t, 0.3, [4])
plt.figure()
plt.plot(t, sig)
plt.grid()
plt.show()
# point 6.1 done

# point 6.2
Amp = input("AmplitudeSquare = ")
Amp = float(Amp)
Shape = input("DurationSquare = ")
Shape = float(Shape)
At = [r.random() * 10]
python_file = open("example.txt", "w")
sig = squareImpulse(Amp, t, Shape, At)
for i in range(len(sig)):
    python_file.write(str(sig[i]) + " ")
python_file.close()

my_file_handle = open("/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/java/sem5/lab1/example.txt")
a = my_file_handle.read()
arr = a.split(" ")
arr.remove(arr[-1])
fig = plt.figure()
plt.plot(t, arr)
plt.grid()
plt.show()

print("mean value = " + str(plotSinEight(1, 2, 5)))
