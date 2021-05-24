function ret = functionIN(x,f)
global b;
q = 0.2;
c = 0.1;
d = 0.1;
m = -1;
sig0 = 1;
sig1 = sig0;
sig2 = sig0;
x1 = 3;
sig = sig0 + sig1 .* sin(2.*pi.*x./x1) + sig2 .* sin(2.*pi.*x./x1);
ret = [
	(1 - m .*c .* sig) .* f(2) .* 1i
	f(3) + q .* f(1) .* 1i
	f(1) + d .* f(3) - (b + m .* sig) .* f(3) .* 1i
	];
end
