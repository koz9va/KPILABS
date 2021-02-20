import math
from sympy import *

def function(x1, x2):
    return tan(x1*x2) * pow(x2, 3 * x1)

def differntial():
    x1, x2 = symbols('x1 x2')
    diffx1 = diff(function(x1,x2), x1)
    diffx2 = diff(function(x1,x2), x2)
    return diffx1, diffx2

function(3, 5)
x = differntial()
print (x)