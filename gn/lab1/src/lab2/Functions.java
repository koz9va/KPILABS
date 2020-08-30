package lab2;

import com.company.Point;

import java.util.ArrayList;
import java.util.Comparator;

public class Functions {

    public static ArrayList<Point> getArrayOfPoints (ArrayList<Point> points){
        ArrayList<Point> soulution = new ArrayList<>();
        ArrayList<Point> pointarr1 = new ArrayList<>();
        ArrayList<Point> pointarr2 = new ArrayList<>();
        ArrayList<Point> pointarr3 = new ArrayList<>();
//        ArrayList<Double> doubles = new ArrayList<>();
//        try(BufferedReader reader = new BufferedReader(new FileReader("1.txt"))) {
//            while (reader.ready()) {
//                doubles.add(Double.parseDouble(reader.readLine()));
//            }
//        }
//        catch (IOException ignored){}
        for (int i = 0; i < 7 ; i++) {
            pointarr1.add(points.get(i));
            pointarr2.add(points.get(i + 7));
            pointarr3.add(points.get(i + 14));
        }
        OneInter(pointarr1);
        OneInter(pointarr2);
        OneInter(pointarr3);
        Comparator<Point> comparator = Point::compareTo;
        pointarr1.sort(comparator.reversed());
        pointarr2.sort(comparator.reversed());
        pointarr3.sort(comparator.reversed());
        soulution.addAll(pointarr1);

        soulution.addAll(pointarr2);
        soulution.addAll(pointarr3);

        return soulution;
    }

//    public static Point getPointOnNewLine(Double first, Double second, Double last, ArrayList<Double> doubles, Double Ugsx) {
//
//    }

    public static void OneInter(ArrayList<Point> points) {
        ArrayList<Point> new_points = new ArrayList<>(points);
        for (int i = 1; i < new_points.size(); i++) {
            double Udsx = (points.get(i).getUsv() + points.get(i - 1).getUsv()) / 2;
            double Ugsx = points.get(i).getUzv();
            points.add(new Point(Ugsx, Udsx, Lagrange(Udsx, new_points)));
        }
    }

    public static double Lagrange(double Udsx, ArrayList<Point> points) {
        double sum = 0;
        for (int i = 1; i < points.size(); i++) {
            double p = points.get(i).getCurrent();
            for (int j = 1; j < points.size(); j++) {
                if (i != j){
                    p *= (Udsx - points.get(j).getUsv()) / (points.get(i).getUsv() - points.get(j).getUsv());
                }
            }
            sum += p;
        }
        return sum;
    }
}
