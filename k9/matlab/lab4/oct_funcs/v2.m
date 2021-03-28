function retval = v2(x)
	global cnt
	cnt = cnt + 1;
	retval = (exp(sin(x))) ./ (1.0 + cos(x));
end
