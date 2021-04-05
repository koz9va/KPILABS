function x = chol_fact(inputMat, inputB)
    R = chol(inputMat);
    R1 = R';
    x = zeros(size(inputB));

    n = size(inputMat, 1);

    for i = 1:n
        x(i) = (inputB(i) - R1(i, 1:i - 1) * x(1 : i - 1)) / R1(i, i);
    end

    rmax = max(diag(R));
    myzero = eps * rmax;

    for i = n:-1:1
        if (abs(R(i, i)) > myzero)
            x(i) = (x(i) - (R(i, i + 1:n) * x(i + 1:n))) / R(i, i);
        else
            disp('Matrix is singular');
            break;
        end
    end
end
