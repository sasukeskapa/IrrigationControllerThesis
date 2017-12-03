package hu.sasukeskapa;

public class InputManager implements Runnable {
    private InputDataBase inputDataBase;
    private ConfigFileMonitor configFileMonitor;
    private CommandParser commandParser;
    private DataBase db;

    public InputManager(DataBase db) {
        inputDataBase = null;
        configFileMonitor = new ConfigFileMonitor();
        commandParser = new CommandParser();
        this.db = db;
    }

    @Override
    public void run() {
        while (true) {
            //check config files
            inputDataBase = configFileMonitor.getConfigFilesIfPossible();

            //parse config files
            if (inputDataBase != null) {
                //System.out.println(inputDataBase);
                //LocalTime localTimeStart = LocalTime.now();
                //System.out.println("parsing starts: " + localTimeStart);
                //wait until the last DB is loaded by the command scheduler
                while (db.isNewDbGenerated()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                db = commandParser.generateDbFromJson(inputDataBase, db);
                db.setNewDB(true);

                //LocalTime localTimeEnd = LocalTime.now();
                //System.out.println("parsing ends:   " + localTimeEnd);
                //System.out.println(" mode: " + db.getMode() + " stop: " + db.stop() + " parsing time: " + (localTimeEnd.getNano()-localTimeStart.getNano())/1000000);
                //System.out.println(db);
            }

            //sleep a bit
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
