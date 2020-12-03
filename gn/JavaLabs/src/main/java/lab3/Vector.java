package lab3;

import lombok.AllArgsConstructor;
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
    double[] vector;

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

    public Vector(Vector vector) {
        this.vector = new double[vector.vector.length];
        System.arraycopy(vector.vector, 0, this.vector,0, vector.vector.length);
    }

}