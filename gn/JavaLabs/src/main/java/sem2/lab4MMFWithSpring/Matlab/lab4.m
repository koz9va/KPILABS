
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
tmax = 1/3;
t = linspace(tmin, tmax + 0.2, numb);
y = zeros(size(t));
for i = 1:numb
    T = t(i);
    if(t(i) <= tmax)
        y(i) = vxid1(tmin) * Perexid(t(i) - tmin) + integral(@Int1, tmin, t(i)) - vxid1(tmax) * Perexid(t(i) - tmax);
    else
        y(i) = vxid1(tmin) * Perexid(t(i) - tmin) + integral(@Int1, tmin, tmax) - vxid1(tmax) * Perexid(t(i) - tmax);
    end
end

plot(t, [y; vxid1(t)], t1, r1);
grid on

figure();
tmax = 20;
t = linspace(tmin, tmax, numb);
for i = 1:numb;
    T = t(i);
    y(i) = vxid2(tmin) * Perexid(t(i) - tmin) + integral(@Int2, tmin, t(i)) - vxid2(tmax) * Perexid(t(i) - tmax);
end
plot(t, [y;vxid2(t)] , t2, r2);
grid on;

figure();
tmax = 3;
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
        y(i) = vxid3(tmin) * Perexid(t(i) - tmin) + integral(@Int3, tmin, 1) - vxid3(1 - eps) * Perexid(t(i) - 1 - eps);
        y(i) = y(i) + vxid3(1) * Perexid(t(i) - 1) + integral(@Int3, 1, t(i)) - vxid3(3) * Perexid(t(i) - 3);
    else
        y(i) = vxid3(tmin) * Perexid(t(i) - tmin) + integral(@Int3, tmin, 1) - vxid3(1 - eps) * Perexid(t(i) - 1 - eps);
        y(i) = y(i) + vxid3(1) * Perexid(t(i) - 1) + integral(@Int3, 1, 3) - vxid3(3) * Perexid(t(i) - 3);
        y(i) = y(i) + vxid3(3 + eps) * Perexid(t(i) - (3 + eps)) + integral(@Int3, 3, t(i)) - vxid3(tmax) * Perexid(t(i) - tmax);
    end
    end
end

plot(t,[y;vxid3(t)], t3, r3);
grid on;