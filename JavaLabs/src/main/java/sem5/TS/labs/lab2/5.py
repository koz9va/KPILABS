from os.path import dirname, join as pjoin
import scipy.io as sio
import numpy as np
import matplotlib.pyplot as plt


def _CRG(data, condition, save_to):
    data_dir = pjoin(dirname(sio.__file__), 'matlab', 'tests', 'data')
    mat_fname = pjoin(data_dir, data)
    mat_f = sio.loadmat(mat_fname)
    sorted(mat_f.keys())
    try:
        cardio = mat_f['hr_norm']
    except:
        cardio = mat_f['hr_ap']
    data = np.array(cardio)
    t = np.linspace(0, len(data)/1000, len(data))
    plt.figure("Cardiorhythmogram of a %s person" % condition)
    plt.plot(t, data)
    plt.grid(True)
    plt.title("Cardiorhythmogram of a %s person" % condition)
    plt.ylabel("RR, c")
    plt.xlabel("Time, s")
    plt.show()
    np.save(save_to, data)

_CRG('C://users/vasyo/kpi/ST/heart_rate_norm.mat', 'healthy', 'C://users/vasyo/kpi/ST/heart_rate_norm_graph.npy')
_CRG('C://users/vasyo/kpi/ST/heart_rate_apnea.mat', 'sick', 'C://users/vasyo/kpi/ST/heart_rate_norm_apnea.npy')
