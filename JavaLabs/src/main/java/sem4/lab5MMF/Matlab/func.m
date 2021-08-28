function ret = func(t, uvih)
	global count
	E0 = 50;
    f = 1e3;
    phi = pi/4;
    R = 4e3;
    C = 1e-6;
    g = 0.8e-3;

	count = count + 1;

	e = E0 .* sin(2 .* pi .* f .* t + phi);
	ud = e - uvih;
	id = zeros(size(t));
	id(ud > 0) = g .* ud(ud > 0) .* sqrt(ud(ud > 0));
	ret = (id - uvih ./ R) ./ C;
end