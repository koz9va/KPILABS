//
// Created by koz9va on 27.11.20.
//

#ifndef LAB4_SLAU_H
#define LAB4_SLAU_H
#include <iostream>
#include <cmath>
#include <cstring>

namespace lin {
	class vector {
	public:
		double *ptr;
		int n;
		explicit vector(int N);
		vector(vector &ref);
		~vector();
		vector& operator -= (const  vector& rhs);
		double& operator [] (int index) const;
		double normalize() const;
	};
	class matrix {
	public:
		double **ptr;
		int n;
		explicit matrix(int N);
		~matrix();
		matrix& operator = (const matrix &ref);
		void QR(lin::vector &B, lin::vector &X) const;
	};



}


#endif //LAB4_SLAU_H
