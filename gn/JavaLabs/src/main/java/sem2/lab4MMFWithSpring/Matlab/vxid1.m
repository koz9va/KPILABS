function F = vxid1(t)
    omega = 3 * pi;
    fi = pi/4;
    A = 3;
    F = zeros(size(t));
    F (t <= 0.5) = A * sin(omega * t(t<= 0.5) + fi);
    F(t > 0.5) = 0;
end
