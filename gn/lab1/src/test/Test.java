package test;

public class Test {
    public String someString;
    public Test(String someString){
        System.out.println("constructor");
    }

    {
        System.out.println("Non static block");
    }

    static {
        System.out.println("Static block");
    }
}
