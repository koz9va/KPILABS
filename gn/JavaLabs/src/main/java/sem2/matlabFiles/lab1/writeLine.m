function writeLine(x, near)
    fh = figure();
    x = linspace(-x, x);
    qwe = zeros(size(x));
    handler = axes(fh);
    y = nonLinealFunc(x);
    line(x,
        y,
        'parent', handler,
        'color', 'red'
        );
    line(x,
        qwe,
        'LineStyle', '--',
        'parent', handler,
        'color', 'black'
    );
    set(handler, 'Xlabel', 'XValue', 'Ylabel', 'YValue');
    hold on
    scatter(fzero("nonLinealFunc", near), 0, 30, 'filled','parent', handler);
end