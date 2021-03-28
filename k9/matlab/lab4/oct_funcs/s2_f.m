function retval = s2_f(t)
	global A t0 sigm
	retval = A .* exp(-(((t - t0).*(t - t0))/(sigm.*sigm)));
end
