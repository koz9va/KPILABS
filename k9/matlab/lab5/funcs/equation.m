function dupn = equation(t, upn)
    global cnt
    cnt = cnt + 1;
	arg = upn ./ 1.7 ./ 26e-3;
	exp_value = zeros(size(upn));
	exp_value(arg >= 80) = exp(80);
	exp_value(arg < 80) = exp(arg);
	exp_value(arg < -80) = 0 .* t;

	j = 5000e-12 .* (exp_value - 1);

	Rb = 1e3 ./ (1 + (j ./ 0.3e-3));

	dupn = (((e(t) - upn) ./ (Rb + 1e3)) - j) ./ 2e-9;
end
