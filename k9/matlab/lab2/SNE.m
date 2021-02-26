function retval = SNE(x, y)
	if (nargin == 2)
		retval = (x - 2 .* y + 2) .* (x - 2 .* y + 2) + (4.*x.*x - x + 1) .* (4.*x.*x - y + 1);
	elseif(nargin == 1)
		retval = (x(1) - 2 .* x(2) + 2) .* (x(1) - 2 .* x(2) + 2) + (4.*x(1).*x(1) - x(2) + 1) .* (4.*x(1).*x(1) - x(2) + 1);
	else
		disp('wrong number of arguments in SNE');
	end
end
