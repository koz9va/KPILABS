function retval = diod(C0, fi0, n, U)
    retval = C0 ./ ((1 + U ./ fi0) .^ n);
end
