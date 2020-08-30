package com.company;

public class Point implements Comparable<Point>{
    private final double Uzv;
    private final double Usv;
    private double Current;

    public double getUzv() {
        return Uzv;
    }

    public double getUsv() {
        return Usv;
    }

    public double getCurrent() {
        return Current;
    }

    public Point(double uzv, double usv) {
        Uzv = uzv;
        Usv = usv;
    }

    public Point(double uzv, double usv, double current) {
        Uzv = uzv;
        Usv = usv;
        Current = current;
    }

    public void setTRansistorCurrent() {
        this.Current = Transistor.getCurrent(Uzv, Usv);
    }

    @Override
    public String toString() {
        return "point with Ugs " + this.Uzv + " and Uds " + this.Usv + " has " + this.Current + " Current";
    }

    @Override
    public int compareTo(Point o) {
        return Double.compare(o.getUsv(), getUsv());
    }
}
