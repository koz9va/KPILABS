function writeLine(x, near)
    fh = figure();
    x = linspace(-x, x);
    qwe = zeros(size(x));
    ah = axes(fh);
    y = nonLinealFunc(x);
    lh = line('xdata', x,
    'ydata', y,
    'LineStyle', '--',
    'parent', ah
    );
    lg = line('xdata', x,
    'ydata', qwe,
    'parent', ah
    );
    hold on
    hs = scatter(fzero("nonLinealFunc", near), 0, 30, 'filled','parent', ah);
end