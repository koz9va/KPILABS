function x = LU_factorization(A, B)
    [l, u, p] = lu (A);
    c = p * B;
    x = zeros(size(c));
    n = size(A, 1);

    for i = 1:n
        x(i) = B(i) - l(i, 1:i - 1) * x(1:i - 1);
    end

    tau = eps * max(diag(u));

    for i = n:-1:1
        if (abs(u(i, i)) > tau)
            x(i) = (x(i) - u(i, i+1:n) * x(i + 1:n)) / u(i, i);
        else
            disp('svd');
            break
        end
    end
end