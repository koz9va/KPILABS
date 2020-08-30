package lab2;

import com.company.Point;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Point> points = new ArrayList<>();
        ArrayList<Point> soulution;
        try (BufferedReader reader = new BufferedReader(new FileReader("3.txt"))) {
            while(reader.ready()) {
                String data = reader.readLine();
                String[] ArrayOfWords = data.split(" ");
                if (ArrayOfWords.length > 3) {
                    points.add(
                            new Point(
                                    Double.parseDouble(ArrayOfWords[3]),
                                    Double.parseDouble(ArrayOfWords[6]),
                                    Double.parseDouble(ArrayOfWords[8])
                            )
                    );
                }
            }
            soulution = Functions.getArrayOfPoints(points);
            try(BufferedReader console_reader = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Enter file to write");
                String filename = console_reader.readLine();

                try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
                    for (Point point : soulution) {
                        writer.write(point.toString() + "\n");
                    }
                    writer.flush();
                }
            }
        }
        catch (IOException ignored) {
            System.out.println("Not existed file");
        }
    }
}
