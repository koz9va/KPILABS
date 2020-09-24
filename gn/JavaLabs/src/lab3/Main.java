package lab3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Matrix matrix = new Matrix(getFilenameFromConsole());

        Matrix matrix1 = new Matrix(matrix);

        double[] b = {0, 20, -12};
        double[] x = new double[3];

        QR(new Matrix(matrix), b, x);

        for (double v : x) {
            System.out.println(v);
        }

//        Matrix matrix2 = new Matrix(1);
//        matrix2.matrix[0][0] = 21;
//        Matrix matrix3 = new Matrix(matrix2);
//
//
//        double[][] mat = matrix.clone();


//        LU(matrix, b, x);
//        QR(matrix, b, x);
//        Hales(matrix, b, x);
//
//        double[] arr = vectorR(matrix1, x, b);
//        double norm = normVec(arr);
    }
    
    public static void LU (Matrix a, double[] b, double[] x) {
        Arrays.fill(x,0);

        for (int j = 0; j < a.matrix.length; j++) {

            for (int i = j; i < a.matrix.length; i++) {

                for (int k = 0; k <= j - 1; k++) {
                    a.matrix[i][j] -= a.matrix[i][k] * a.matrix[k][j];
                }
            }
            //present

            for (int i = j + 1; i < a.matrix.length; i++) {

                for (int k = 0; k <= j - 1; k++) {
                    a.matrix[j][i] -= a.matrix[j][k] * a.matrix[k][i];
                }
                a.matrix[j][i] /= a.matrix[j][j];
            }
        }

        for (int i = 0; i < a.matrix.length; i++) {
            x[i] = b[i];
            for (int j = 0; j <= i - 1; j++) {
                x[i] -= a.matrix[i][j] * x[j];
            }
            x[i] /= a.matrix[i][i];
        }

        for (int i = a.matrix.length - 1; i >= 0; i--) {
            for (int j = i + 1; j < a.matrix.length; j++) {
                x[i] -= a.matrix[i][j] * x[j];
            }
        }
    }

    public static void QR (Matrix a, double[] b, double[] x) {
        Arrays.fill(x,0);

        for (int j = 0; j < a.matrix.length - 1; j++) {
            double alpha = 0;
            for (int i = j; i < a.matrix.length; i++) {
                alpha += a.matrix[i][j] * a.matrix[i][j];
            }
            if (a.matrix[j][j] < 0)
                alpha = Math.sqrt(alpha);
            else
                alpha = -Math.sqrt(alpha);
            double k = 1.0 / (alpha * alpha - alpha * a.matrix[j][j]);
            a.matrix[j][j] -= alpha;
            double t;
            for (int i = j + 1; i < a.matrix.length; i++) {
                t = 0;
                for (int l = j; l < a.matrix.length; l++) {
                    t += a.matrix[l][j] * a.matrix[l][i];
                }
                for (int l = j; l < a.matrix.length; l++) {
                    a.matrix[l][i] -= k * a.matrix[l][j] * t;
                }
            }
            t = 0;
            for (int i = j; i < a.matrix.length; i++) {
                t += a.matrix[i][j] * b[i];
            }
            for (int i = j; i < a.matrix.length; i++) {
                b[i] -= k * a.matrix[i][j] * t;
            }
            a.matrix[j][j] = alpha;
        }

        for (int i = a.matrix.length - 1; i >= 0; i--) {
            x[i] = b[i];
            for (int j = i + 1; j < a.matrix.length; j++) {
                x[i] -= a.matrix[i][j] * x[j];
            }
            x[i] /= a.matrix[i][i];
        }
    }

    public static void Hales (Matrix a, double[] b, double[] x) {
        Arrays.fill(x,0);

        for (int j = 0; j < a.matrix.length; j++) {

            for (int k = 0; k < j; k++) {
                a.matrix[j][j] -= a.matrix[j][k] * a.matrix[j][k] * a.matrix[k][k];
            }

            for (int i = j + 1; i < a.matrix.length; i++) {

                for (int k = 0; k < j; k++) {
                    a.matrix[i][j] -= a.matrix[i][k] * a.matrix[k][k] * a.matrix[j][k];
                }

                a.matrix[i][j] /= a.matrix[j][j];
            }
        }

        for (int i = 0; i < a.matrix.length; i++) {

            x[i] = b[i];

            for (int j = 0; j < i; j++) {
                x[i] -= a.matrix[i][j] * x[j];
            }
        }

        for (int i = 0; i < a.matrix.length; i++) {
            x[i] = x[i] / a.matrix[i][i];
        }

        for (int i = a.matrix.length - 1; i >= 0; i--) {

            for (int j = i + 1; j <a.matrix.length; j++) {
                x[i] -= a.matrix[j][i] * x[j];
            }
        }
    }

    public static String getFilenameFromConsole () {
        String filename = null;
        System.out.println("Enter file with matrix");
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))){
            filename = consoleReader.readLine();
        } catch (IOException ignored) {
            System.out.println("E0");
        }
        return filename;
    }

    public static double[] vectorR (double[][]matrix, double[] arrayOfX, double[] arrayOfB) {
        double[] returnedArray = new double[arrayOfX.length];
        for (int i = 0; i < matrix.length; i++) {

            for (int j = 0; j < matrix.length; j++) {
                returnedArray[i] += matrix[i][j] * arrayOfX[j];
            }
        }
        subtractVectors(returnedArray, arrayOfB);
        return returnedArray;
    }

    public static void subtractVectors (double[] arraySetValue, double[] arrayGetValue) {
        if (arraySetValue.length == arrayGetValue.length) {

            for (int i = 0; i < arraySetValue.length; i++) {
                arraySetValue[i] -= arrayGetValue[i];
            }
        }
        else {
            System.out.println("Vectors lengths are not identical");
        }
    }

    public static double normVec (double[] vector) {
        double sum = 0;

        for (double v : vector) {
            sum += Math.pow(v, 2);
        }
        return Math.sqrt(sum);
    }
}
