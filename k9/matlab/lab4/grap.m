xFile = fopen('x.txt', 'r');
x = fscanf(xFile, "%lf", Inf);
fclose(xFile);
yFile = fopen('y.txt', 'r');
y = fscanf(yFile, '%lf', Inf);
fclose(yFile);
sFile = fopen('s.txt', 'r');
s = fscanf(sFile, "%lf", Inf);
fclose(sFile);
fh = figure();
ahy = axes(fh);
hold on;
%ahs = axes(fh);
grid on;
hold on;
line(x, y, 'parent', ahy, 'color', 'r');
line(x, s, 'parent', ahy, 'color', 'b');
