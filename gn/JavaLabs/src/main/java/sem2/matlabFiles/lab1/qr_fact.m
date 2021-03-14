function x = qr_fact(inputMat, inputB)
    [Q, R] = qr(inputMat);
    b1 = Q' * inputB;
    x = zeros(size(inputB));
    n = size(inputMat, 1);

    rmax = max(diag(R));
    myzero = eps * rmax;

    for (ii = n:-1:1)
        if (abs(R(ii, ii)) > myzero)
            x(ii) = (b1(ii) - R(ii, ii + 1:n) * x(ii + 1:n)) / R(ii,ii);
        else
            disp('Matrix is singular');
            break;
        end
    end
end
