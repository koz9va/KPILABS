function roots_svd = SVD_roots(A, B)
	[U, S, V] = svd(A);
	C = U' * B;

	tau = eps * max(diag(S));
	Y = zeros(size(B));

	for i = 1:size(B, 1)
		if(S(i, i) > tau)
			Y(i) = C(i) / S(i, i);
		else
			Y(i) = 0;
		end
	end

	roots_svd = V * Y;
end
