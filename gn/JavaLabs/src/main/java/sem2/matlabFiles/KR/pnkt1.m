x = linspace(0, 0.0005);
y = funkforpunkt1(x);
plot(x, y)
%g1 = 2;
%g2 = 1;
%r1 = 0.5
%r2 = 1
%c1 = 4e-3;
%c2 = 1e-6;
%k = 100;
%lambda1 = -30038
%lambda2 = -164
%
%A = [-g1/c1 -g1/((k+1)*c1); -g1/c2 -(r1+r2)/(r1*r2*(k+1)*c2)]
%
%E = eye(size(A, 2))
%
%z1 = (A - lambda2 * E) ./ (lambda1 - lambda2)
%z2 = (A - lambda1 * E) ./ (lambda2 - lambda1)

%A = [-335.16 -4.95;-2e6 -29538.16] ./ -29873.16