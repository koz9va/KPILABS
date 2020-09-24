package lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public Matrix (Matrix matrix) {
        this.matrix = new double[matrix.matrix.length][matrix.matrix.length];
        for (int i = 0; i < matrix.matrix.length; i++) {
            System.arraycopy(matrix.matrix[i], 0, this.matrix[i], 0, matrix.matrix.length);
        }
    }
}
