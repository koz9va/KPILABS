function U = e(t)
	U = zeros(size(t));
	U(t <= 1e-6 & t >= 0) = 5;
	U(t >= 1e-6) = 0;
end
