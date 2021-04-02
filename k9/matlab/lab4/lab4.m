global cnt A fi tau sigm omg t0


A = 10.0;
fi = pi ./ 4.0;
sigm = 1.0;
t0 = 4.0;
tau = 4.0;
omg = pi .* 2.0;


xFile1 = fopen('cmake-build-debug/data/x1.txt', 'r');
xFile2 = fopen('cmake-build-debug/data/x2.txt', 'r');
xFile3 = fopen('cmake-build-debug/data/x3.txt', 'r');
yFile1 = fopen('cmake-build-debug/data/y1.txt', 'r');
yFile2 = fopen('cmake-build-debug/data/y2.txt', 'r');
yFile3 = fopen('cmake-build-debug/data/y3.txt', 'r');
sFile1 = fopen('cmake-build-debug/data/s1.txt', 'r');
sFile2 = fopen('cmake-build-debug/data/s2.txt', 'r');
sFile3 = fopen('cmake-build-debug/data/s3.txt', 'r');
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
<<<<<<< HEAD
ah_s2 = axes(fh_s2);
ah_s3 = axes(fh_s3);
=======
ah_s12 = axes(fh_s1);

set(ah_s12, 'YAxisLocation', 'right');
ah_s2 = axes(fh_s2);
ah_s22 = axes(fh_s2);

set(ah_s22, 'YAxisLocation', 'right');

ah_s3 = axes(fh_s3);
ah_s32 = axes(fh_s3);
set(ah_s3, 'YAxisLocation', 'right');


set(ah_s22, 'Color', 'none');
set(ah_s32, 'Color', 'none');
set(ah_s12, 'Color', 'none');
>>>>>>> cca08da58216cf91d474f29db63ee72be3f58124

line(x1, y1, 'parent', ah_s1, 'color', 'r');
line(x1, s1, 'parent', ah_s12, 'color', 'b');
line(x2, s2, 'parent', ah_s22, 'color', 'b');
line(x2, y2, 'parent', ah_s2, 'color', 'r');
line(x3, y3, 'parent', ah_s3, 'color', 'r');
line(x3, s3, 'parent', ah_s32, 'color', 'b');



format longE
cnt = 0;
disp('quad:');
quad('v2', -pi ./ 2, pi ./ 2, 1e-6)
disp('function calls:');
format short
cnt

%format longE
%cnt = 0;
%disp('integral:');
%integral('v2',-pi ./ 2, pi ./ 2)
%disp('function calls:');
%format short
%cnt

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
		y(i) = s1_f(tpp) .* h(x(i) - tpp) + quad('fun', tpp, x(i) - s1_f(tpm), 1e-6) .* h(x(i)) - s1_f(tpm) * h(x(i) - tmax);
	else
		y(i) = s1_f(tpp) .* h(x(i) - tpp) + quad('fun', tpp, tpm - s1_f(tpm), 1e-6) .* h(x(i)) - s1_f(tpm) .* h (x(i) - tmax);
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
