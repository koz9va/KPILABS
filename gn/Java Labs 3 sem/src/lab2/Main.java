package lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        double[] arrayOfUds = new double[lab1.Main.UdsLengthOfArray];
        double[] arrayOfUgs = new double[lab1.Main.UgsLengthOfArray];
        double[][] arrayOfCurrent = new double[lab1.Main.UgsLengthOfArray][lab1.Main.UdsLengthOfArray];
        try {

            try (BufferedReader fileReader = new BufferedReader(new FileReader("Uds.txt"))) {
                for (int i = 0; fileReader.ready() ; i++) {
                    arrayOfUds[i] = Double.parseDouble(fileReader.readLine());
                }
            }

            try (BufferedReader fileReader = new BufferedReader(new FileReader("Ugs.txt"))) {
                for (int i = 0; fileReader.ready() ; i++) {
                    arrayOfUgs[i] = Double.parseDouble(fileReader.readLine());
                }
            }

            try (BufferedReader fileReader = new BufferedReader(new FileReader("Current.txt"))) {
                while(fileReader.ready()){//пока есть строки
                    for (int i = 0; i < lab1.Main.UgsLengthOfArray; i++) {//проходим по строкам
                        String[] data = fileReader.readLine().split(" ");//читаем строку в массив через пробел
                        for (int j = 0; j < lab1.Main.UdsLengthOfArray; j++) {//проходим по столбцам
                            arrayOfCurrent[i][j] = Double.parseDouble(data[j]);//парсим елемент массива строк в елемент массива дабл
                        }
                    }
                }
            }



        }
        catch (IOException ignored) {
            System.out.println("E0");
        }

    }

    private

}
