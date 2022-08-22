package sample;

public abstract class Constants {

    public static int busCapacity = 8;
    public static int passengerFileSize = 30; //if the passenger file is changed, change this variable too!
    public static final int randomPassengerLimit =10;
    public static final int timeToArrive = 10 ;// it will be represented in term of second
    public static final int maxBusAtTheSameTime = 1; // how many bus can be created in the same time

    public static final String[] busStopNames={//do not change

            "SogutluCesme",
            "Fikirtepe",
            "Bogazici",
            "Zincirlikuyu",
            "Mecidiyekoy"

    };

    public static void setBusCapacity(int busCapacity) {
        Constants.busCapacity = busCapacity;
    }

    public static void setPassengerFileSize(int passengerFileSize) {
        Constants.passengerFileSize = passengerFileSize;
    }
}
