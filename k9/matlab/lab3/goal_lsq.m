function retval = goal_lsq(x)
    global U C
    n = x(1);
    fi0 = x(2);
    fx = C(1) ./ (1 + U / fi0) .^ n;
    retval = C - fx;
end
