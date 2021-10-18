from os.path import dirname, join as pjoin
import scipy.io as sio
import numpy as np
import matplotlib.pyplot as plt


data_dir = pjoin(dirname(sio.__file__), 'matlab', 'tests', 'data')
mat_fname = pjoin(data_dir, 'C://users/vasyo/kpi/ST/eeg_healthy_10.mat')
mat_f = sio.loadmat(mat_fname)
sorted(mat_f.keys())
eeg = mat_f['sig']
data = np.array(eeg)
data_h = data[0]
t = np.linspace(0, len(data_h)/256, len(data_h))
plt.figure("EEG of a healthy person")
plt.plot(t, data_h)
plt.grid(True)
plt.title("EEG of a healthy person")
plt.ylabel("U, mcV")
plt.xlabel("Time, s")
mat_fname = pjoin(data_dir, 'C://users/vasyo/kpi/ST/eeg_sick_10.mat')
mat_f = sio.loadmat(mat_fname)
sorted(mat_f.keys())
eeg = mat_f['sig']
data = np.array(eeg)
data_s = data[0]
t = np.linspace(0, len(data_s)/256, len(data_s))
plt.figure("EEG of a sick person")
plt.plot(t, data_s)
plt.grid(True)
plt.title("EEG of a sick person")
plt.ylabel("U, mсV")
plt.xlabel("Time, s")
plt.show()
np.save("C://users/vasyo/kpi/ST/eeg_h_p.npy", data_h)
np.save("C://users/vasyo/kpi/ST/eeg_s_p.npy", data_s)
