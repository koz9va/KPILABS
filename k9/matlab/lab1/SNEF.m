function F = SNEF(X)
	Rc = 1e2;
	ie0 = 1e-9;
	a = 0.98;
	h12 = 2e-3;
	ft = 26e-3;
	eb = 4.0;
	me = 1.0;
	ic0 = 1.5e-9;
	Rb = 5e3;
	ec = 10.0;

	F(1) = X(1) - ec + Rc .* ie0 .* a .* (exp((X(2) + h12 .* X(1)) ./ (me .* ft)) - 1.0) - Rc .* ic0 .* (exp((X(2) - X(1)) / (me .* ft)) - 1.0);
	F(2) = X(2) - eb + Rb .* ic0 .* (exp((X(2) - X(1)) / (me .* ft)) - 1.0) + Rb .* (1.0 - a) .* ie0 * (exp( (X(2) + h12 .* X(1)) / (me .* ft) ) - 1.0);

end
