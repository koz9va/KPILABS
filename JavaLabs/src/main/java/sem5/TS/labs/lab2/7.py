import csv
import matplotlib.pyplot as plt
import numpy as np

hr = list()
hr_av = list()
spo2 = list()
spo2_av = list()
filename = 'C://users/vasyo/kpi/ST/Subject10_SpO2Hr.csv'
with open(filename) as f:
    reader = csv.reader(f, delimiter=',')
    i = 0
    for row in reader:
        i += 1
        if i > 1:
            spo2.append(float(row[2]))
            hr.append(float(row[3]))
print(hr)
for i in range(0, len(hr), 30):
    if len(hr) - i > 29:
        hr_av.append(np.average(hr[i:i+29]))
        spo2_av.append(np.average(spo2[i:i+29]))
    else:
        hr_av.append(np.average(hr[i:len(hr)-1]))
        spo2_av.append(np.average(spo2[i:len(hr)-1]))
print(len(hr_av))
print(len(spo2_av))
plt.figure("hr and spo2 graphs")
t = np.linspace(0, len(hr), len(hr))
plt.xlabel("t, s")
plt.ylabel("SpO2 %, hr bpm")
plt.plot(t, hr, label='hr')
plt.plot(t, spo2, label='spo2')
plt.legend()
plt.grid(True)
plt.figure('average hr and spo2 graphs')
t = np.linspace(0, int(len(hr)/30) + 1, int(len(hr)/30) + 1)
plt.xlabel("each point means average for 30 seconds")
plt.ylabel("SpO2 %, hr bpm")
plt.plot(t, hr_av, label='hr')
plt.plot(t, spo2_av, label='spo2')
plt.legend()
plt.grid(True)
plt.show()
