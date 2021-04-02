function y = fun(t)
	global cnt omg tau fi A

	cnt = cnt + 1;

	if(t < pi ./ omg)
		y = A .* omg .* cos(omg .* tau + fi) .* h(t - tau);
	else
		y = t .* 0;
	end
end
