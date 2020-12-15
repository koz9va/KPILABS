package lab6;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		double[] oldArrU = new double[]{0.0, 0.5, 1.0, 3.0, 4.5, 6.0, 7.5, 9.5, 12.0, 16.0, 20.0, 25.0, 30.0};
		double[] oldArrC = new double[]{9.4e-12, 7.7e-12, 6.8e-12, 5.0e-12, 4.4e-12, 4.1e-12, 3.8e-12, 3.5e-12, 3.2e-12, 2.9e-12, 2.7e-12, 2.4e-12, 2.3e-12};

		double[] newU = new double[8];
		double[] newC = new double[8];

		cutTable(oldArrU,oldArrC,newU,newC);

		double [] arr = function(newU, newC, 1);

		returnUnknown(arr);

		System.out.println(Arrays.toString(arr));
		System.out.println("Fi " + "N" );


	}

	public static void cutTable(double[] U1, double[] C1, double[] U2, double[] C2) {

		for (int i = 5; i < U1.length; i++) {
			U2[i - 5] = Math.log(U1[i]);
			C2[i - 5] = Math.log(C1[0]/C1[i]);

		}
	}

	public static double[] function(double[] C, double[] U, int polinomPower) {
		double pow;
		polinomPower++;
		double [][] matrix = new double[polinomPower][polinomPower];
		double [] B = new double[polinomPower];

		for (int i = 0; i < matrix.length; i++) {
			for (int k = 0; k < U.length; k++) {
				pow = 1.0;
				for (int l= 0; l < i; l++) {
					pow *= C[k];
				}
				B[i] += pow * U[k];
			}

			for (int j = 0; j < matrix.length; j++) {
				for (int k = 0; k < U.length; k++) {
					pow = 1.0;
					for (int l = 0; l < i + j ; l++) {
						pow *= C[k];
					}
					matrix[i][j] += pow;
				}
			}
		}
		double[] arr = new double[polinomPower];
		lab3.MAIN.holes(matrix, B, arr);
		return arr;
	}

	public static void returnUnknown (double[] arr) {
		arr[1] = 1/arr[1];
		arr[0] = Math.exp(arr[0]);
	}




}
