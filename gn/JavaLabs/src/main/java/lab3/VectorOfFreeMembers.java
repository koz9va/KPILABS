package lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VectorOfFreeMembers {
    double[] vector;

    public VectorOfFreeMembers (String filename) {
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

    public VectorOfFreeMembers (VectorOfFreeMembers vectorOfFreeMembers) {
        this.vector = new double[vectorOfFreeMembers.vector.length];
        System.arraycopy(vectorOfFreeMembers.vector, 0, this.vector,0, vectorOfFreeMembers.vector.length);
    }

}
