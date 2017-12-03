package hu.sasukeskapa;

public class Main {

    public static void main(String[] args) {
        //fun Knight Rider tribute
        //Thread t = new Thread(new KnightRider());
        //t.start();

        //initial GPIO reset
        //IoController ioController = new IoController();
        //ioController.shutdownAllPins();
        //ioController = null;
        //System.out.println("Initial GPIO reset done.");

        //communication variables
        DataBase db = new DataBase();
        //input Manager  thread
        Thread inputThread = new Thread(new InputManager(db));
        inputThread.start();

        //command scheduler
        CommandScheduler commandScheduler = new CommandScheduler(db);
        //commandScheduler.runProgram();
        Thread irrigationThread = new Thread(commandScheduler);
        irrigationThread.start();
    }
}
