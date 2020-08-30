package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        try(BufferedReader console_reader = new BufferedReader(new InputStreamReader(System.in))) {//поток для чтения строк из консоли, через try потому что они сами закроются

            System.out.println("Enter first file to write");
            String UzvVoltaic_file_write = console_reader.readLine();//имя файла в котором будут значения Uzv(VOLTAIC)

            System.out.println("Enter second file to write");
            String Usv_file_write = console_reader.readLine();//имя файла в котором будут значения Usv

            System.out.println("Enter fourth file to write");
            String Current_file_write = console_reader.readLine();//имя файла в котором будут значения тока


            Transistor transistor = new Transistor(0.4e-3, 2e-6, 4, 5, 7, 0, 15);//создаем обьект транзистора для инициализации полей

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(UzvVoltaic_file_write))) {//поток для записи в первый файл
                for (int i = 0; i < transistor.getVoltaic().length; i++) {
                    writer.write(transistor.getVoltaic(i) + "\n");
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Usv_file_write))){//поток для записи в третий файл
                for (int i = 0; i < transistor.getUsv().length; i++) {
                    writer.write(transistor.getUsv(i) + "\n");
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Current_file_write))) {//поток для записи в четвертый файл
                for (int i = 0; i < transistor.getVoltaic().length; i++) {
                    for (int j = 0; j < transistor.getUsv().length; j++) {
                        Point point = new Point(transistor.getVoltaic(i), transistor.getUsv(j));
                        point.setTRansistorCurrent();
                        writer.write(point.toString() + "\n");
                        /*
                         * пишем в файл обьект
                         * новая точка которая принимает 2 значения напряжения и сама себе считает силу тока
                         * переводим обьект в строку для нужного нам вывода который прописале в классе
                         * добавляем символ переноса строки
                         */
                    }
                    writer.write("Current Wave Exit----------------------------------------------\n");
                }
            }
        }
        catch (IOException e) {
            System.out.println("E0");//если что-то с файлами не так выводим ошибку Е0
        }
    }
}
