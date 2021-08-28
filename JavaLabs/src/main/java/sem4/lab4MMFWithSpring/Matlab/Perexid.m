function F = Perexid(t)
    if t < 0
        F = 0;
    else
        F = 1/3 * (1 - exp(t / -2 / 3));
    end
end
