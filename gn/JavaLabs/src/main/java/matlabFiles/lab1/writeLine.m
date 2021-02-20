function writeLine(x, near)
    fh = figure();
    x = linspace(-x, x);
    qwe = zeros(size(x));
    handler = axes(fh);
    y = nonLinealFunc(x);
    line(x,
        y,
        'LineStyle', '--',
        'parent', handler
        );
    line(x,
        qwe,
        'parent', handler
        );
    set(handler, 'Xlabel', 'XValue', 'Ylabel', 'YValue');
    hold on
    scatter(fzero("nonLinealFunc", near), 0, 30, 'filled','parent', handler);
end