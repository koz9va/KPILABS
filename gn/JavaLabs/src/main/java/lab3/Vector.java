package lab3;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Setter
@Getter
@NoArgsConstructor
public class Vector {
    public void setVector(int i, double j) {
        this.vector[i] = j;
    }

    public double[] vector;

    public Vector(int capacity) {
        this.vector = new double[capacity];
    }

    public Vector(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String[] data = reader.readLine().split(" ");
            this.vector = new double[data.length];
            for (int i = 0; i < vector.length; i++) {
                this.vector[i] = Double.parseDouble(data[i]);
            }
        }
        catch (IOException ignored) {
            System.out.println("Something wrong with file");
        }
    }

    public Vector (double[] vec) {
        System.arraycopy(vec, 0, this.vector, 0, vec.length);
    }

    public Vector(Vector vector) {
        this.vector = new double[vector.vector.length];
        System.arraycopy(vector.vector, 0, this.vector,0, vector.vector.length);
    }

}
