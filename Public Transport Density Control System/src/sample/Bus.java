package sample;


import java.util.ArrayList;

public class Bus extends Item{


    private final ArrayList<Passenger> passengersOnBus = new ArrayList<>();
    private String currentStop;
    private final int id;

    public Bus(String currentStop) {

        this();   //constructor chain
        this.currentStop = currentStop;

    }

    public Bus(){
        this.id = Counter.Count.getTotalBusCount();
    }

    public String getCurrentStop() {
        return currentStop;
    }

    public int getId() {
        return id;
    }

    public static ArrayList<Bus> moveFurther(ArrayList<Bus> allBusses) {

        ArrayList<Bus> toRemove = new ArrayList<>();
        for (Bus bus: allBusses){

            int size = Constants.busStopNames.length;

            for (int i = 0; i < Constants.busStopNames.length; i++) {

                String possibleStop = Constants.busStopNames[i];

                if (bus.currentStop.equals(possibleStop)) {//find the stop the bus on

                    if (i == size - 1) {//if the stop is last stop remove the bus

                        //System.out.println("sa");
                        //System.out.println(allBusses.size());

                        toRemove.add(bus);
                        //Manager.removeBus(this);

                    } else {

                        bus.currentStop = Constants.busStopNames[i + 1]; //change the currentStop with the next stop

                        break;

                    }
                }
            }
        }
        for (Bus bus: toRemove){
            allBusses.remove(bus);
        }
        return allBusses;


    }

    public void passengerIsIn(Passenger passenger) {


        passengersOnBus.add(passenger);

    }

    public void passengerIsOut(Passenger passenger){

        passengersOnBus.remove(passenger);


    }

    public ArrayList<Passenger> getPassengersOnBus() {
        return passengersOnBus;
    }
}
