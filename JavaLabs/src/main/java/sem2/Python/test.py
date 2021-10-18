import matplotlib.pyplot as plt
import numpy as np


def approx_par(a, x):
    return a[4] * 12 * x ** 2 + a[3] * 6 * x + a[2] * 2


def approx(a, x):
    return a[4] * x ** 4 + a[3] * x ** 3 + a[2] * x ** 2 + a[1] * x + a[0]


price = [1.12601, 1.12562, 1.12587, 1.12631, 1.12596, 1.12572, 1.12569, 1.1252, 1.12586, 1.12599, 1.12564, 1.12563, 1.1259, 1.12572, 1.12591, 1.12619, 1.12566, 1.12509, 1.12355, 1.12373, 1.12358, 1.12362, 1.12387, 1.12389, 1.12365, 1.12418, 1.12361, 1.12348, 1.12366, 1.1238, 1.12389, 1.12359, 1.12346, 1.12355, 1.12347, 1.12354, 1.12299, 1.12337, 1.12338, 1.12359, 1.12368, 1.12359, 1.12373, 1.12378, 1.12362, 1.12354, 1.12299, 1.12295, 1.12294, 1.12255]
KAMA = [1.1259839549137916, 1.125982386937242, 1.125981976309029, 1.1259719520708105, 1.1259715911875652, 1.1259781875298052, 1.1259766122322405, 1.1259701443701857, 1.1255673466645424, 1.1252785905022233, 1.1259839549137916, 1.125982386937242, 1.125981976309029, 1.1259719520708105, 1.1259715911875652, 1.1259781875298052, 1.1259766122322405, 1.1259701443701857, 1.1255673466645424, 1.1252785905022233, 1.1250299784591642, 1.124834349577389, 1.1246971845568516, 1.1245935211560143, 1.1244249872982048, 1.1243926412897318, 1.1242876140732814, 1.124199599722775, 1.1241948884200699, 1.1241921516408322, 1.1241853756858977, 1.1241822987331715, 1.1241613077444055, 1.1241475861665309, 1.1241389488818898, 1.124091648650106, 1.124007341441478, 1.1240012864868718, 1.123986138208679, 1.1239793689901807, 1.1239742533662347, 1.1239726539765729, 1.1239664736342558, 1.1239624195799653, 1.1239578017259004, 1.1239560626968643, 1.1239520416242448, 1.1238930584431657, 1.1238329679191852, 1.1235662933433503]
time = [404400, 404460, 404520, 404580, 404640, 404700, 404760, 404820, 404880, 404940, 405000, 405060, 405120, 405180, 405240, 405300, 405360, 405420, 405480, 405540, 405600, 405660, 405720, 405780, 405840, 405900, 405960, 406020, 406080, 406140, 406200, 406260, 406320, 406380, 406440, 406500, 406560, 406620, 406680, 406740, 406800, 406860, 406920, 406980, 407040, 407100, 407160, 407220, 407280, 407340]
x = np.linspace(np.min(time), np.max(time), 50)
APR = [-1.9952341898795616E7, 196.60627947329678, -7.264924112687904E-4, 1.1931119936731834E-9, -7.3478716420587135E-16]
FirstIndex = [-1.0366826032831618E-4, -1.0366826032831618E-4, -1.0366825438537768E-4, -1.0366825438537768E-4, -1.0366823955545862E-4, -1.0366823955545862E-4, -1.036682158385589E-4, -1.036682158385589E-4, -1.0366818323467863E-4, -1.0366818323467863E-4, -1.0366814174381768E-4, -1.0366814174381768E-4, -1.0366809136597616E-4, -1.0366809136597616E-4, -1.03668032101154E-4, -1.03668032101154E-4, -1.0366796394935127E-4, -1.0366796394935127E-4, -1.0366788691056785E-4, -1.0366788691056785E-4, -1.036678009848039E-4, -1.036678009848039E-4, -1.0366770617205927E-4, -1.0366770617205927E-4, -1.0366760247233409E-4, -1.0366760247233409E-4, -1.0366748988562823E-4, -1.0366748988562823E-4, -1.0366736841194182E-4, -1.0366736841194182E-4, -1.0366723805127476E-4, -1.0366723805127476E-4, -1.0366709880362711E-4, -1.0366709880362711E-4, -1.036669506689988E-4, -1.036669506689988E-4, -1.0366679364738995E-4, -1.0366679364738995E-4, -1.0366662773880043E-4, -1.0366662773880043E-4, -1.0366645294323032E-4, -1.0366645294323032E-4, -1.0366626926067957E-4, -1.0366626926067957E-4, -1.0366607669114826E-4, -1.0366607669114826E-4, -1.0366587523463628E-4, -1.0366587523463628E-4, -1.0366566489114373E-4, -1.0366566489114373E-4]

color = 'tab:red'
fig, ax1 = plt.subplots()
ax1.plot(time, KAMA, color='tab:blue')
ax1.plot(time, price, color=color)
ax1.plot(x, approx(APR, x), color='tab:green')
ax1.tick_params(axis='y', labelcolor=color)

ax2 = ax1.twinx()
color = 'tab:blue'
ax2.plot(x, approx_par(APR, x), color=color)
#ax2.plot(x, FirstIndex)
ax2.tick_params(axis='y', labelcolor=color)

fig.tight_layout()
plt.grid(True)
plt.savefig('ThirdIndex.png', dpi=1000, bbox_inches='tight')
plt.show()