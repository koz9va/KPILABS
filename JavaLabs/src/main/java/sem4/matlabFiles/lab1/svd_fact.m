function [x, Condition] = svd_fact(inputMat, inputB)
    [U, S, V] = svd(inputMat);

    C = U' * inputB;

    max = S(1,1);
    min = S(1,1);
    for i = 1:size(S,1)
        if S(i,i) > max
            max = S(i,i);
        end
        if S(i,i) < min
            min = S(i,i);
        end
    end

    t = eps * max;
    Y = zeros(size(S, 1), 1);
    for i = 1:size(S,1)
        if S(i,i) > t
            Y(i) = C(i) / S(i,i);
        else
            Y(i) = 0;
        end
    end
    x = V * Y;

    Condition = max / min;
end
