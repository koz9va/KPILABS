A = [5 2 3;2 6 1;3 1 7];
B = [15;14;13];
DetA = det(A)
NevVec = eig(A, 'matrix')

x = LU_factorization(A, B)
x = qr_factorization(A, B)
x = choles_factorization(A, B)
[x, result] = singularity(A, B)
x = sne(fsolve('sne', [0.6, 0.6]))

graphic(300)







function x = qr_factorization(A, B)
    [q, r] = qr(A);
    b1 = q' * B;
    x = zeros(size(B));
    n = size(A, 1);

    tau = eps * max(diag(r));

    for (i = n:-1:1)
        if (abs(r(i, i)) > tau)
            x(i) = (b1(i) - r(i, i + 1:n) * x(i + 1:n)) / r(i,i);
        else
            disp('svd ');
            break;
        end
    end
end



function x = choles_factorization(A, B)
    R = chol(A);
    Rt = R';
    x = zeros(size(B));
    n = size(A, 1);

    for i = 1:n
        x(i) = (B(i) - Rt(i, 1:i - 1) * x(1 : i - 1)) / Rt(i, i);
    end

    tau = eps * max(diag(R));

    for i = n:-1:1
        if (abs(R(i, i)) > tau)
            x(i) = (x(i) - (R(i, i + 1:n) * x(i + 1:n))) / R(i, i);
        else
            disp('svd');
            break;
        end
    end
end



function [x, result] = singularity(A, B)
    [u, s, v] = svd(A);

    C = u' * B;

    dmax = max(diag(s));
    dmin = min(diag(s));
    result = dmax / dmin;

    t = eps * dmax;
    Y = zeros(size(s, 1), 1);
    for i = 1:size(s,1)
        if s(i,i) > t
            Y(i) = C(i) / s(i,i);
        end
    end
    x = v * Y;
end


function y = nonlinear_function(x)

    y = log(4.9) + 15 * log(10) + 0.75 * log(0.049) + 1.5 * log(x) - (1.6e-19 * 1.2) ./ (2 * 1.38e-23 * x) - log(2) - 10 * log(10);

end




function Y = sne(X)

    e = 6.0;
    r = 1.5e3;
    i01 = 2.0e-9;
    i02 = 5.0e-9;
    m1 = 1.5;
    m2 = 1.7;
    phi = 26.0e-3;

    id1 = i01 * (exp(X(1) / (m1 * phi)) - 1);
    id2 = i02 * (exp(X(2) / (m2 * phi)) - 1);

    Y(1) = id1 - id2;
    Y(2) = e - X(1) - X(2) - id1 * r;

end

function graphic(rnd)

    fh = figure();
    x = linspace(300, 400);
    handler = axes(fh);
    y = nonlinear_function(x);
    line(x, y, 'parent', handler, 'color', 'blue');
    set(handler, 'Xlabel', 'XValue', 'Ylabel', 'YValue');
    hold on
    grid on
    scatter(fzero("nonlinear_function", rnd), 0, 30, 'filled','parent', handler);

end