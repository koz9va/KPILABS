function y = fun(t)
	global cnt omg tau fi A

	cnt = cnt + 1;

	if(t < pi ./ omg)
		y = A .* omg .* cos(omg .* tau + fi) .* h(t - tau);
	else
%<<<<<<< HEAD
		y = 0.0 .* t;
%=======
		y = t .* 0;
%>>>>>>> cca08da58216cf91d474f29db63ee72be3f58124
	end
end
