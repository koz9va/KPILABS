package test;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        double x = -0.72;
        double x2 = -0.54;

        double a = 2 * x + x2 / 2 + 2 + 4 * Math.pow(x + x2 + 1, 3);
        double b = x2 / 8 + x / 2 + 0.5 + 4 * Math.pow(x + x2 + 1, 3);

        System.out.println(a);
        System.out.println(b);

        System.out.println("------------------------------");

        System.out.print(12 * (x + x2 + 1) * (x + x2 + 1) + 2);
        System.out.print(" ");
        System.out.println(12 * (x + x2 + 1) * (x + x2 + 1) + 0.5);
        System.out.print(12 * (x + x2 + 1) * (x + x2 + 1) + 0.5);
        System.out.print(" ");
        System.out.println(12 * (x + x2 + 1) * (x + x2 + 1) + 1.0/8);
    }

}
