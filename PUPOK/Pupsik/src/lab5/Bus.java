package lab5;

public class Bus implements Driveble {


    @java.lang.Override
    public void drive() {
        System.out.println("їду на бусі");
    }

    @java.lang.Override
    public void sitZaRul() {
        System.out.println("сів за руль буса");
    }
}
