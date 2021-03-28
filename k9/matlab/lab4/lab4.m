global cnt A fi tau sigm omg t0


A = 10.0;
fi = pi ./ 4.0;
sigm = 1.0;
t0 = 4.0;
tau = 4.0;
omg = pi .* 2.0;


xFile1 = fopen('data/x1.txt', 'r');
xFile2 = fopen('data/x2.txt', 'r');
xFile3 = fopen('data/x3.txt', 'r');
yFile1 = fopen('data/y1.txt', 'r');
yFile2 = fopen('data/y2.txt', 'r');
yFile3 = fopen('data/y3.txt', 'r');
sFile1 = fopen('data/s1.txt', 'r');
sFile2 = fopen('data/s2.txt', 'r');
sFile3 = fopen('data/s3.txt', 'r');
y1 = fscanf(yFile1, '%lf', Inf);
y2 = fscanf(yFile2, '%lf', Inf);
y3 = fscanf(yFile3, '%lf', Inf);
s1 = fscanf(sFile1, '%lf', Inf);
s2 = fscanf(sFile2, '%lf', Inf);
s3 = fscanf(sFile3, '%lf', Inf);
x1 = fscanf(xFile1, "%lf", Inf);
x2 = fscanf(xFile2, "%lf", Inf);
x3 = fscanf(xFile3, "%lf", Inf);

fclose(yFile1);
fclose(yFile2);
fclose(yFile3);
fclose(sFile1);
fclose(sFile2);
fclose(sFile3);
fclose(xFile1);
fclose(xFile2);
fclose(xFile3);

fh_s1 = figure("name", "Signal one");
fh_s2 = figure("name", "Signal two");
fh_s3 = figure("name", "Signal three");
ah_s1 = axes(fh_s1);
hold on;
grid on;
ah_s2 = axes(fh_s2);
hold on;
grid on;
ah_s3 = axes(fh_s3);
hold on;
grid on;

line(x1, y1, 'parent', ah_s1, 'color', 'r');
line(x1, s1, 'parent', ah_s1, 'color', 'b');
line(x2, y2, 'parent', ah_s2, 'color', 'r');
line(x2, s2, 'parent', ah_s2, 'color', 'b');
line(x3, y3, 'parent', ah_s3, 'color', 'r');
line(x3, s3, 'parent', ah_s3, 'color', 'b');



format longE
cnt = 0;
disp('quad:');
quad('v2', -pi ./ 2, pi ./ 2, 1e-6)
disp('function calls:');
format short
cnt

format longE
cnt = 0;
disp('integral:');
integral('v2',-pi ./ 2, pi ./ 2)
disp('function calls:');
format short
cnt

format longE
cnt = 0;
disp('quadl:');
quadl('v2', -pi ./ 2, pi ./ 2, 1e-6)
disp('function calls:');
format short
cnt

tpp = 0.0;
tpm = pi ./ omg;

y = y1;
s = s1;
tmax = 1.0;

x = linspace(0, tmax, length(x1));

for i = 1:length(x)
	if(x(i) < pi ./ omg)
		y(i) = s1_f(tpp) .* h(x(i) - tpp) + quad('fun', tpp, x(i) - s1_f(tpm)) .* h(x(i)) - s1_f(tpm) * h(x(i) - tmax);
	else
		y(i) = s1_f(tpp) .* h(x(i) - tpp) + quad('fun', tpp, tpm - s1_f(tpm)) .* h(x(i)) - s1_f(tpm) .* h (x(i) - tmax);
	end
end
disp('Difference between C++ and matlab data:')
disp('S1:')
norm(abs(y1 - y))

tmax = 10;
x = linspace(0, tmax, length(x2));
y = y2;
for i = 1:length(x)
	if(x(i) < pi ./ omg)
		y(i) = s2_f(tpp) .* h(x(i) - tpp) + quad('fun', tpp, x(i) - s2_f(tpm)) .* h(x(i)) - s2_f(tpm) * h(x(i) - tmax);
	else
		y(i) = s2_f(tpp) .* h(x(i) - tpp) + quad('fun', tpp, tpm - s2_f(tpm)) .* h(x(i)) - s2_f(tpm) .*  h(x(i) - tmax);
	end
end
disp('S2:')
norm(abs(y2 - y))

tmax = 5;
x = linspace(0, tmax, length(x3));
y = y3;
for i = 1:length(x)
	if(x(i) < pi ./ omg)
		y(i) = s3_f(tpp) .* h(x(i) - tpp) + quad('fun', tpp, x(i) - s3_f(tpm)) .* h(x(i)) - s3_f(tpm) * h(x(i) - tmax);
	else
		y(i) = s3_f(tpp) .* h(x(i) - tpp) + quad('fun', tpp, tpm - s3_f(tpm)) .* h(x(i)) - s3_f(tpm) .*  h(x(i) - tmax);
	end
end
disp('S3:')
norm(abs(y3 - y))
