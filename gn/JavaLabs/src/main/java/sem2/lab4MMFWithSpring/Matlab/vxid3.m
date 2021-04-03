function F = vxid3(t)
    F(t < 1) = 2 - 2 * t(t < 1);
    F(t >= 1 & t <= 3) = t(t >= 1 & t <= 3) - 1;
    F(t > 3) = 0;
end
