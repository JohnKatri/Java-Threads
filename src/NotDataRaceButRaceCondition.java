package basics;

public class NotDataRaceButRaceCondition {
//    Does not work because its not data race

    public static void main(String arg[]){
        SharedData sharedData = new SharedData();
        new Thread(() -> incrementThread(sharedData)).start();
        new Thread(() -> incrementThread(sharedData)).start();
        new Thread(() -> incrementThread(sharedData)).start();
        new Thread(() -> check(sharedData)).start();
    }
    private static void incrementThread(SharedData sharedData){
        for(int i=1; i <= Integer.MAX_VALUE; i++){
            sharedData.increment();
        }
    }
    private static void check(SharedData sharedData){
        for(int i=1; i <= Integer.MAX_VALUE; i++){
            sharedData.checkForDataRace();
        }
    }
    private static class SharedData{

        private volatile int x;
        private volatile int y;
        public  void increment(){
            x++;
            y++;
        }

        public  void checkForDataRace(){
            if( y > x ){
                System.out.println("Data race detected");
            }
        }
    }

//Replication of race condition by removing volatile

//    public static void main(String arg[]){
//        SharedData sharedData = new SharedData();
//        new Thread(() -> incrementThread(sharedData)).start();
//        new Thread(() -> incrementThread(sharedData)).start();
//        new Thread(() -> incrementThread(sharedData)).start();
//        new Thread(() -> check(sharedData)).start();
//    }
//    private static void incrementThread(SharedData sharedData){
//        for(int i=1; i <= Integer.MAX_VALUE; i++){
//            sharedData.increment();
//        }
//    }
//    private static void check(SharedData sharedData){
//        for(int i=1; i <= Integer.MAX_VALUE; i++){
//            sharedData.checkForDataRace();
//        }
//    }
//    private static class SharedData{
//        private int x;
//        private int y;
//        public synchronized void increment(){
//            x++;
//            y++;
//        }
//
//        public synchronized void checkForDataRace(){
//            if( y > x ){
//                System.out.println("Data race detected");
//            }
//        }
//    }
//
//}

}