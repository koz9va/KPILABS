global counter
ah = axes(figure());
hold on;
grid on;


file_y = fopen("y.txt", "r");
file_t = fopen("t.txt", "r");

y = fscanf(file_y, "%lf", Inf);
t = fscanf(file_t, "%lf", Inf);
fclose(file_y);
fclose(file_t);

line(t, y, "color", "r");
counter = 0;
[t_m, uvih] = ode45('fun', [0 0.6e-3], 0);
disp('Points found:');
length(t_m);
disp('Calls to function:');
counter
line(t_m, uvih, "color", "b");