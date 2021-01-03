package Exam;

import java.util.Arrays;

public class Fact {

    public static void main(String[] args) {
        double[][] mat = {
            {12.0, -6.0, -4.0},
            {-6.0, 18.0, -3.0},
            {-4.0, -3.0, 7.0}
        };
        double[] b = {-16.0, 18.0, 18.0};

        Holes(mat, b);
    }

    public static void LU(double[][] mat, double[] b) {
        System.out.println("LU");
        for (int i = 0;i < mat.length;++i) {
            System.out.println(Arrays.toString(mat[i]) + " * x" + i + " = " + b[i]);
        }

        System.out.println("j = 1");
        double L12 = mat[0][1] / mat[0][0];
        double L13 = mat[0][2] / mat[0][0];
        System.out.format("L12 = %f/%f = %f \n", mat[0][1], mat[0][0], L12);
        System.out.format("L13 = %f/%f = %f\n", mat[0][2], mat[0][0], L13);
        System.out.println();
        mat[0][1] = L12;
        mat[0][2] = L13;
        printMat(mat);

        System.out.println();
        System.out.println("j = 2");
        double L22 = mat[1][1] - (mat[1][0] * mat[0][1]);
        System.out.format("L22 = %f - (%f * %f) = %f\n", mat[1][1], mat[1][0], mat[0][1], L22);
        double L32 = mat[2][1] - (mat[0][1] * mat[2][0]);
        System.out.format("L32 = %f - (%f * %f) = %f\n", mat[2][1], mat[0][1], mat[2][0], L32);
        double L23 = (mat[2][1] - (mat[1][0] * mat[0][2]))/(L22);
        System.out.format("L23 = (%f - (%f * %f))/(%f) = %f\n", mat[2][1], mat[1][0], mat[0][2], L22, L23);
        mat[1][1] = L22;
        mat[2][1] = L32;
        mat[1][2] = L23;
        printMat(mat);

        System.out.println();
        System.out.println("j = 3");
        double L33 = mat[2][2] - mat[2][0] * mat[0][2] - L32 * L23;
        System.out.format("L33 = %f - (%f * %f) - (%f * %f) = %f\n", mat[2][2], mat[2][0], mat[0][2], L32, L23, L33);
        mat[2][2] = L33;
        printMat(mat);

        double Y1 = b[0] / mat[0][0];
        System.out.format("Y1 = %f / %f = %f\n", b[0], mat[0][0], Y1);
        double Y2 = (b[1] - mat[1][0] * Y1)/(mat[1][1]);
        System.out.format("Y2 = (%f - (%f * %f))/(%f) = %f\n", b[1], mat[1][0], Y1, mat[1][1], Y2);
        double Y3 = (b[2] - (mat[2][0] * Y1) - (mat[2][1] * Y2))/(mat[2][2]);
        System.out.format("Y3 = (%f - (%f * %f) - (%f * %f))/(%f) = %f\n", b[2], mat[2][0], Y1, mat[2][1], Y2, mat[2][2], Y3);

        System.out.format("x3 = y3 = %f\n", Y3);
        double x2 = Y2 - (mat[1][2] * Y3);
        System.out.format("x2 = %f - (%f * %f) = %f\n",Y2, mat[1][2], Y3, x2);
        double x1 = Y1 - (mat[0][1] * x2 + mat[0][2] * Y3);
        System.out.format("x1 = %f - ((%f * %f) + (%f * %f)) = %f\n", Y1, mat[0][1], x2, mat[0][2], Y3, x1);
    }

