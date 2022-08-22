package sample;

import java.util.LinkedList;
import java.util.Queue;

public class Stop {


    private final String name;
    private final Queue<Passenger> passengersOnStop= new LinkedList<>();

    public Stop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addPassenger(Passenger passenger){
        passengersOnStop.add(passenger);
    }

    public Passenger pollPassenger(){

        return passengersOnStop.poll();
    }
    public int getNumberOfPass(){
        return passengersOnStop.size();
    }
}
