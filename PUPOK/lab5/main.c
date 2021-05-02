#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <float.h>
#define pi (float)M_PI

int counter;

const float a[] = {0.0f, 1.0f/5.0f, 3.0f/10.0f, 3.0f/5.0f, 1.0f, 7.0f/8.0f};
const float c[] = {37.0f/378.0f, 0.0f, 250.0f/621.0f, 125.0f/594.0f, 0, 512.0f/1771.0f};
const float c_z[] = {2825.0f/27648.0f, 0.0f, 18575.0f/48384.0f, 13525.0f/55296.0f, 277.0f/14336.0f, 1.0f/4.0f};
const float b[][5] = {
	{1.0f/5.0f},
	{3.0f/40.0f, 9.0f/40.0f},
	{3.0f/10.0f, -9.0f/10, 6.0f/5.0f},
	{-11.0f/54.0f, 5.0f/2.0f, -70.0f/27.0f, 35.0f/27.0f},
	{1631.0f/55296.0f, 175.0f/512.0f, 575.0f/13824.0f, 44275.0f/110592.0f, 253.0f/4096.0f}
	};

int odeEuler(float f(float t, float y), float tend, float y0, float eps, float *t, float *y, int nmax, float h_max) {
	int i = 0;
	float h = tend;

	y[0] = y0;
	t[0] = 0;

	do {
		float y1, y2, y3, R;

		y2 = y[i] + h * f(t[i], y[i]);
		do {
			h /= 2;
			y1 = y2;
			y2 = y[i] + h * f(t[i], y[i]);
			y3 = y2 + h * f(t[i] + h, y2);
			R = y3 - y1;
		} while(fabsf(R) > eps * fabsf(y3));
		++i;
		if(i >= nmax)
			return nmax;
		h *= 2.0f;
		y[i] = y3;
		t[i] = t[i - 1] + h;

		if(fabsf(R) < eps * fabsf(y[i]) && h < h_max / 2.f) h *= 2.f;

	} while(t[i] < tend);
	return i;
}


int odeEuler_n(float f(float t, float y), float tend, float y0, float eps, float *t, float *y, int nmax) {
	int i = 0;
	float h = tend/20.f;

	y[0] = y0;
	t[0] = 0.f;

	do {
		float R, y2;

		do {
			float y1 = y[i] + h * f(t[i], y[i]), dy;

			y2 = y1;

			do {
				float dt = sqrtf(FLT_EPSILON) * y2, fx = f(t[i] + h, y2);
				dy = -(y2 - y[i] - h * fx) / (1.f - h * (f(t[i] + h, y2 + dt) - fx) / dt);
				y2 += dy;
			} while(eps * fabsf(y2) < fabsf(dy));

			h /= 2.f;
			R = y2 - y1;

		} while(eps * fabsf(y2) < fabsf(R));
		h *= 2.f;
		++i;
		if(i >= nmax) return nmax;

		if(eps * fabsf(y2) / 4.f > fabsf(R)) h *= 2.f;

		y[i] = y2;
		t[i] = t[i - 1] + h;
	} while(t[i] < tend);

	return i;
}

int Runge_Kutta(float f(float t, float y), float tend, float y0, float eps, float *t, float *y, int nmax, float h_max) {
	int i, j;
	float h;

	y[0] = y0;
	t[0] = 0;
	i = 0;
	h = tend;
	do {
		float R, y2;
		do {
			float k[6];

			if(h > 2.0f * FLT_EPSILON) {
				h /= 2.0f;
			} else break;

			k[0] = f(t[i], y[i]);
			k[1] = f(t[i] + a[1] * h, y[i] + h * b[0][0]);
			k[2] = f(t[i] + a[2] * h, y[i] + h * (b[1][0] * k[0] + b[1][1] * k[1]));
			k[3] = f(t[i] + a[3] * h, y[i] + h * (b[2][0] * k[0] + b[2][1] * k[1] + b[2][2] * k[2]));
			k[4] = f(t[i] + a[4] * h, y[i] + h * (b[3][0] * k[0] + b[3][1] * k[1] + b[3][2] * k[2] + b[3][3] * k[3]));
			k[5] = f(t[i] + a[5] * h, y[i] + h * (b[4][0] * k[0] + b[4][1] * k[1] + b[4][2] * k[2] + b[4][3] * k[3] + b[4][4] * k[4]));
			y2 = 0.0f;
			R = 0.0f;
			for(j = 0; j < 6; j++) {
				y2 += c[j] * k[j];
				R += (c[j] - c_z[j]) * k[j];
			}	

			R *= h;
			y2 = h * y2 + y[i];
		} while(eps * fabsf(y2) < fabsf(R));
		++i;
		
		if(i >= nmax) break;

		y[i] = y2;
		t[i] = t[i - 1] + h;

		if(eps * fabsf(y2) > fabsf(R) && h < h_max / 2.f) h *= 2.f;

	} while(t[i] < tend);
	
	return i;
}


float task3(float t, float uvih) {
	const float E0 = 100.f, f = 5e3f, phi = pi/3.f, R = 3e3f, g = 1e-3f, C = 0.4e-6f;
	float e, ud, id;
	++counter;
	e = E0 * sinf(2.f * pi * f * t + phi);
	ud = e - uvih;
	id = 0;
	if(ud > 0) id = g * ud * sqrtf(ud);
	return (id - uvih / R) / C;
}

int main() {
	int nmax, n, i;
	float *y, *t;
	FILE *y_f, *t_f;
	
	printf("Enter nmax:\n");
	scanf("%d", &nmax);

	y = malloc(sizeof(float) * nmax);
	t = malloc(sizeof(float) * nmax);

	if(!y || !t) {
		printf("Error in allocating memory\n");
		return -1;
	}

	y_f = fopen("uvih_data.txt", "w");
	t_f = fopen("t_data.txt", "w");

	if(!y_f || !t_f) {
		printf("Can't open file");
		free(y);
		free(t);
		return -1;
	}
	counter = 0;
	n = Runge_Kutta(task3, 0.6e-3f, 0.f, 1e-6f, t, y, nmax, 0.6e-3f);
	printf("Function called: %d, Found %d points\n", counter, n);
	counter = 0;
	n = odeEuler_n(task3, 0.6e-3f, 0.f, 1e-6f, t, y, nmax);
	printf("Function called: %d, Found %d points\n", counter, n);
	counter = 0;
	n = odeEuler(task3, 0.6e-3f, 0.f, 1e-6f, t, y, nmax, 0.6e-3f);
	printf("Function called: %d, Found %d points\n", counter, n);

	if(n >= nmax) {
		printf("Too less points are allocated\n");
	}

	for(i = 0; i < n; ++i) {
		fprintf(y_f, "%e\n", y[i]);
		fprintf(t_f, "%e\n", t[i]);
	}	

	fclose(y_f);
	fclose(t_f);

	free(y);
	free(t);

	return 0;
}
