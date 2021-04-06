infun = @(x) (log(2 - sin(x))) ./ (0.1 + tan(x)^2);
I = integral(infun, 0, pi/2.5, 'ArrayValued', true);
display(I);

t1 = textread('G:/t1.txt');
t2 = textread('G:/t2.txt');
t3 = textread('G:/t3.txt');
r1 = textread('G:/y1.txt');
r2 = textread('G:/y2.txt');
r3 = textread('G:/y3.txt');
global T
numb = 100;
tmin = 0;
tmax = 1;
dev = 0.5;
t = linspace(tmin, tmax + 0.2, numb);
y = zeros(size(t));
for i = 1:numb
    T = t(i);
    if(t(i) <= dev)
        y(i) = vxid1(tmin) * Perexid(t(i) - tmin) + integral(@Int1, tmin, t(i)) - vxid1(tmax) * Perexid(t(i) - tmax);
    else
        y(i) = vxid1(tmin) * Perexid(t(i) - tmin) + integral(@Int1, tmin, dev) - vxid1(dev) * Perexid(t(i) - dev);
    end
end

%plot(t, [y; vxid1(t)]);

f = figure();
a = axes(f);
b = axes(f);
set(a, 'YAxisLocation', 'right');
set(b, 'Color', 'none');

line(t, y, 'parent', a);
line(t, vxid1(t), 'parent', b);
grid on

figure();
tmax = 20;
t = linspace(tmin, tmax, numb);
for i = 1:numb;
    T = t(i);
    y(i) = vxid2(tmin) * Perexid(t(i) - tmin) + integral(@Int2, tmin, t(i)) - vxid2(tmax) * Perexid(t(i) - tmax);
end
plot(t, [y;vxid2(t)]);
grid on;

figure();
tmax = 10;
t = linspace(tmin, tmax + 1, numb);
for i = 1:numb
    T = t(i);
    if(t(i) < 1)
        one = vxid3(tmin);
        two = Perexid(t(i) - tmin);
        three = integral(@Int3, tmin, t(i));
        four = vxid3(1 - eps);
        five = Perexid(t(i) - 1 - eps);
        y(i) = one * two + three - four * five;
    else if(t(i) <= 3)
        y(i) = vxid3(0) * Perexid(t(i)) + integral(@Int3, 0, 1 - 2 * eps) - vxid3(1 - 2 * eps) * Perexid(t(i) - 1 + 2 * eps) + vxid3(1 + 2 * eps) * Perexid(t(i) - 1 + 2 * eps) + integral(@Int3, 1 + 2 * eps, t(i)) - vxid3(3 - 2 * eps) * Perexid(t(i) - 3 + 2 * eps);
    else
        y(i) = vxid3(0) * Perexid(t(i)) + integral(@Int3, 0, 1 - 2 * eps) - vxid3(1 - 2 * eps) * Perexid(t(i) - 1 + 2 * eps) + vxid3(1 + 2 * eps) * Perexid(t(i) - 1 - 2 * eps) + integral(@Int3, 1 + 2 * eps, 3 - 2 * eps) - vxid3(3 - 2 * eps) * Perexid(t(i) - 3 + 2 * eps);
    end
    end
end

plot(t,[y;vxid3(t)]);
grid on;