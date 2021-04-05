package sem1.lab3;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Setter
@NoArgsConstructor
public class Matrix {
    public double[][] matrix;

    public Matrix (String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            StringBuilder sb = new StringBuilder();
            while (reader.ready()) {
                sb.append(reader.readLine()).append(" ");
            }
            String[] data = sb.toString().split(" ");
            int count = 0;
            this.matrix = new double[(int) Math.sqrt(data.length)][(int) Math.sqrt(data.length)];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    this.matrix[i][j] = Double.parseDouble(data[count]);
                    count++;
                }
            }
        }
        catch (IOException ignored) {
            System.out.println("Something wrong with file or matrix");
        }
    }

    public Matrix (double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    public Matrix (Matrix matrix) {
        this.matrix = new double[matrix.matrix.length][matrix.matrix.length];
        for (int i = 0; i < matrix.matrix.length; i++) {
            System.arraycopy(matrix.matrix[i], 0, this.matrix[i], 0, matrix.matrix.length);
        }
    }

    public Matrix(int i) {
        this.matrix = new double[i][i];
    }

    public static void copyMatrix (Matrix A, Matrix B) {

        if (A.matrix.length != B.matrix.length){
            System.out.println("Error");
        }
        else {
            for (int i = 0; i < A.matrix.length; i++) {
                System.arraycopy(B.matrix[i], 0, A.matrix[i], 0, A.matrix.length);
            }
        }
    }
}
