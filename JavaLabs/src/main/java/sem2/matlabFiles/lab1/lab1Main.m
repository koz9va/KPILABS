inputMat = [18 -6 -9;-6 15 -8;-9 -8 17];
inputB = [-21;0;26];

qr = qr_fact(inputMat, inputB)
lu = lu_fact(inputMat, inputB)
chol = chol_fact(inputMat, inputB)
[svd, Cond] = svd_fact(inputMat, inputB)

DetInputMat = det(inputMat)
EigVec = eig(inputMat, 'vector')

writeLine(5, 1)
x = fsolve('root2d', [0.001, 1.4])
norm(root2d(x))