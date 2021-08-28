global M;
global q;
global alpha0;

M = 16;
q = 0.3;
alpha0 = 0.3;

[t, y] = ode45("tmp_fun", [0, 5], [2 * pi / M * (1:M), -alpha0 * sin(2 * pi / M * (1:M))]);

F = y(1:numel(y) / 2);

i1 = zeros(size(t));

for j = 1 : size(t)
    for k = 1:M
        i1(j) += exp((0 - 1i) * y(j, k));
    end
end
i = i1 / M;

[max, a] = max(i)
plot(t(1:a), i(1:a))
xlabel("X")
ylabel("i1(X)")
title("lab7(title)")