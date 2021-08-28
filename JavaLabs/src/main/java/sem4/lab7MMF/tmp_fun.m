function ret = tmp_fun(x, y)
    global q;
    global M;
    global alpha0;
    ret = zeros(size(y));
    F = y(1:M);
    P = y(M + 1 : 2 * M);
    ret(1 : M) = P;
    ret(M + 1 : 2 * M) = -(1 + P) .* (1 + P) .* (1 + P) .* real(1i * q * exp(1i * F) / M * sum(exp(-1i * F)));
end
