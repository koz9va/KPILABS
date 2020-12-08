package lab5;

public class Car implements Driveble {
    @java.lang.Override
    public void drive() {
        System.out.println("їду на машині");
    }

    @java.lang.Override
    public void sitZaRul() {
        System.out.println("сів за руль машини");
    }
}
