import matplotlib.pyplot as plt
import numpy as np


def part_time(rate, t_start, t_finish, y):
    try:
        t = np.linspace(t_start, t_finish, (t_finish - t_start) * rate)
        plt.figure('Part time graph')
        plt.title('intracranial pressure signal')
        plt.plot(t, y[t_start * rate: t_finish * rate])
        plt.xlabel('t, s')
        plt.ylabel('P, mmHg')
        plt.grid(True)
    except:
        print('Incorrect time')
        return -1


tbi = list()
rate = 125
f = open('C://users/vasyo/kpi/ST/TBI_ICP.txt')
for row in f:
    tbi.append(float(row))
print(len(tbi))
t = np.linspace(0, len(tbi)/125, len(tbi))
plt.figure('intracranial pressure signal')
plt.title('intracranial pressure signal')
plt.plot(t, tbi)
plt.xlabel('t, s')
plt.ylabel('P, mmHg')
plt.grid(True)
part_time(rate, 5000, 15000, tbi)
plt.show()
