function y = funkforpunkt1(x)
%    y = 334.75 * (exp(-30038 * x) - exp(-164 * x));
    y = (0.5976 .* exp(0.1 .* x) .* cos(0.55677 .* x - 0.344) - 0.5976 .* exp(0.1 .* x) .* cos(0.55677 .* x + 0.344) - 3.6369 .* exp(0.1 .* x) .* sin(0.55677 .* x));
end
