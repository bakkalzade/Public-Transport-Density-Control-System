package sample;

public class Counter{

    static class Count {

        private static int totalBusCount =0;
        private static int totalPassengerCount=0;
        private static int allPassengersOnStop;
        private static int allPassengersOnBusses;
        private static int allCount=0;
        private static double systemTime;

        public static void setSystemTime(double systemTime) {
            Count.systemTime = systemTime;
        }

        public static int getTotalBusCount() {
            return totalBusCount;
        }

        public static int getTotalPassengerCount() {
            return totalPassengerCount;
        }

        private static long initTime;
        private static long outTime;

        public Count() {

        }

        public static int getAllCount() {
            return allCount;
        }

        public int getAllPassengersOnStop() {
            return allPassengersOnStop;
        }

        public static void setInitTime(long initTime) {
            Count.initTime = initTime;

        }

        public static void setOutTime(long outTime) {
            Count.outTime = outTime;

        }

        public int getAllPassengersOnBusses() {
            return allPassengersOnBusses;

        }

        public static boolean doWeNeedBus(){

            int busCount = Controller.getAllBusses().size();
            int busCapacity = Constants.busCapacity;

            int totalCapacity= busCount*busCapacity;

            int allPassengers = allPassengersOnStop+allPassengersOnBusses;


            return allPassengers > totalCapacity;

        }

        public static long getSystemTime(){

            return outTime-initTime;
        }

        public static void newPassenger(){
            totalPassengerCount++;
            allCount++;

        }

        public static void newBus(){
            totalBusCount++;
            allCount++;
        }

        public static void addPassToStop(){

            allPassengersOnStop++;

        }
        public static void removePassFromStop(){

            allPassengersOnStop--;

        }

        public static void addPassToBus() {

            allPassengersOnBusses++;

        }
        public static void removePassFromBus() {

            allPassengersOnBusses--;

        }
    }

}

