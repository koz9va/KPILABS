package lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

            double step = (arrayOfUds[0] + arrayOfUds[1]) / 2;

            ArrayList<ArrayList<Double>> finalArray = new ArrayList<>();

            for (double[] array :
                    arrayOfCurrent) {
                ArrayList<Double> tmpArr = inter(array, step, array[0], arrayOfUds);
                finalArray.add(tmpArr);
                System.out.println(tmpArr.toString());
            }

            for (int i = 0; i < finalArray.get(i).size(); i++) {
                ArrayList<Double> tmpArr = null;
                for (ArrayList<Double> doubles : finalArray) {
                    tmpArr = new ArrayList<>(lab1.Main.UgsLengthOfArray);
                    tmpArr.add(doubles.get(i));
                }
                System.out.print(inter(tmpArr, tmpArr.get(0), arrayOfUgs) + " ");
            }



        }
        catch (IOException ignored) {
            System.out.println("E0");
        }

    }

    private static ArrayList<Double> inter(double[] DefaultArray, double step, double FirstElement, double[] arrayOfUds){
        ArrayList<Double> arrayList = new ArrayList<>();

        for(int k = 0; k < 13; ++k){// поменять 13

            double sum = 0;
            for (int i = 0; i < DefaultArray.length; i++) {
                double product = DefaultArray[i];
                for (int j = 0; j < DefaultArray.length; j++) {
                    if (i != j) {
                        product *= (FirstElement - arrayOfUds[j]) / (arrayOfUds[i] - arrayOfUds[j]);
                    }
                }
                sum += product;
            }
            arrayList.add(sum);
            FirstElement += step;
        }
        return arrayList;
    }

    private static Double inter (ArrayList<Double> VerticalLine, double FirstElement, double[] arrayOfUgs) {
        double sum = 0;

        for (int i = 0; i < arrayOfUgs.length; i++) {
            double product = VerticalLine.get(i);

            for (int j = 0; j < arrayOfUgs.length; j++) {
                if (i != j) {
                     product = (FirstElement - arrayOfUgs[j]) / (arrayOfUgs[i] - arrayOfUgs[j]);
                }
            }
            sum += product;
        }
        return sum;
    }
}
