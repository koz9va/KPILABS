import csv
import numpy as np
import pandas as pd
files = ['C://users//vasyo//kpi//ST/abc10.csv', 'C://users//vasyo//kpi//ST/abo10.csv',
         'C://users//vasyo//kpi//ST/hbo10.csv', 'C://users//vasyo//kpi//ST/hbc10.csv']
time_ms = list()
top_left_f_kg = list()
top_right_f_kg = list()
bottom_left_f_kg = list()
bottom_right_f_kg = list()
cop_x = list()
cop_y = list()
total_f = list()
ave0 = list()
ave1 = list()
ave2 = list()
ave3 = list()
square0 = list()
square1 = list()
square2 = list()
square3 = list()
medi0 = list()
medi1 = list()
medi2 = list()
medi3 = list()


def aaa(filename, ave, square, medi):
    with open(filename) as f:
        reader = csv.reader(f, delimiter=' ')
        for row in reader:
            time_ms.append(int(row[0]))
            top_left_f_kg.append(float(row[1]))
            top_right_f_kg.append(float(row[2]))
            bottom_left_f_kg.append(float(row[3]))
            bottom_right_f_kg.append(float(row[4]))
            cop_x.append(float(row[5]))
            cop_y.append(float(row[6]))
            total_f.append(float(row[7]))
    ave.append(np.average(top_left_f_kg))
    ave.append(np.average(top_right_f_kg))
    ave.append(np.average(bottom_left_f_kg))
    ave.append(np.average(bottom_right_f_kg))
    ave.append(np.average(cop_x))
    ave.append(np.average(cop_y))
    ave.append(np.average(total_f))
    medi.append(np.median(top_left_f_kg))
    medi.append(np.median(top_right_f_kg))
    medi.append(np.median(bottom_left_f_kg))
    medi.append(np.median(bottom_right_f_kg))
    medi.append(np.median(cop_x))
    medi.append(np.median(cop_y))
    medi.append(np.median(total_f))
    square.append(np.std(top_left_f_kg))
    square.append(np.std(top_right_f_kg))
    square.append(np.std(bottom_left_f_kg))
    square.append(np.std(bottom_right_f_kg))
    square.append(np.std(cop_x))
    square.append(np.std(cop_y))
    square.append(np.std(total_f))


aaa(files[0], ave0, square0, medi0)
aaa(files[1], ave1, square1, medi1)
aaa(files[2], ave2, square2, medi2)
aaa(files[3], ave3, square3, medi3)
df = pd.DataFrame({'type of signal': ['Acrobats base close', 'Acrobats base open', 'Handball base close',
                   'Handball base open'], 'average top_left_f_kg': [ave0[0], ave1[0], ave2[0], ave3[0]],
                   'average top_right_f_kg': [ave0[1], ave1[1], ave2[1], ave3[1]],
                   'average bottom_left_f_kg': [ave0[2], ave1[2], ave2[2], ave3[2]],
                   'average bottom_right_f_kg': [ave0[3], ave1[3], ave2[3], ave3[3]],
                   'average cop_x': [ave0[4], ave1[4], ave2[4], ave3[4]],
                   'average cop_y': [ave0[5], ave1[5], ave2[5], ave3[5]],
                   'average total_f': [ave0[6], ave1[6], ave2[6], ave3[6]],
                   'medium top_left_f_kg': [medi0[0], medi1[0], medi2[0], medi3[0]],
                   'medium top_right_f_kg': [medi0[1], medi1[1], medi2[1], medi3[1]],
                   'medium bottom_left_f_kg': [medi0[2], medi1[2], medi2[2], medi3[2]],
                   'medium bottom_right_f_kg': [medi0[3], medi1[3], medi2[3], medi3[3]],
                   'medium cop_x': [medi0[4], medi1[4], medi2[4], medi3[4]],
                   'medium cop_y': [medi0[5], medi1[5], medi2[5], medi3[5]],
                   'medium total_f': [medi0[6], medi1[6], medi2[6], medi3[6]],
                   'std top_left_f_kg': [square0[0], square1[0], square2[0], square3[0]],
                   'std top_right_f_kg': [square0[1], square1[1], square2[1], square3[1]],
                   'std bottom_left_f_kg': [square0[2], square1[2], square2[2], square3[2]],
                   'std bottom_right_f_kg': [square0[3], square1[3], square2[3], square3[3]],
                   'std cop_x': [square0[4], square1[4], square2[4], square3[4]],
                   'std cop_y': [square0[5], square1[5], square2[5], square3[5]],
                   'std total_f': [square0[6], square1[6], square2[6], square3[6]],
                   })
print(df)
