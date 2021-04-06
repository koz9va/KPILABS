function y = ds2(t)
	global tau
	y = -2.0 * exp(-((t - 5.0) .* (t - 5.0))) .* (t - 5.0) .* h(tau - t);
end
