function F = Pvxid2(t)
    A = 3;
    t0 = 9;
    sigma = 3;
    F = -A .* (t - t0) .* exp(-(t - t0) .* (t - t0) ./ (sigma * sigma)) .* 2 ./ sigma ./ sigma;
end
