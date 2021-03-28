function retval = h(t)
	Re = 4.0;
	C = 1.0;

	if(t >= 0)
		retval = 0.5 .* (1.0 - exp(t ./ Re ./ C));
	else
		retval = 0;
	end
end

