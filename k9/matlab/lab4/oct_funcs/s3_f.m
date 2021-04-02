function retval = s3_f(t)
	if(t < 0)
		retval = t .* 0;
	elseif(t >= 0 && t <= 1) 
		retval = -2.0 .* t + 2.0;	
	elseif(t > 1 && t < 3)
		retval = t - 1.0;
	else
		retval = t .* 0;
	end
end
