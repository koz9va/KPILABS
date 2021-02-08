
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




[U, S, V] = svd(inputMat);

C = U' * inputB;


max = S(1,1);
min = S(1,1);
for i = 1:size(S,1)
    if S(i,i) > max
        max = S(i,i);
    end
    if S(i,i) < min
        min = S(i,i)
    end
end

t = eps * max;
Y = zeros(size(S, 1), 1);
for i = 1:size(S,1)
    if S(i,i) > t
        Y(i) = C(i) / S(i,i);
    else
        Y(i) = 0;
    end
end

printf("SVD_fact\n")
SolveVecSvd = V * Y

printf("CondA\n");
Cond = max / min





printf("DetInputMat\n")
Det = det(inputMat)

printf("EigVec\n")
e = eig(inputMat, 'matrix')