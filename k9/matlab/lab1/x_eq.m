function y = x_eq(Ud)
	egs = 1.5;
	eds = 10.0;
	R = 3e3;
	U0 = -3.0;
	Beta = 2e-3;
	g22 = 1e-4;
	if(egs <= U0)
		id = g22 .* Ud;
	elseif(abs(Ud) < -(egs + U0))
		id = Beta .* (-2.0 .* (egs + U0) - Ud) .* Ud + g22 .* Ud;
	else
		id = Beta .* (egs + U0) .* (egs + U0) + g22 .* Ud;
	end
	y = eds - Ud - R .* id;
end
