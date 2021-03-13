root_ne = fminsearch('NE', 0)
x1 = linspace(root_ne/2, root_ne + (root_ne/2));
y1 = NE(x1);

fh2d = figure();
ah2d = axes(fh2d);
set(ah2d, 'xlabel', 'X', 'ylabel', 'Y');
lh = line('parent', ah2d, 'xdata', x1, 'ydata', y1);
hold on;
grid on;
hs = scatter(root_ne, NE(root_ne), 100, 'parent', ah2d, 's', 'filled');

options = optimset('TolFun', 1e-9)
roots = fminunc('SNE', [1 1], options)
range1 = linspace(roots(1)/2, roots(1) + (roots(1)/2));
range2 = linspace(roots(2)/2, roots(2) + (roots(2)/2));
[X Y] = meshgrid(range1, range2);
Z = SNE(X, Y);
z_of_roots = SNE(roots)
fh3d = figure();
axh3d = axes(fh3d);
hs = surf(X, Y, Z, 'parent', axh3d);
hold on;
shading interp;

scatter3(roots(1), roots(2), z_of_roots, 150, 'filled');