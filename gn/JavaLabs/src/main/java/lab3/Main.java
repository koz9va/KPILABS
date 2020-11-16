package lab3;


import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        double[] x = new double[3];
        Matrix matrix;
        VectorOfFreeMembers vectorOfFreeMembers;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Matrix");
            matrix = new Matrix(getFilenameFromConsole(reader));

            System.out.println("Vector");
            vectorOfFreeMembers = new VectorOfFreeMembers(getFilenameFromConsole(reader));

            System.out.println("Enter file to write Main Matrix");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reader.readLine()))) {
                for (int i = 0; i < matrix.matrix.length; i++) {
                    for (int j = 0; j < matrix.matrix.length; j++) {
                        writer.write(matrix.matrix[i][j] + " ");
                    }
                    writer.write("\n");
                }
            }

            System.out.println("Enter file to write LU fact");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reader.readLine()))) {

                Matrix LUMatrix = new Matrix(matrix);
                VectorOfFreeMembers vec = new VectorOfFreeMembers(vectorOfFreeMembers);

                LU(LUMatrix, vec, x);

                writer.write("LU_fact_Matrix\n");
                for (int i = 0; i < LUMatrix.matrix.length; i++) {
                    for (int j = 0; j < LUMatrix.matrix.length; j++) {
                        writer.write(LUMatrix.matrix[i][j] + " ");
                    }
                    writer.write("\n");
                }
                PrintInfoOfFact(x, matrix, vectorOfFreeMembers, writer);
            }

            System.out.println("Enter file to write QR fact");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reader.readLine()))) {

                Matrix QRMatrix = new Matrix(matrix);
                VectorOfFreeMembers vec = new VectorOfFreeMembers(vectorOfFreeMembers);

                QR(QRMatrix, vec, x);

                writer.write("QR_fact_Matrix\n");
                for (int i = 0; i < QRMatrix.matrix.length; i++) {
                    for (int j = 0; j < QRMatrix.matrix.length; j++) {
                        writer.write(QRMatrix.matrix[i][j] + " ");
                    }
                    writer.write("\n");
                }
                PrintInfoOfFact(x, matrix, vectorOfFreeMembers, writer);
            }

            System.out.println("Enter file to write Holes fact");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reader.readLine()))) {

                Matrix HolesMatrix = new Matrix(matrix);
                VectorOfFreeMembers vec = new VectorOfFreeMembers(vectorOfFreeMembers);

                Hales(HolesMatrix, vec, x);

                writer.write("LU_fact_Matrix\n");
                for (int i = 0; i < HolesMatrix.matrix.length; i++) {
                    for (int j = 0; j < HolesMatrix.matrix.length; j++) {
                        writer.write(HolesMatrix.matrix[i][j] + " ");
                    }
                    writer.write("\n");
                }
                PrintInfoOfFact(x, matrix, vectorOfFreeMembers, writer);
            }
        }
        catch(IOException ignored){
            System.out.println("E1");
        }
    }

    private static void PrintInfoOfFact(double[] x, Matrix matrix, VectorOfFreeMembers vectorOfFreeMembers, BufferedWriter writer) throws IOException {
        writer.write("\nUnknown_Vector\n");
        for (double v : x) {
            writer.write(v + " ");
        }
        writer.write("\nResidual_Vector\n");
        double[] arr = residualVector(new Matrix(matrix), x, new VectorOfFreeMembers(vectorOfFreeMembers));
        writer.write(Arrays.toString(arr) + " ");
        writer.write("\nNormal_Residual_Vector\n");
        writer.write(normVec(arr) + " ");
        writer.write("\n");
    }

    public static void LU (Matrix a, VectorOfFreeMembers b, double[] x) {
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
            x[i] = b.vector[i];
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

    public static void QR (Matrix a, VectorOfFreeMembers b, double[] x) {
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
                t += a.matrix[i][j] * b.vector[i];
            }
            for (int i = j; i < a.matrix.length; i++) {
                b.vector[i] -= k * a.matrix[i][j] * t;
            }
            a.matrix[j][j] = alpha;
        }

        for (int i = a.matrix.length - 1; i >= 0; i--) {
            x[i] = b.vector[i];
            for (int j = i + 1; j < a.matrix.length; j++) {
                x[i] -= a.matrix[i][j] * x[j];
            }
            x[i] /= a.matrix[i][i];
        }
    }

    public static void Hales (Matrix a, VectorOfFreeMembers b, double[] x) {
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

            x[i] = b.vector[i];

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

    public static String getFilenameFromConsole (BufferedReader reader) {
        String filename = null;
        System.out.println("Enter file with data");
        try {
            filename = reader.readLine();
        } catch (IOException ignored) {
            System.out.println("E0");
        }
        return filename;
    }

    public static double[] residualVector(Matrix matrix, double[] arrayOfX, VectorOfFreeMembers arrayOfB) {
        double[] returnedArray = new double[arrayOfX.length];
        for (int i = 0; i < matrix.matrix.length; i++) {

            for (int j = 0; j < matrix.matrix.length; j++) {
                returnedArray[i] += matrix.matrix[i][j] * arrayOfX[j];
            }
        }
        subtractVectors(returnedArray, arrayOfB.vector);
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
