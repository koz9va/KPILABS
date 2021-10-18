import csv
import numpy as np
import matplotlib.pyplot as plt


def _plot_aksel(T1, X, Y, Z):
    plt.plot(T1, X, label=u"ACCELEROMETER X")
    plt.plot(T1, Y, label=u"ACCELEROMETER Y")
    plt.plot(T1, Z, label=u"ACCELEROMETER Z")
    plt.title("ACCELEROMETER")
    plt.xlabel("s")
    plt.ylabel("m/s\xb2")
    plt.grid(True)
    plt.legend()
    plt.show()
    return 0


def _plot_gyro(T1, X, Y, Z,):
    plt.plot(T1, gyro_X, label=u"Gyroscope X")
    plt.plot(T1, gyro_Y, label=u"Gyroscope Y")
    plt.plot(T1, gyro_Z, label=u"Gyroscope Z")
    plt.title("Gyroscope, rad/s")
    plt.xlabel("s")
    plt.ylabel("rad")
    plt.grid(True)
    plt.legend()
    plt.show()
    return 0


def re(input, out1, out2):
    i = 0
    with open(input) as f:
        reader = csv.reader(f, delimiter=";")
        for row in reader:
            i += 1
            if i > 2:
                aksel_X.append(float(row[0]))
                aksel_Y.append(float(row[1]))
                aksel_Z.append(float(row[2]))
                gyro_X.append(float(row[9]))
                gyro_Y.append(float(row[10]))
                gyro_Z.append(float(row[11]))
    T1 = np.linspace(0, len(aksel_X)*0.5, len(aksel_X))
    _plot_aksel(T1, aksel_X, aksel_Y, aksel_Z)
    _plot_gyro(T1, gyro_X, gyro_Y, gyro_Z)
    with open(out1, 'wb') as f:
        np.save(f, aksel_X, aksel_Y, aksel_Z)
    with open(out2, 'wb') as ff:
        np.save(ff, gyro_X, gyro_Y, gyro_Z)


aksel_X = list()
aksel_Y = list()
aksel_Z = list()
gyro_X = list()
gyro_Y = list()
gyro_Z = list()
files_in = ['/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/resources/standing.csv', '/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/resources/going.csv',
            '/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/resources/running.csv']
files_out = ['/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/out/aksel1', '/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/out/gyro2', '/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/out/aksel2',
             '/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/out/gyro2', '/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/out/aksel3', '/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/out/gyro3']

re(files_in[0], files_out[0], files_out[1])
re(files_in[1], files_out[2], files_out[3])
re(files_in[2], files_out[4], files_out[5])
