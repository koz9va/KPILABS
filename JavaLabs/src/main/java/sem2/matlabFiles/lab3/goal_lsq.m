function dif = goal_lsq(x, U, C)
global U1 C1
n = x(1);
fio = x(2);
fx = C1(1) ./ (1 + U1 / fio) .^ n;
dif = C1 - fx;
end
