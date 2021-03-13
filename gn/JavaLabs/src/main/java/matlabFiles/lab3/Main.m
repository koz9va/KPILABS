Uzv1 = 4;
Uzv3 = 6;
Usv1 = 0;
Usv7 = 12;
U0 = 3.5;
g22 = 2e-6;
betta = 0.3e-3;

global U1 C1
U1 = [0.0, 0.5, 1.0, 3.0, 4.5, 6.0, 7.5, 9.5, 12.0, 16.0, 20.0, 25.0, 30.0];

C1 = [15.5e-12, 14.0e-12, 12.2e-12, 10.5e-12, 9.7e-12, 9.0e-12, 8.6e-12, 8.1e-12, 7.6e-12, 7.1e-12,
6.7e-12, 6.4e-12, 6.1e-12];

Uzv = linspace(Uzv1, Uzv3, 3);
Usv = linspace(Usv1,Usv7, 7);
UsvLong = linspace(Usv1,Usv7);

fh = figure(1);
handler = axes(fh);
grid on
hold on
I = zeros(size(Usv, 2), size(Usv, 2));
for i = 1:size(Uzv, 2)
    for j = 1:size(Usv, 2)
        if (Uzv(i) <= U0)
            I(i, j) = g22 .* Usv(j);
        elseif (Usv(j) < (Uzv(i) - U0))
            I(i, j) = betta .* (2 .* (Uzv(i) - U0) - Usv(j)) .* Usv(j) + g22 .* Usv(j);
        else
            I(i, j) = betta .* (Uzv(i) - U0) .* (Uzv(i) - U0) + g22 .* Usv(j);
        end
    end
    y = interp1(Usv, I(i, 1:size(Usv, 2)), UsvLong, 'cubic');
    line(UsvLong, y, 'parent', handler);
    y = interp1(Usv, I(i, 1:size(Usv, 2)), UsvLong, 'spline');
    line(UsvLong, y, 'parent', handler);
    y = interp1(Usv, I(i, 1:size(Usv, 2)), UsvLong, 'next');
    line(UsvLong, y, 'parent', handler);
    scatter(Usv, I(i, 1:size(Usv, 2)), 'filled', 'parent', handler);
end

C_0 = C1(1);

fn = figure(2);
handler = axes(fn);

hold on
grid on

n = 1;
for i = 1:size(U1, 2)
    if U1(i) > 6
        n = i;
        break
    end
end
U = U1(n:size(U1, 2));
C = C1(n:size(C1, 2));

U_q = log(U);

C_q = log(C_0 ./ C);

x_fit = polyfit(C_q, U_q, 1);

n = 1 / x_fit(1);
fi = exp(x_fit(2));

X = linspace(0, max(U));
Y = function1(C_0, fi, n, X);

line(X, Y, 'parent', handler, 'color', 'green');

scatter(U1, C1, 'filled')
y = lsqnonlin('goal_lsq', [n, fi]);
n = y(1)
fio = y(2)

X = linspace(0, max(U));
Y = function1(C_0, fio, n, X);

line(X, Y, 'parent', handler, 'color', 'red');
