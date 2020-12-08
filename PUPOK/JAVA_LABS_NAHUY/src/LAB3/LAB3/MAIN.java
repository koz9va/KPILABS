package LAB3;



import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MAIN {

    public static void LU (double[][] A, double [] B, double[] x){
        Arrays.fill(x,0);

        for(int j = 0; j < A.length; j++){

            for (int i = j; i < A.length; i++) {

                for (int k = 0; k <= j - 1; k++) {

                    A[i][j] -= A[i][k] * A[k][j];

                }
            }
            for (int i = j + 1; i < A.length; i++) {

                for (int k = 0; k <= j - 1; k++) {

                    A[j][i] -= A[j][k] * A[k][i];


                }

                A[j][i] /= A[j][j];

            }

        }
        for (int i = 0; i < A.length; i++) {
            x[i] = B[i];
            for (int j = 0; j <= i - 1; j++) {
                x[i] -= A[i][j] * x[j];
            }
            x[i] /= A[i][i];
        }

        for (int i = A.length - 1; i >= 0; i--) {
            for (int j = i + 1; j < A.length; j++) {
                x[i] -= A[i][j] * x[j];
            }
        }



    }

    public static void QR(double [][] A, double[] B, double[] x) {
        Arrays.fill(x, 0);

        for (int j = 0; j < A.length - 1; j++) {
            double a = 0;
            for (int i = j; i < A.length; i++) {
                a += A[i][j] * A[i][j];
            }
            if (A[j][j] < 0)
                a = Math.sqrt(a);
            else
                a = -Math.sqrt(a);
            double k = 1.0 / (a * a - a * A[j][j]);
            A[j][j] -= a;
            double t;
            for (int i = j + 1; i < A.length; i++) {
                t = 0;
                for (int l = j; l < A.length; l++) {
                    t += A[l][j] * A[l][i];
                }
                for (int l = j; l < A.length; l++) {
                    A[l][i] -= k * A[l][j] * t;
                }
            }
            t = 0;
            for (int i = j; i <A.length; i++) {
                t += A[i][j] * B[i];
            }
            for (int i = j; i < A.length; i++) {
                B[i] -= k * A[i][j] * t;
            }
            A[j][j] = a;
        }

        for (int i = A.length - 1; i >= 0; i--) {
            x[i] = B[i];
            for (int j = i + 1; j < A.length; j++) {
                x[i] -= A[i][j] * x[j];
            }
            x[i] /= A[i][i];
        }



    }

    public static void holes (double[][] A, double [] B, double[] x) {
        Arrays.fill(x,0);

        for (int j = 0; j < A.length; j++) {

            for (int k = 0; k < j; k++) {
                A[j][j] -= A[j][k] * A[j][k] * A[k][k];
            }

            for (int i = j + 1; i < A.length; i++) {

                for (int k = 0; k < j; k++) {
                    A[i][j] -= A[i][k] * A[k][k] * A[j][k];
                }

                A[i][j] /= A[j][j];
            }
        }

        for (int i = 0; i < A.length; i++) {

            x[i] = B[i];

            for (int j = 0; j < i; j++) {
                x[i] -= A[i][j] * x[j];
            }
        }

        for (int i = 0; i < A.length; i++) {
            x[i] = x[i] / A[i][i];
        }

        for (int i = A.length - 1; i >= 0; i--) {

            for (int j = i + 1; j < A.length; j++) {
                x[i] -= A[j][i] * x[j];
            }
        }
    }

    public static double CalcMiss(double [][] matrix, double [] B, double [] x) {
        double sum = 0;

        double[] tmp = new double[B.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                tmp[i] += matrix[i][j] * x[j];

            }
        }
        for (int i = 0; i < B.length; i++) {
            tmp[i] -= B[i];
            sum += tmp[i] * tmp[i];

        }
        return Math.sqrt(sum);
    }


    public static void main(String[] args) {
        double[][] matrix = {
                {16, -8, -2},
                {-8, 19, -5},
                {-2, -5, 7}
        };
        double[][] TmpMat = new double[matrix.length][matrix.length];
        MatrixCopy(matrix,TmpMat);
        double[] B = {-12, 9, 2};
        double[] TmpB = new double [B.length];
        System.arraycopy(B,0,TmpB,0,B.length);
        double[] x = new double[3];


        try(FileWriter writer = new FileWriter("source.txt")){
            writer.write("incoming system:\n");
            for (double[] doubles : matrix) {
                for (int j = 0; j < matrix.length; j++) {
                    writer.write(doubles[j] + " ");
                }
                writer.write("\n");
            }
            LU(TmpMat,TmpB,x);
            writer.write("LU\n");
            WriteData(matrix, TmpMat, B, TmpB, x, writer);
            QR(TmpMat,TmpB,x);
            writer.write("QR\n");
            WriteData(matrix, TmpMat, B, TmpB, x, writer);
            holes(TmpMat, TmpB, x);
            writer.write("Holes\n");
            WriteData(matrix,TmpMat,B,TmpB,x,writer);
        }
        catch(IOException ignored){}

    }

    public static void WriteData(double[][] matrix, double[][] tmpMat, double[] b, double[] tmpB, double[] x, FileWriter writer) throws IOException {
        writer.write("--------------------------------\n");
        writer.write("decomposed system:\n");
        for (double[] doubles : tmpMat) {
            for (int j = 0; j < tmpMat.length; j++) {
                writer.write(doubles[j] + " ");
            }
            writer.write("\n");
        }
        writer.write("roots of system:\n");
        for (double v : x) {
            writer.write(v + " ");
        }
        writer.write("\nNorm R: "+CalcMiss(matrix, b,x)+"\n");
        MatrixCopy(matrix,tmpMat);
        System.arraycopy(b,0, tmpB,0, b.length);
    }

    public static void MatrixCopy(double[][] src, double[][] dst){
        if (src.length == dst.length ){

            for (int i = 0; i < src.length; i++) {
                System.arraycopy(src[i],0,dst[i],0,src.length);

            }
        }

    }


}
