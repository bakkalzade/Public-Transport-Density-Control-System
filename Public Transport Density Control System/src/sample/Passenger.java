package sample;

public class Passenger extends Item{

    private final String name;
    private final String stopIn;
    private final String stopOut;

    public Passenger(String name, String stopIn, String stopOut) {

        super();
        this.name = name;
        this.stopIn = stopIn;
        this.stopOut = stopOut;

    }


    public String getStopIn() {
        return stopIn;
    }

    public String getName() {
        return name;
    }

    public String getStopOut() {
        return stopOut;
    }

}
