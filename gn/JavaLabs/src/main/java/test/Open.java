package test;


public class Open {
    private int age;
    private String name;
    private String Surname;
    private TestCar car;


    public TestCar getCar() {
        return car;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return Surname;
    }

    public Open(int age, String name, String surname) {
        this.age = age;
        this.name = name;
        this.Surname = surname;

        this.car = new TestCar(1, 2, "qwe");
    }

    public Open() {}
}