    public static void QR(double[][] mat, double[] b) {
        System.out.println("QR");
        for (int i = 0;i < mat.length;++i) {
            System.out.println(Arrays.toString(mat[i]) + " * x" + i + " = " + b[i]);
        }
        System.out.println("j = 1");
        double alphaPowered = mat[0][0] * mat[0][0] + mat[0][1] * mat[0][1] + mat[0][2] * mat[0][2];
        System.out.format("alpha^2 = %f^2 + %f^2 + %f^2 = %f\n", mat[0][0], mat[0][1], mat[0][2], alphaPowered);

        double alpha;
        if (mat[0][0] < 0)
            alpha = Math.sqrt(alphaPowered);
        else
            alpha = - Math.sqrt(alphaPowered);
        System.out.format("alpha = sqrt(%f) = %f\n", alphaPowered, alpha);
        double k = 1.0 / (alphaPowered - alpha * mat[0][0]);
        System.out.format("k = 1.0 / (%f - %f * %f) = %f\n", alphaPowered, alpha, mat[0][0], k);
        double[] S1T = {mat[0][0], mat[0][1], mat[0][2]};
        S1T[0] -= alpha;
        System.out.println("S1T = " + Arrays.toString(S1T));
        System.out.println("S1TA = " + Arrays.toString(S1T) + " * ");
        System.out.println("{");
        for (double[] doubles : mat) {
            System.out.println(Arrays.toString(doubles));
        }
        System.out.println("}");
        double[] S1TA = VecOnMat(S1T, mat);
        System.out.println(" = " + Arrays.toString(S1TA));

        System.out.println("A1 = {");
        printMat(mat);
        System.out.println("} - " + k + " * " + Arrays.toString(S1TA) + " * (Tранспоноване) " + Arrays.toString(S1T) + " = ");

        double[][] A1 = VecAndVecToMat(S1TA, S1T);
        NumberOnMat(A1, k);
        printMat(mat);
        System.out.println(" - ");
        printMat(A1);
        System.out.println(" = ");
        MatMinusMat(mat, A1);
        printMat(mat);
        double S1TB = VecAndVecToNumb(S1T, b);
        System.out.println("S1TB = " + Arrays.toString(S1T) + " * (Trans) " + Arrays.toString(b) + " = " + S1TB);

        double tmp = k * S1TB;

        System.out.println("B1 = (Trans)" + Arrays.toString(b) + " - (Trans)" + Arrays.toString(S1T) +
                " * " + k + " * " + S1TB + " = ");
        VecOnNumb(S1T, tmp);
        System.out.println("(Trans)" + Arrays.toString(b) + " - (Trans)" + Arrays.toString(S1T));
        VecMinusVec(b, S1T);
        System.out.println(" = (Trans)" + Arrays.toString(b));

        System.out.println("\nj = 2");

        double alphaPowered1 = mat[1][1] * mat[1][1] + mat[2][1] * mat[2][1];
        System.out.format("alpha^2 = %f^2 + %f^2 + %f^2 = %f\n", mat[0][0], mat[0][1], mat[0][2], alphaPowered1);

        double alpha1;
        if (mat[1][1] < 0)
            alpha1 = Math.sqrt(alphaPowered1);
        else
            alpha1 = - Math.sqrt(alphaPowered1);
        System.out.format("alpha = sqrt(%f) = %f\n", alphaPowered1, alpha1);
        double k1 = 1.0 / (alphaPowered1 - alpha1 * mat[1][1]);
        System.out.format("k = 1.0 / (%f - %f * %f) = %f\n", alphaPowered1, alpha1, mat[0][0], k1);
        double[] S1T1 = {mat[1][0], mat[1][1], mat[2][1]};
        S1T1[1] -= alpha1;
        System.out.println("S2T = " + Arrays.toString(S1T1));
        System.out.println("S2TA = " + Arrays.toString(S1T1) + " * ");
        System.out.println("{");
        for (double[] doubles : mat) {
            System.out.println(Arrays.toString(doubles));
        }
        System.out.println("}");
        double[] S1TA1 = VecOnMat(S1T1, mat);
        System.out.println(" = " + Arrays.toString(S1TA1));

        System.out.println("A2 = {");
        printMat(mat);
        System.out.println("} - " + k1 + " * " + Arrays.toString(S1TA1) + " * (Tранспоноване) " + Arrays.toString(S1T1) + " = ");

        double[][] A2 = VecAndVecToMat(S1TA1, S1T1);
        NumberOnMat(A2, k1);
        printMat(mat);
        System.out.println(" - ");
        printMat(A2);
        System.out.println(" = ");
        MatMinusMat(mat, A2);
        printMat(mat);
        double S1TB1 = VecAndVecToNumb(S1T1, b);
        System.out.println("S2TB = " + Arrays.toString(S1T1) + " * (Trans) " + Arrays.toString(b) + " = " + S1TB1);

        double tmp1 = k1 * S1TB1;

        System.out.println("\nB2 = (Trans)" + Arrays.toString(b) + " - (Trans)" + Arrays.toString(S1T1) +
                " * " + k1 + " * " + S1TB1 + " = ");
        VecOnNumb(S1T1, tmp1);
        System.out.println("(Trans)" + Arrays.toString(b) + " - (Trans)" + Arrays.toString(S1T1));
        VecMinusVec(b, S1T1);
        System.out.println(" = (Trans)" + Arrays.toString(b));

        System.out.println("{");
        for (double[] doubles : mat) {
            System.out.println(Arrays.toString(doubles));
        }
        System.out.println("} * " + "(Trans)[x1, x2, x3] = (Trans)" + Arrays.toString(b));
        double x3 = b[2]/mat[2][2];
        double x2 = (b[1] - (mat[1][2] * x3))/(mat[1][1]);
        double x1 = (b[0] - (mat[0][1] * x2) - (mat[0][2] * x3))/(mat[0][0]);
        System.out.format("x3 = %f\n", x3);
        System.out.format("x2 = %f\n", x2);
        System.out.format("x1 = %f\n", x1);
    }

