package sample;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class Controller {

    private static final ArrayList<Passenger> allPassengers =new ArrayList<>();



    private static ArrayList<Bus> allBusses = new ArrayList<>();
    private static final Map<String,Stop> allStops= new HashMap<String, Stop>();
    private boolean busCap= false;
    private boolean pasSize= false;

    @FXML
    private Button buttonpass;

    @FXML
    private  Label sgtpass;

    @FXML
    private  Label fikirpass;

    @FXML
    private  Label bogazpass;

    @FXML
    private  Label mecidpass;

    @FXML
    private  Label zincirpass;

    @FXML
    private  Label busshift;

    @FXML
    private  Label currentsituation;

    @FXML
    private  Label lengthofsystem;

    @FXML
    private Button button;

    @FXML
    private TextField text;

    @FXML
    private Label identificationlabel;

    @FXML
    private Button buttoninput;

    @FXML
    private Label passlabel;

    @FXML
    void inputbutton(ActionEvent event) {

        int value = 0;

        boolean error = false;
        try {

            value=Integer.parseInt(text.getText());

        }catch (Exception e){
            error= true;
        }

        if (error){

            identificationlabel.setText(     "Write a number. "       +identificationlabel.getText());

        }else {
            if (!busCap){

                if (value>50 || value<1){

                    identificationlabel.setText("Capacity couldn't be over 50 or below 0. Recommended values are 5 to 10");

                }
                else {

                    buttoninput.setText("Set Passenger Size");
                    identificationlabel.setText("It defines number of Passengers to read from the passenger.txt 10 to 300");
                    Constants.setBusCapacity(value);
                    busCap=true;
                }

            }else if (!pasSize){

                if (value>300 || value<10){

                    identificationlabel.setText("Please write a number 10 to 300");
                }
                else {

                    buttoninput.setVisible(false);
                    identificationlabel.setVisible(false);
                    text.setVisible(false);

                    Constants.setPassengerFileSize(value);
                    pasSize=true;

                    button.setVisible(true);

                }


            }
        }



    }

    @FXML
    void passbutton(ActionEvent event) {

        String userDirectoryString = System.getProperty("user.dir");
        File userDirectory = new File(userDirectoryString);

        buttonpass.setVisible(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("CHOOSE --> \"passenger.txt\"");
        fileChooser.setInitialDirectory(userDirectory);

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile!=null){

            if (selectedFile.getName().equals("passenger.txt")){

                passlabel.setVisible(false);
                buttoninput.setVisible(true);
                text.setVisible(true);
                identificationlabel.setVisible(true);

            }else {

                buttonpass.setVisible(true);

            }



        }else {

            buttonpass.setVisible(true);

        }

    }

    @FXML
    void buttonclick(ActionEvent event) {
        button.setVisible(false);


        long initTime = Thread.activeCount();//beginning time
        Counter.Count.setInitTime(initTime);

        int sleep=2000;

        int finalSleep = sleep;
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(finalSleep);
                } catch (InterruptedException e) {
                    System.out.println("interrupt");
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                currentsituation.setText("Program Starts ...");
                CreateStops();
            }
        });
        new Thread(sleeper).start();
        //currentsituation.setText("Program Başlıyor...");

        BufferedReader br;
        final String[] currentLine = new String[1];
        Random random = new Random();

        int lineLimit =Constants.passengerFileSize;  //can be changed int constants file
        int randomPassengerLimit = Constants.randomPassengerLimit; //can be changed int constants file

        int lineCounter=0;  //it should be equal to number of passenger file's row


        try {
            br=new BufferedReader(new FileReader("passenger.txt"));

            while (lineCounter<lineLimit ){


                int lineToRead = random.nextInt(randomPassengerLimit);
                lineCounter += lineToRead;


                if (lineCounter>lineLimit ){

                    int impossibleLine = lineCounter-lineLimit ;
                    lineToRead -= impossibleLine;

                }

                int holdLineToRead = lineToRead;

                sleep+=2000;
                int finalSleep2 = sleep;
                sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(finalSleep2);
                        } catch (InterruptedException e) {
                            System.out.println("interrupt");
                        }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        currentsituation.setText("Passengers Enter the Stops");
                    }
                });
                new Thread(sleeper).start();

                while (lineToRead>0){

                    lineToRead--;
                    sleep+=50;
                    int finalSleep3 = sleep;
                    sleeper = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                Thread.sleep(finalSleep3);
                            } catch (InterruptedException e) {
                                System.out.println("interrupt");
                            }
                            return null;
                        }
                    };
                    sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {

                            try {
                                currentLine[0] = br.readLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            String passengerName = currentLine[0].split(" ")[0];

                            String stopIn = currentLine[0].split(" ")[1];

                            String stopOut = currentLine[0].split(" ")[2];

                            Passenger newPassenger = new Passenger(passengerName,stopIn,stopOut);

                            allPassengers.add(newPassenger);  //add the passenger into system data
                            Counter.Count.newPassenger();
                            Counter.Count.addPassToStop();

                            allStops.get(stopIn).addPassenger(newPassenger);// add the passenger into passenger queue

                        }

                    });
                    new Thread(sleeper).start();

                }

                sleep+=2000;

                int finalSleep3 = sleep;
                sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(finalSleep3);
                        } catch (InterruptedException e) {
                            System.out.println("interrupt");
                        }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        currentsituation.setText(holdLineToRead+" passengers entered the stops");

                        int sgtpassa = allStops.get("SogutluCesme").getNumberOfPass();
                        sgtpass.setText(String.valueOf(sgtpassa));

                        int fikirpassa = allStops.get("Fikirtepe").getNumberOfPass();
                        fikirpass.setText(String.valueOf(fikirpassa));

                        int bogazpassa = allStops.get("Bogazici").getNumberOfPass();
                        bogazpass.setText(String.valueOf(bogazpassa));

                        int zincirpassa = allStops.get("Zincirlikuyu").getNumberOfPass();
                        zincirpass.setText(String.valueOf(zincirpassa));

                        mecidpass.setText("0");
                    }

                });
                new Thread(sleeper).start();


                //remove the passenger if it is the arrival stop of him/her

                sleep+=2000;
                int finalSleep4 = sleep;
                sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(finalSleep4);
                        } catch (InterruptedException e) {
                            System.out.println("interrupt");
                        }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {

                        int busCounter = moveBus();

                        String message ;

                        if (busCounter == 0){

                            message="No buses depart from the center";

                        }else {
                            message=busCounter+" bus depart from Sogutlucesme stop\n" +
                                    " Passengers boarded buses at their stops.";

                        }
                        currentsituation.setText(message );

                        //System.out.println(String.valueOf(Integer.parseInt(busshift.getText())+busCounter));
                        //busshift.setText(String.valueOf(Integer.parseInt(busshift.getText())+busCounter));
                        busshift.setText(String.valueOf(allBusses.size()));

                        int sgtpassa = allStops.get("SogutluCesme").getNumberOfPass();
                        sgtpass.setText(String.valueOf(sgtpassa));

                        int fikirpassa = allStops.get("Fikirtepe").getNumberOfPass();
                        fikirpass.setText(String.valueOf(fikirpassa));

                        int bogazpassa = allStops.get("Bogazici").getNumberOfPass();
                        bogazpass.setText(String.valueOf(bogazpassa));

                        int zincirpassa = allStops.get("Zincirlikuyu").getNumberOfPass();
                        zincirpass.setText(String.valueOf(zincirpassa));

                        int mecidpassa = allStops.get("Mecidiyekoy").getNumberOfPass();
                        mecidpass.setText(String.valueOf(mecidpassa));
                    }
                });
                new Thread(sleeper).start();


                sleep+=2000;
                int finalSleep5 = sleep;
                sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(finalSleep5);
                        } catch (InterruptedException e) {
                            System.out.println("interrupt");
                        }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        currentsituation.setText("The buses are on their way, After about " +Constants.timeToArrive+ "  minutes they will reach the other stop");
                        //busshift.setText(String.valueOf(allBusses.size()));
                    }
                });
                new Thread(sleeper).start();

            }

            sleep+=2000;
            int finalSleep5 = sleep;
            sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(finalSleep5);
                    } catch (InterruptedException e) {
                        System.out.println("interrupt");
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    currentsituation.setText("There are no new passengers left at the stops \n" +
                            "Buses will continue to run for the remaining passengers.");

                    int sgtpassa = allStops.get("SogutluCesme").getNumberOfPass();
                    sgtpass.setText(String.valueOf(sgtpassa));

                    int fikirpassa = allStops.get("Fikirtepe").getNumberOfPass();
                    fikirpass.setText(String.valueOf(fikirpassa));

                    int bogazpassa = allStops.get("Bogazici").getNumberOfPass();
                    bogazpass.setText(String.valueOf(bogazpassa));

                    int zincirpassa = allStops.get("Zincirlikuyu").getNumberOfPass();
                    zincirpass.setText(String.valueOf(zincirpassa));

                    int mecidpassa = allStops.get("Mecidiyekoy").getNumberOfPass();
                    mecidpass.setText(String.valueOf(mecidpassa));
                }
            });
            new Thread(sleeper).start();





            sleep+=1500;
            int finalSleep4 = sleep;
            sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(finalSleep4);
                    } catch (InterruptedException e) {
                        System.out.println("interrupt");
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {


                    while (allPassengers.size()>0){
                        int busCounter= moveBus();
                        //System.out.println(busCounter);

                        String message ;

                        if (busCounter == 0){

                            message="No buses depart from the center";

                        }else {
                            message=busCounter+" bus depart from Sogutlucesme stop";

                        }

                        currentsituation.setText(message );
                        //System.out.println(String.valueOf(Integer.parseInt(busshift.getText())+busCounter)+"kk");
                        busshift.setText(allBusses.size()+"");
                        int sgtpassa = allStops.get("SogutluCesme").getNumberOfPass();
                        sgtpass.setText(String.valueOf(sgtpassa));

                        int fikirpassa = allStops.get("Fikirtepe").getNumberOfPass();
                        fikirpass.setText(String.valueOf(fikirpassa));

                        int bogazpassa = allStops.get("Bogazici").getNumberOfPass();
                        bogazpass.setText(String.valueOf(bogazpassa));

                        int zincirpassa = allStops.get("Zincirlikuyu").getNumberOfPass();
                        zincirpass.setText(String.valueOf(zincirpassa));

                        int mecidpassa = allStops.get("Mecidiyekoy").getNumberOfPass();
                        mecidpass.setText(String.valueOf(mecidpassa));
                    }
                }
            });
            new Thread(sleeper).start();




            sleep+=1500;
            int finalSleep12 = sleep;
            sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(finalSleep12);
                    } catch (InterruptedException e) {
                        System.out.println("interrupt");
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    currentsituation.setText("All passengers have reached their destinations.\n" +
                            "buses will go to the last stop." );
                    int sgtpassa = 0;
                    sgtpass.setText(String.valueOf(sgtpassa));

                    int fikirpassa = 0;
                    fikirpass.setText(String.valueOf(fikirpassa));

                    int bogazpassa =0;
                    bogazpass.setText(String.valueOf(bogazpassa));

                    int zincirpassa =0;
                    zincirpass.setText(String.valueOf(zincirpassa));

                    int mecidpassa = 0;
                    mecidpass.setText(String.valueOf(mecidpassa));
                    //(String.valueOf(Integer.parseInt(busshift.getText())));

                }
            });
            new Thread(sleeper).start();





            sleep+=2000;
            int finalSleep7 = sleep;
            sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(finalSleep7);
                    } catch (InterruptedException e) {
                        System.out.println("interrupt");
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    if (Integer.parseInt(busshift.getText())>0) {
                        currentsituation.setText("Buses go towards the last stop\n");
                    }
                }
            });
            new Thread(sleeper).start();



            sleep+=2500;
            int finalSleep77 = sleep;
            sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(finalSleep77);
                    } catch (InterruptedException e) {
                        System.out.println("interrupt");
                    }
                    return null;
                }
            };
            int finalSleep1 = sleep;
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    //System.out.println(busshift.getText());

                    currentsituation.setText("All buses arrived at the last stop. The program is over.\n" +
                            " The system will shut down after 10 seconds.");
                    busshift.setText(0+"");
                    long outTime = Thread.activeCount();// ending time
                    Counter.Count.setOutTime(outTime);


                    double systemTime = Counter.Count.getSystemTime()/1000.0;// end - begin
                    BigDecimal d = BigDecimal.valueOf(systemTime+ finalSleep1/1000.0);
                    DecimalFormat f = new DecimalFormat("##.000");

                    lengthofsystem.setText("Total elapsed time: " + f.format(d) +" seconds");

                    StringBuilder message = new StringBuilder(); //usage of strBuilder

                    message.append("Total number of buses used : ").append(Counter.Count.getTotalBusCount()).append(
                            "\nTotal number of passengers : ").append(Counter.Count.getTotalPassengerCount()).append(
                                    "\nUsed bus capacity : ").append(Constants.busCapacity).append(
                                            "\nTotal elapsed time : ").append(f.format(d)).append(" seconds");

                    try {

                        BufferedWriter brw = new BufferedWriter(new FileWriter("projectInfo.txt"));
                        brw.write(message.toString());
                        brw.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            new Thread(sleeper).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private static void CreateStops(){

        for (String stopName: Constants.busStopNames){

            allStops.put(stopName,new Stop(stopName));

        }
    }

    private static void CreateBus(){

        Bus newBus = new Bus("SogutluCesme");

        ArrayList<Bus> newArr = allBusses;

        newArr.add(newBus);

        allBusses =newArr;
        //System.out.println(allBusses.size());

        Counter.Count.newBus();


    }

    public static int moveBus(){

        for (Stop stop: allStops.values()){

            String stopName = stop.getName();

            for (Bus bus : allBusses){

                String busStop = bus.getCurrentStop();

                if (stopName.equals(busStop)){

                    addPassOnStop(bus,stop);
                }
            }
        }

        for (Bus bus: allBusses){

            removePassOnStop(bus);

        }
        allBusses=Bus.moveFurther(allBusses);


        int busCounter =0;
        int maxBusInTheSameTime = Constants.maxBusAtTheSameTime;//2

        while (Counter.Count.doWeNeedBus() && busCounter < maxBusInTheSameTime ) {

            CreateBus();
            busCounter++;

        }

        return busCounter;


    }

    private static void removePassOnStop(Bus bus){

        String thisStop = bus.getCurrentStop();
        ArrayList<Passenger> passengersOnBus=bus.getPassengersOnBus();

        ArrayList<Passenger> toRemove= new ArrayList<>();

        for ( Passenger passenger :passengersOnBus ){

            if( passenger.getStopOut().equals(thisStop)){//if it is the stop that the passenger wants to get out of the bus

                toRemove.add(passenger);

            }
        }
        for (Passenger passenger: toRemove){

            bus.passengerIsOut(passenger);
            allPassengers.remove(passenger);
            Counter.Count.removePassFromBus();

        }
    }

    private static void addPassOnStop(Bus bus, Stop stop){

        while (true){

            int capacity =Constants.busCapacity; //20

            if (bus.getPassengersOnBus().size() == capacity){//if the bus is full

                break;                // no further passengers are allowed to get in
            }

            Passenger passenger = stop.pollPassenger();
            if (passenger!= null){

                Counter.Count.removePassFromStop();
                bus.passengerIsIn(passenger);
                Counter.Count.addPassToBus();

            }else {

                break;

            }
        }
    }

    public static ArrayList<Bus> getAllBusses() {
        return allBusses;
    }
}
