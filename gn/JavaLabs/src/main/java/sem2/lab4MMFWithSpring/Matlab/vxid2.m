function F = vxid2(t)
    A = 3;
    t0 = 9;
    sigma = 3;
    F = A .* exp (-(t - t0) .* (t - t0) ./ (sigma * sigma));
end
