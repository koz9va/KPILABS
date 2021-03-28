function F = Int2(x)
    global T;
    F = Perexid(T - x) .* Pvxid2(x);
end
