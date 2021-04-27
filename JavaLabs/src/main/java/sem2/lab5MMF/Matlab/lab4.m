global cnt
addpath("functions");

t_file = fopen("C:\\Users\\min\\IdeaProjects\\KPILABS\\JavaLabs\\t.txt", "r");
y_file = fopen("C:\\Users\\min\\IdeaProjects\\KPILABS\\JavaLabs\\y.txt", "r");

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
j = 5000e-12 .* (exp(y_m ./ 1.7 ./ 26-3) - 1);


Rb = 1e3 ./ (1 + (j ./ 0.3e-3));
Ud_m = ((e(t_m) - y_m) ./ (Rb + 1e3)) .* Rb + y_m;
line(t_arr, y_arr, "parent", ah, "color", "r");
line(t_m, Ud_m, "parent", ah);
line(t, et, "parent", ah, "color", "black");
legend("RK45", "ode45", "e(t)");