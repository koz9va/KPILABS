Uzv1 = 4;
Uzv3 = 6;
Usv1 = 0;
Usv7 = 12;
U0 = 3.5;
g22 = 2e-6;
betta = 0.3e-3;

U = [0.0, 0.5, 1.0, 3.0, 4.5, 6.0, 7.5, 9.5, 12.0, 16.0, 20.0, 25.0, 30.0];
C = [15.5e-12, 14.0e-12, 12.2e-12, 10.5e-12, 9.7e-12, 9.0e-12, 8.6e-12, 8.1e-12, 7.6e-12, 7.1e-12, 6.7e-12, 6.4e-12, 6.1e-12];

%Uzv = linspace(Uzv1, Uzv3, 3);
%Usv = linspace(Usv1,Usv7, 7);
%UsvLong = linspace(Usv1,Usv7);
%
%fh = figure();
%handler = axes(fh);
%I = zeros(size(Usv, 2), size(Usv, 2));
%for i = 1:size(Uzv, 2)
%    for j = 1:size(Usv, 2)
%        if (Uzv(i) <= U0)
%            I(i, j) = g22 .* Usv(j);
%        elseif (Usv(j) < (Uzv(i) - U0))
%            I(i, j) = betta .* (2 .* (Uzv(i) - U0) - Usv(j)) .* Usv(j) + g22 .* Usv(j);
%        else
%            I(i, j) = betta .* (Uzv(i) - U0) .* (Uzv(i) - U0) + g22 .* Usv(j);
%        end
%    end
%    y = interp1(Usv, I(i, 1:size(Usv, 2)), UsvLong, 'cubic');
%    scatter(Usv, I(i, 1:size(Usv, 2)), 'filled', 'parent', handler);
%    hold on
%    grid on
%    line(UsvLong, y, 'parent', handler);
%end

fg = figure(2);
axes(fg);

x_fit = polyfit(U, C, 3);
y_fit = polyval(x_fit, U);

lsqnonlin();

%plot(U, y_fit)
%hold on
%grid on
%scatter(U, C, 'filled')
%
%C_0 = C(1);
%
%n = 1;
%for i = 1:size(U, 2)
%    if U(i) > 6
%        n = i;
%        break
%    end
%end
%U = U(n:size(U, 2));
%C = C(n:size(C, 2));
%
%U_q = log(U);
%C_q = log(C_0 ./ C);
%
%x_fit = polyfit(C_q, U_q, 1);
%
%n = 1 / x_fit(1);
%fi = exp(x_fit(2));
%
%X = linspace(0, max(U));
%Y = function1(C_0, fi, n, X);
%
%plot(X ,Y);
