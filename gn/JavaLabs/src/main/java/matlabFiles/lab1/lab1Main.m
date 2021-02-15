inputMat = [5 2 3;2 6 1;3 1 7];
inputB = [15;14;13];

x = qr_fact(inputMat, inputB)

x = lu_fact(inputMat, inputB)

x = chol_fact(inputMat, inputB)

[x, Cond] = svd_fact(inputMat, inputB)

DetInputMat = det(inputMat)

EigVec = eig(inputMat, 'vector')

writeLine(5, 1)

fun = @root2d;
x0 = [0, 0];
x = fsolve(fun, x0);
F = root2d(x);
ans = norm(F)