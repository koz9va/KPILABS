
inputMat = [5 2 3;2 6 1;3 1 7];
inputB = [15;14;13];

[L, U] = lu (inputMat);

max = U(1,1);

for i = 1:size(inputMat, 2)
    if U(i,i) > max;
        max = U(i,i);
    end
end

realZero = max * eps;

bool = 0;
for i = 1:size(inputMat, 2)
    if abs(U(i,i)) < abs(realZero)
        bool = 1;
        break
    end
end

if bool == 1
    printf("We can`t resolve this Matrix with LU_fact\n")
else
    printf("LU fact\n")
    SolvedVecLU = U\(L\inputB)
end





[Q, R] = qr(inputMat);

max = R(1, 1);

for i = 1:size(inputMat, 2)
    if R(i,i) > max;
        max = R(i,i);
    end
end

realZero = max * eps;

bool = 0;
for i = 1:size(inputMat, 2)
    if abs(R(i,i)) < abs(realZero)
        bool = 1;
        break
    end
end

if bool == 1
    printf("We can`t resolve this Matrix with QR_fact\n")
else
    printf("QR fact\n")
    SolvedVecQR = R \ (Q\inputB)
end





R = chol(inputMat);

max = R(1, 1);

for i = 1:size(inputMat, 2)
    if R(i,i) > max;
        max = R(i,i);
    end
end

realZero = max * eps;

bool = 0;
for i = 1:size(inputMat, 2)
    if abs(R(i,i)) < abs(realZero)
        bool = 1;
        break
    end
end

if bool == 1
    printf("We can`t resolve this Matrix with chol_fact\n")
else
    printf("Chol_Fact\n")
    SolvedVecChol = R \ (R'\inputB)
end




%S = svd(inputMat);
%
%Solve = inputB ./ S
%
%printf("CondInputmat\n");
%Cond = cond(inputMat)





printf("DetInputMat\n")
Det = det(inputMat)

printf("EigVec\n")
e = eig(inputMat, 'matrix')