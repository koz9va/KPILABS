function x = lu_fact(inputMat, inputB)
    [L, U, P] = lu (inputMat);
    inputB = P * inputB;
    x = zeros(numel(inputB), 1);

    for i = 1:size(inputMat, 1)
        x(i) = inputB(i) - L(i, 1:i - 1) * x(1:i - 1);
    end

    umax = max(diag(U));
    myzero = eps * umax;

    for i = size(inputMat, 1):-1:1
        if (abs(U(i, i)) > myzero)
            x(i) = (x(i) - U(i, i+1:size(inputMat, 1)) * x(i + 1:size(inputMat, 1))) / U(i, i);
        else
            disp('Matrix is singular');
            break;
        end
    end
end
