function retval = s1_f(t)
	global A omg fi
	if(t >= 0 && t <= pi/omg)
		retval = A .* sin(omg .* t + fi);
	else
		retval = 0;
	end
end
