global count
ah = axes(figure());
hold on;
grid on;


file_y = fopen("/home/min/IdeaProjects/KPILABS/JavaLabs/y.txt", "r");
file_t = fopen("/home/min/IdeaProjects/KPILABS/JavaLabs/t.txt", "r");

y = fscanf(file_y, "%lf", Inf);
t = fscanf(file_t, "%lf", Inf);
fclose(file_y);
fclose(file_t);

line(t, y, "color", "r");
count = 0;
[t_m, uvih] = ode45('func', [0 4e-3], 0, odeset('RelTol',1e-6));
disp('Points found:');
length(t_m);
disp('Calls to function:');
count
line(t_m, uvih, "color", "b");

legend('Java', 'Matlab')
