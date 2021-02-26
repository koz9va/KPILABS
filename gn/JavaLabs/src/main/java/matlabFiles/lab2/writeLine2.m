function x = writeLine2(x, near)
    fh = figure();
    x = linspace(-x, x);
    handler = axes(fh);
    y = functionlab2(x);
    line(x,
        y,
        'parent', handler,
        'color', 'red'
        );
    set(handler, 'Xlabel', 'XValue', 'Ylabel', 'YValue');
    hold on
    grid on
    x = fminsearch('functionlab2', near);
    y = functionlab2(x);
    scatter(x, y, 30, 'filled','parent', handler);
end