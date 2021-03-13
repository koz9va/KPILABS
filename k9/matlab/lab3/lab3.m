beta = 0.2;
g22 = 1e-3;
U0 = 3;
Uzv1 = 3.5;
Uzv3 = 5.5;
Usv1 = 0;
Usv7 = 12;

inter_methods = {"nearest"; "linear"; "pchip"; "next"};

global U C

Uzv = linspace(Uzv1, Uzv3, 3);
Usv = linspace(Usv1, Usv7, 7);

nzv = 3;
nsv = 7;

I = zeros(nzv, nsv);
X =  linspace(Usv1, Usv7);

table_f = figure('name', 'Mosfet Table');
table_ax = axes(table_f);
grid on;

for i = 1:nzv
    for j = 1:nsv
        if(Uzv(i) <= U0)
            I(i, j) = g22 .* Usv(j);
        elseif Usv(j) < (Uzv(i) - U0)
            I(i, j) = beta .* (2 .* (Uzv(i) - U0) - Usv(j)) .* Usv(j) + g22 .* Usv(j);
        else
            I(i, j) = beta .* (Uzv(i) - U0) .* (Uzv(i) - U0) + g22 .* Usv(j);
        end
    end
    for k = 1:numel(inter_methods)
    	Y = interp1(Usv, I(i, 1:nsv), X, inter_methods{k});
	line(X, Y, 'parent', table_ax);
	hold on;
    end
    
	scatter(Usv, I(i, 1:nsv), 'parent', table_ax, 'o', 'filled');
end

format longE;

UFile = fopen('U.txt', 'r');
U = fscanf(UFile, "%lf", Inf);
fclose(UFile);
CFile = fopen('C.txt', 'r');
C = fscanf(CFile, "%lf", Inf);
fclose(CFile);

data_f = figure('name', 'Polynomial aproximation');

data_f_ax = axes(data_f);

grid on;
scatter(U, C, 'filled');
hold on;
C0 = C(1);

n = 1;
for i = 1:numel(U)
    if(U(i) >= 6)
        n = i;
        break;
    end
end

U_sh = U(n:numel(U));
C_sh = C(n:numel(C));

max_U = max(U);

U_lin = log(U_sh);
C_lin = log(C0 ./ C_sh);

x_lin = polyfit(C_lin, U_lin, 1);

x = linspace(0, max_U);

disp('Linear approximation');

n = 1 ./ x_lin(1)
fi = exp(x_lin(2))

line(x, diod(C0, fi, n, x), 'parent', data_f_ax, 'color', 'red');

scatter(U, C, 'filled');

root = lsqnonlin('goal_lsq', [n, fi]);

disp('Nonlinear approximation');
n = root(1)
fi0 = root(2)

x = linspace(0, max_U);
y = diod(C0, fi0, n, x);
line(x, y, 'parent', data_f_ax, 'color', 'blue');
