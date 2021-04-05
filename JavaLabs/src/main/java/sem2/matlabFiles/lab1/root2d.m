function F = root2d(x)

    i0 = 10e-9;
    iv = 1e-3;
    rb0 = 2e3;
    E = 5.0;
    R = 4e3;
    Fi = 26e-3;
    m = 1.5;

    Rb = rb0 / (1 + x(1) / iv);
    F(1) = x(1) - i0 * (exp((x(2) - x(1) * Rb) / (m * Fi)) + 1.0);
    F(2) = E - x(2) - x(1) * R;

end
