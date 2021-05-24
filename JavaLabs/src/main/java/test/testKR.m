infun = @(x) (8 * 1e6 * x + 2)
infun1 = @(x) (8 * 1e6 * x + 2)

T = 1e-6;

integral(infun,-T/2, T/2, 'ArrayValued', true)