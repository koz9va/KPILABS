import numpy as np
import matplotlib.pyplot as plt


def _ekg(data, condition):
    lst = data.files
    sign = list()
    lab = list()
    lab_ind = list()
    labA = list()
    labR = list()
    labN = list()
    for item in lst:
        print(item)
        if item == 'units':
            units = data[item]
        if item == 'signal':
            sign = data[item]
        if item == 'fs':
            rate = data[item]
        if item == 'labels':
            lab = data[item]
        if item == 'labels_indexes':
            lab_ind = data[item]
        print(data[item])
    t = np.linspace(0, len(sign) / rate, len(sign))
    for i in range(len(lab)):
        if lab[i] == 'A':
            labA.append(lab_ind[i])
        elif lab[i] == 'R':
            labR.append(lab_ind[i])
        elif lab[i] == 'N':
            labN.append(lab_ind[i])
        else: break
    print(labA)
    plt.figure("%s person" % condition)
    plt.plot(t, sign)
    plt.plot(t[labA], sign[labA], 'ro', label='Atrial premature beat')
    plt.plot(t[labR], sign[labR], 'ko', label='Right bundle branch block beat')
    plt.plot(t[labN], sign[labN], 'bo', label='Normal beat')
    plt.title("%s person ECG" % condition)
    plt.ylabel(units)
    plt.xlabel("t, s")
    plt.grid(True)
    plt.legend()
    plt.show()

data_an = np.load("C://users/vasyo/kpi/ST/anomaly_10.npz")
data_norm = np.load("C://users/vasyo/kpi/ST/norm_10.npz")
_ekg(data_an, 'Anomaly')
_ekg(data_norm, 'Norm')
