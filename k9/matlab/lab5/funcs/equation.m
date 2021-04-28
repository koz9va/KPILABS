function dupn = equation(t, upn)
    global cnt
    R = 1e3;
    C = 2e-9;
    i0 = 5000e-12;
    Rb0 = 1e3;
    Iv = 0.3e-3;
    cnt = cnt + 1;
	arg = upn ./ 1.7 ./ 26e-3;
	exp_value = zeros(size(upn));
	exp_value(arg > 80) = exp(80);
	exp_value(arg < 80 & arg > -80) = exp(arg(arg < 80 | arg > -80));

	j = i0 .* (exp_value - 1);

	Rb = Rb0 ./ (1 + (j ./ Iv));

	dupn = (((e(t) - upn) ./ (Rb + R)) - j) ./ C;
end
