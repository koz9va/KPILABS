package lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

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

            double[][] solution = inter(arrayOfCurrent, arrayOfUds);

            for (double[] i:
            solution){
                System.out.println(Arrays.toString(i));
            }

        }
        catch (IOException ignored) {
            System.out.println("E0");
        }

    }

    private static double[][] inter(double[][] matrix, double[] arrayOfXiXj){
        double[] arrayOfX = new double[arrayOfXiXj.length - 1];
        for (int i = 0; i < arrayOfXiXj.length - 1; i++) {
            arrayOfX[i] = (arrayOfXiXj[i] + arrayOfXiXj[i + 1]) / 2;
        }

        //int HorizontalLen = arrayOfX.length + arrayOfXiXj.length;
        double[][] response = new double[lab1.Main.UgsLengthOfArray][arrayOfX.length];

        for (int i = 0; i < response.length; i++) {
            for (int j = 0; j < arrayOfX.length; j++) {
                double sum = 0;

                for (int k = 1; k < arrayOfXiXj.length; k++) {
                    double product = 1.0;

                    for (int l = 1; l < arrayOfXiXj.length; l++) {
                        if (l != k){
                            product *= (arrayOfX[j] - arrayOfXiXj[l]) / (arrayOfXiXj[k] - arrayOfXiXj[l]);
                        }
                    }
                    product *= matrix[i][k];
                    sum += product;
                }

                response[i][j] = sum;
            }
        }
        return response;
    }

}



















