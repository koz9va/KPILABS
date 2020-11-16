package test;

public class TestCar {
    private int year;
    private int weight;
    private String name;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestCar(int year, int weight, String name) {
        this.year = year;
        this.weight = weight;
        this.name = name;
    }
}
