Uzv1 = 4;
Uzv3 = 6;
Usv1 = 0;
Usv7 = 12;
U0 = 3.5;
g22 = 2e-6;
betta = 0.3e-3;

Uzv = linspace(Uzv1, Uzv3, 3);
Usv = linspace(Usv1,Usv7, 7);
UsvLong = linspace(Usv1,Usv7);

fh = figure();
handler = axes(fh);
I = zeros(size(Usv, 2), size(Usv, 2));
for i = 1:size(Uzv, 2)
    for j = 1:size(Usv, 2)
        if (Uzv(i) <= U0)
            I(i, j) = g22 .* Usv(j);
        elseif (Usv(j) < (Uzv(i) - U0))
            I(i, j) = betta .* (2 .* (Uzv(i) - U0) - Usv(j)) .* Usv(j) + g22 .* Usv(j);
        else
        I(i, j) = betta .* (Uzv(i) - U0) .* (Uzv(i) - U0) + g22 .* Usv(j);
        end
    end
    y = interp1(Usv, I(i, 1:size(Usv, 2)), UsvLong, 'cubic');
    scatter(Usv, I(i, 1:size(Usv, 2)), 'filled', 'parent', handler);
    hold on
    grid on
    line(UsvLong, y, 'parent', handler);
end
