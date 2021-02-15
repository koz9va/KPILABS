inputMat = [5 2 3;2 6 1;3 1 7];
inputB = [15;14;13];

x = qr_fact(inputMat, inputB)

x = lu_fact(inputMat, inputB)

x = chol_fact(inputMat, inputB)

[x, Cond] = svd_fact(inputMat, inputB)

DetInputMat = det(inputMat)

EigVec = eig(inputMat, 'vector')

writeLine(5, 1)

norm(root2d(fsolve('root2d', [0.001, 1.4])))