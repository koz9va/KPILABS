file = fopen('data.txt', 'r');
n = fscanf(file, '%d', 'C');
matrix = zeros(n);
B = zeros(n, 1);
for i = 1:n
    for j = 1:n
        matrix(i, j) = fscanf(file, "%f", 'C');
    end
end

for i = 1:n
    B(i, 1) = fscanf(file, '%f', 'C');
end
fclose(file);
[L, U, P] = lu(matrix);

B_lu = P * B;

x_lu = zeros(numel(B_lu), 1);

n = numel(B_lu);

for i = 1:n
    x_lu(i) = (B_lu(i) - L(i, 1:i-1) * x_lu(1:i-1));
end

my_zero = eps * abs(max(diag(U)));
for i = n:-1:1
    if abs(U(i, i)) > my_zero
        x_lu(i) = (x_lu(i) - U(i, i+1:n) * x_lu(i+1:n)) / U(i, i);
    else
        disp('Matrix is singular\nCalculating with SVD decomposition...');
        x_lu = SVD_roots(matrix, B);
        break
    end
end

x_lu

[Q, R] = qr(matrix);

B_qr = Q' * B;

x_qr = zeros(size(B_qr));

my_zero = eps * abs(max(diag(R)));

for i = n:-1:1
    if(abs(R(i, i)) > my_zero)
        x_qr(i) = (B_qr(i) - (R(i, i + 1:n) * x_qr(i+1:n))) / R(i, i);
    else
        disp('Matrix is singular\nCalculating with SVD decomposition...');
        x_qr = SVD_roots(matrix, B);
        break
    end
end
x_qr

R = chol(matrix);
x_chol = zeros(size(B));
R1 = R';
zero_r = eps * abs(max(diag(R)));
zero_r1 = eps * abs(max(diag(R1)));
abort = 0;
for i = 1:n
    if (abs(R1(i, i)) > zero_r1)
        x_chol(i) = (B(i) - R1(i, 1:i-1) * x_chol(1:i-1)) / R1(i, i);
    else
        disp('Matrix is singular\nCalculating with SVD decomposition...');
        abort = 1;
        break
    end
end
if abort == 0
  
  for i = n:-1:1
      if(abs(R(i, i)) > zero_r)
          x_chol(i) = (x_chol(i) - (R(i, i + 1:n) * x_chol(i+1:n))) / R(i, i);
      else
        disp("Matrix is singular\nCalculating with SVD decomposition...");
        x_chol = SVD_roots(A, B);
          break
      end
  end
else
  x_chol = SVD_roots(A, B);
end

x_chol


x_svd = SVD_roots(matrix, B)

determinant = det(matrix)

conditional = eig(matrix)

root_of_eq = fzero("x_eq", 1)
% Start of the plotting
fh = figure();
x = linspace(root_of_eq-(root_of_eq/2), root_of_eq+(root_of_eq/2));
ah = axes(fh);
y = x_eq(x);
set(ah, "xlabel", "U_{ds}", "ylabel", 'I_d');
lh = line("parent", ah, "xdata", x, "ydata", y);
hold on;
grid on;
hs = scatter(root_of_eq, x_eq(root_of_eq), 100, "parent", ah, "s", "filled");
% End of the plotting

x0_sne = fsolve('SNEF', [5, 0.5])

test_of_roots = SNEF(x0_sne)
