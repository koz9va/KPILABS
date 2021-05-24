global count
ah = axes(figure());
hold on;
grid on;


file_y = fopen("C:/Users/min/IdeaProjects/KPILABS/JavaLabs/y.txt", "r");
file_t = fopen("C:/Users/min/IdeaProjects/KPILABS/JavaLabs/t.txt", "r");

y = fscanf(file_y, "%lf", Inf);
t = fscanf(file_t, "%lf", Inf);
fclose(file_y);
fclose(file_t);

line(t, y, "color", "r");
count = 0;
[t_m, uvih] = ode45('func', [0 4e-3], 0, odeset('RelTol',1e-6));
disp('Points found:');
length(t_m)
disp('Calls to function:');
count
line(t_m, uvih, "color", "b");

E0 = 50;
f = 1e3;
phi = pi/4;
R = 4e3;
C = 1e-6;
g = 0.8e-3;
t = linspace(0, 4e-3, 5000);
e = E0 .* sin(2 .* pi .* f .* t + phi);

line(t, e, 'color', 'black')


legend('Java', 'Matlab')
