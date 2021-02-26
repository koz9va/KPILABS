

min = fminsearch('System', [0, 0])
[X1, X2] = meshgrid(linspace((min(1) - 0.5), (min(1) + 0.5)),linspace((min(2) - 0.5), (min(2) + 0.5)));
Z = System2(X1, X2);
subplot(111)
surf(X1, X2, Z)
hold on
shading interp;

scatter3(min(1), min(2), System(min), 'filled');

subplot(111)
writeLine2(2, 1)