function retval = h(t)
	Re = 4.0;
	C = 1.0;

	if(t >= 0)
		retval = 0.5 .* (1.0 - exp(t ./ Re ./ C));
	else
<<<<<<< HEAD
		retval = 0 .* t;
=======
		retval = t .* 0;
>>>>>>> cca08da58216cf91d474f29db63ee72be3f58124
	end
end

