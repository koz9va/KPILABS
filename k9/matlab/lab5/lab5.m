global cnt
addpath("funcs");

t_file = fopen("t.txt", "r");
y_file = fopen("y.txt", "r");

t_arr = fscanf(t_file, "%g", Inf);
y_arr = fscanf(y_file, "%g", Inf);

fclose(y_file);
fclose(t_file);

fh = figure();

ah = axes(fh);
grid on;
hold on;
xlabel("t, c");
ylabel("U, в");

t = linspace(0, 5e-6);
et = e(t);

cnt = 0;
[t_m, y_m] = ode45('equation', [0 5e-6], 0);
disp("Calls to function:")
cnt
disp("Points found:")
size(y_m, 1)
R = 1e3;
C = 2e-9;
i0 = 5000e-12;
m = 1.7;
ft = 26e-3;
Rb0 = 1e3;
Iv = 0.3e-3;
cnt = cnt + 1;
arg = y_m ./ 1.7 ./ 26e-3;
exp_value = zeros(size(y_m));
exp_value(arg >= 80) = exp(80);
exp_value(arg < 80 | arg > -80) = exp(arg(arg < 80 | arg > -80));
j = i0 .* (exp_value - 1);


Rb = Rb0 ./ (1 + (j ./ Iv));
Ud_m = ((e(t_m) - y_m) ./ (Rb + R)) .* Rb + y_m;
line(t_arr, y_arr, "parent", ah, "color", "r");
line(t_m, Ud_m, "parent", ah);
line(t, et, "parent", ah, "color", "black");
legend("RK45", "ode45", "e(t)");