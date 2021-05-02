function ff = fun(t, uvih)
	global counter
	E0 = 100;
	f = 5e3;
	phi = pi/3;
	R = 3e3;
	C = 0.4e-6;
	g = 1e-3;

	counter = counter + 1;

	e = E0 .* sin(2 .* pi .* f .* t + phi);
	ud = e - uvih;
	id = zeros(size(t));
	id(ud > 0) = g .* ud(ud > 0) .* sqrt(ud(ud > 0));
	ff = (id - uvih ./ R) ./ C;
end
