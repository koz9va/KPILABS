function F = Pvxid1(t)
    omega = 3 * pi;
    fi = pi / 4;
    A = 3;
    if t <= 0.5
        F = A * omega * cos(omega * t + fi);
    else
        F = 0;
    end
end
