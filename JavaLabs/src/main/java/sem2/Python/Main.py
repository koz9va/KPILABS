import pandas as pd
import numpy as np

df = pd.DataFrame({'period': [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
                   'sales': [25, 20, 14, 16, 27, 20, 12, 15, 14, 19]})

df['4dayEWM'] = df['sales'].ewm(alpha=0.2, adjust=False).mean()
print(df)
