function F = System2(x1, x2)

    F = (x1 - x2 - 2) .* (x1 - x2 - 2) .* (x1 - x2 - 2) .* (x1 - x2 - 2) + (2 .* x1 .* x1 + x2 - 1) .* (2 .* x1 .* x1 + x2 - 1);

end
