test_f = @(t) 2 ./ (((t + 1) .* (t + 1)) + 1);

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

t_m = linspace(0, 1);
y_m = test_f(t_m);

line(t_arr, y_arr, "parent", ah, "color", "r");
line(t_m, y_m, "parent", ah);