    public static void Holes(double[][] mat, double[] b){
        System.out.println("Holes");
        for (int i = 0;i < mat.length;++i) {
            System.out.println(Arrays.toString(mat[i]) + " * x" + i + " = " + b[i]);
        }
        double L11 = Math.sqrt(mat[0][0]);
        double L21 = mat[1][0] / L11;
        double L31 = mat[2][0] / L11;
        double L22 = Math.sqrt(mat[1][1] - L21 * L21);
        double L32 = (mat[1][2] - L31 * L21)/(L22);
        double L33 = Math.sqrt(mat[2][2] - L31 * L31 - L32 * L32);

        System.out.println("A = L*LT -> писать акуратно як множення двох матриць");
        System.out.println("[" + L11 + " " + 0 + " " + 0 + "]  [" + L11 + " " + L21 + " " + L31 + "]");
        System.out.println("[" + L21 + " " + L22 + " " + 0 + "] * [" + 0 + " " + L22 + " " + L32 + "]");
        System.out.println("[" + L31 + " " + L32 + " " + L33 + "]  [" + 0 + " " + 0 + " " + L33 + "]");

        System.out.println("Ly = B -> писать акуратно як матричне рівняння");
        System.out.println("[" + L11 + " " + 0 + " " + 0 + "] y1 " + b[0]);
        System.out.println("[" + L21 + " " + L22 + " " + 0 + "] * y2 = " + b[1]);
        System.out.println("[" + L31 + " " + L32 + " " + L33 + "] y3 " + b[2]);

        double y1 = b[0] / L11;
        double y2 = (b[1] - (L21 * y1))/(L22);
        double y3 = (b[2] - (L31 * y1) - (L32 * y2))/(L33);
        System.out.println("y1 = " + y1);
        System.out.println("y2 = " + y2);
        System.out.println("y3 = " + y3);

        System.out.println("LT * x = y -> матричне рівняння");
        System.out.println("[" + L11 + " " + L21 + " " + L31 + "] x1 " + y1);
        System.out.println("[" + 0 + " " + L22 + " " + L32 + "] * x2 = " + y2);
        System.out.println("[" + 0 + " " + 0 + " " + L33 + "] x3 " + y3);

        double x3 = y3/L33;
        double x2 = (y2 - (L32 * x3))/(L22);
        double x1 = (y1 - (L21 * x2) - (L31 * x3))/(L11);
        System.out.format("x3 = %f\n", x3);
        System.out.format("x2 = %f\n", x2);
        System.out.format("x1 = %f\n", x1);
    }

    public static void printMat(double[][] mat) {
        for (double[] doubles : mat) {
            System.out.println(Arrays.toString(doubles));
        }
    }

    public static double[] VecOnMat(double[] ST, double[][] mat) {
        double tmp;
        double[] arr = new double[ST.length];
        for (int i = 0; i < mat.length; i++) {
            tmp = 0.0;
            for (int j = 0; j < mat.length; j++) {
                tmp += ST[j] * mat[j][i];
            }
            arr[i] = tmp;
        }
        return arr;
    }

    public static double[][] VecAndVecToMat(double[] a, double[] b) {
        double[][] mat = new double[a.length][a.length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                mat[i][j] = a[j] * b[i];
            }
        }
        return mat;
    }

    public static void NumberOnMat (double[][] mat, double numb) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                mat[i][j] *= numb;
            }
        }
    }

    public static void MatMinusMat(double[][] A, double[][] B) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                A[i][j] -= B[i][j];
            }
        }
    }

    public static double VecAndVecToNumb(double[] A, double[] B) {
        double tmp = 0.0;
        for (int i = 0; i < A.length; i++) {
            tmp += A[i] * B[i];
        }
        return tmp;
    }

    public static void VecOnNumb(double[] A, double numb) {
        for (int i = 0; i < A.length; i++) {
            A[i] *= numb;
        }
    }

    public static void VecMinusVec(double[] A, double[] B) {
        for (int i = 0; i < A.length; i++) {
            A[i] -= B[i];
        }
    }
}
