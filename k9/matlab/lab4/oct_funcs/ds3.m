function y = ds3(t)
	global tau
	if(t < 0)
		y = t * 0;
	elseif(t < 1.0)
		y = -2 .* h(tau - t);
	elseif(t < 3.0)
		y = h(tau - t);
	else
		y = t * 0;
	end
end

