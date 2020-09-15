package lab2;

        import java.io.*;

public class Main {
    public static void main(String[] args) {
        double[] arrayOfUds = new double[lab1.Main.UdsLengthOfArray];
        double[] arrayOfUgs = new double[lab1.Main.UgsLengthOfArray];
        double[][] arrayOfCurrent = new double[lab1.Main.UgsLengthOfArray][lab1.Main.UdsLengthOfArray];
        try (BufferedReader console_reader = new BufferedReader(new InputStreamReader(System.in))) {

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

            double stepUds = (arrayOfUds[0] + arrayOfUds[1]) / 2;

            double stepUgs = (arrayOfUgs[0] + arrayOfUgs[1]) / 2;

            System.out.println("Enter File to write");
            String filename = console_reader.readLine();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (double arrayOfUg : arrayOfUgs) {
                    double value = 0;
                    for (int i = 0; i < lab1.Main.UdsLengthOfArray * 2 - 1; i++) {
                        writer.write(inter(arrayOfUgs, arrayOfUds, arrayOfCurrent, arrayOfUg, value) + " ");
                        value += stepUds;
                    }
                    writer.write("\n");
                }


                double Uds = 0;
                for (int i = 0; i < lab1.Main.UdsLengthOfArray * 2 - 1; i++) {
                    writer.write(inter(arrayOfUgs, arrayOfUds, arrayOfCurrent, stepUgs, Uds) + " ");
                    Uds += stepUds;
                }
            }

        }
        catch (IOException ignored) {
            System.out.println("E0");
        }
    }

    private static double inter(double[] arrayOfUgs, double[] arrayOfUds, double[][] matrix, double ugsX, double udsX) {

        double sum, product, f = 0;

        for (int k = 0; k < arrayOfUgs.length; k++) {

            sum = 0;

            for (int i = 0; i < arrayOfUds.length; i++) {

                product = matrix[k][i];

                for (int j = 0; j < arrayOfUds.length; j++) {

                    if (j != i) {
                        product *= (udsX - arrayOfUds[j]) / (arrayOfUds[i] - arrayOfUds[j]);
                    }
                }

                sum += product;
            }


            for (int i = 0; i < arrayOfUgs.length; i++)

                if (i != k)

                    sum *= (ugsX - arrayOfUgs[i]) / (arrayOfUgs[k] - arrayOfUgs[i]);

            f += sum;
        }
        return f;
    }
}
