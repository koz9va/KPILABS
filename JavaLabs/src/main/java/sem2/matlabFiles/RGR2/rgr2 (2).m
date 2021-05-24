f = @(t, y) -y .* y .* (t + 1);
rng_pnts = [0, 1];
points_num = 4;
h = (rng_pnts(2)-rng_pnts(1))/points_num;
range = linspace(rng_pnts(1)+rng_pnts(2)/points_num, rng_pnts(2), points_num);
y0 = 1;

%Euler
disp("Euler's method");
E_y = zeros(points_num, 1);
fx = f(rng_pnts(1), y0);
E_y(1) = y0 + h .* fx;
disp("1:  " + y0 + " + " + h + " * " + fx + "; y = "+ E_y(1)+"; t = " + range(1));
for ii = 2:points_num
    fx = f(range(ii-1), E_y(ii-1));
    E_y(ii) = E_y(ii-1) + h .* fx;
    disp(ii+":  " + E_y(ii-1) + " + " + h + " * " + fx + "; y = "+E_y(ii)+"; t = " + range(1));
end

%Runge
disp("Runge method");
h = 0.5;
t0 = rng_pnts(1);
R_y = zeros(points_num, 1);
k = zeros(4, 1);

k(1) = f(t0, y0);
k(2) = f(t0+h/2, y0+(h*k(1))/2);
k(3) = f(t0+h/2, y0+(h*k(2))/2);
k(4) = f(t0+h/2, y0+h*k(3));
y1 = y0 + (h/6)*(k(1) + 2*k(2)+2*k(3)+k(4));

disp("1:");
disp("k" + (1:4)' + ": " + k);
disp("y1: " + y1);

t1 = t0+h;
k(1) = f(t1, y1);
k(2) = f(t1+h/2, y1+(h*k(1))/2);
k(3) = f(t1+h/2, y1+(h*k(2))/2);
k(4) = f(t1+h/2, y1+h*k(3));
y2 = y1 + (h/6)*(k(1) + 2*k(2)+2*k(3)+k(4));


disp("2:");
disp("k" + (1:4)' + ": " + k);
disp("y2: " + y2);

%Adams

h = 0.25;

t = h .* (1:4) + t0;
y3 = E_y(2) + (h/12) * (23 * f(t(2), E_y(2)) - 16 * f(t(1), E_y(1)) + 5 * f(t0, y0));

disp("Adams:");
disp("y3: " + y3);

y4 = E_y(3) + (h/12) * (23 * f(t(3), E_y(3)) - 16 * f(t(2), E_y(2)) + 5 * f(t(1), E_y(1)));
disp("y4: " + y4);

%Gir

disp("Gir");

N_y = zeros(4, 1);
N_y(1) = fzero(@(x) h * f(t(1), x) + y0 - x, y0);
for ii = 2:length(N_y)
    N_y(ii) = fzero(@(x) h * f(t(ii), x) + N_y(ii-1) - x, N_y(ii-1));
end
disp("y_n" + (1:4)' + ": " + N_y);

G_y = zeros(2, 1);
G_y(1) = fzero(@(x) (11/6)*x - 3*N_y(2) + (3/2) * N_y(1) - (1/3)*y0 - h*f(t(3), x), N_y(2));
G_y(2) = fzero(@(x) (11/6)*x - 3*N_y(3) + (3/2) * N_y(2) - (1/3)*N_y(1) - h*f(t(4), x), G_y(1));
disp("y_g" + (1:2)' + ": " + G_y);
tg = 0.1;
F = @(x, y) [x + tg;
            x + y];
v0 = [1; 2];
h = 0.1;
X1 = v0 + h .* F(v0(1), v0(2));
tg = tg + h;
X2 = X1 + h .* F(X1(1), X1(2));
disp("System Euler")
disp("X1");
disp(X1);
disp("X2");
disp(X2);