package hu.sasukeskapa;

import java.time.LocalTime;
import java.util.PriorityQueue;

public class CommandScheduler implements Runnable {
    private DataBase db;
    private PriorityQueue<IrrigationEvent> irrigationSchedule;
    private IoController ioController;
    private boolean stop = false;

    @Override
    public void run() {
        runProgram();
    }

    public CommandScheduler(DataBase db) {
        this.db = db;
        this.irrigationSchedule = db.getIrrigationSchedule();
        db.setNewDB(false);
        ioController = new IoController();
    }

    void runProgram() {
        //initializing the variables
        int previousNow = 0;
        int now = 0;
        IrrigationEvent irrigationEvent;
        //run the program
        while (!stop) {
            //check if the DB was updated and we can use the fresh data
            if (db.isNewDbGenerated()) {
                irrigationSchedule = db.getIrrigationSchedule();
                db.setNewDB(false);
                System.out.println("New DB loaded. With " + db.getIrrigationSchedule().size() + " IrrigationEvents.");
            }
            //get current time of day in seconds
            now = LocalTime.now().getHour() * 3600 +
                    LocalTime.now().getMinute() * 60 +
                    LocalTime.now().getSecond();
            //check if it's a new day and we need to regenerate the irrigation schedule
            if (now < previousNow) {
                if (db.getMode() == 0) {     //0-automatic, 1-manual
                    db.generateIrrigationSchedule();
                    irrigationSchedule = db.getIrrigationSchedule();
                } else {
                    shutdownGpio();
                }
            }
            //we just sleep if the irrigation schedule is empty or the irrigation is stopped
            if (db.stop() || irrigationSchedule.isEmpty()) {
                shutdownGpio();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //reset the valves
                ioController.offAllPins();
                //System.out.println(LocalTime.now() + " - valves off.");
                if (irrigationSchedule.peek().getStartTime() > (now + 1)) {
                    //long sleep if the next irrigation event is scheduled 2 or more seconds in the future
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (irrigationSchedule.peek().getStartTime() > now) {
                    //short sleep if the next irrigation event is scheduled in the near future
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    //testing code: (next line)
                    //try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }
                    //run the irrigation for the current valve
                    irrigationEvent = irrigationSchedule.poll();
                    ioController.onPin(irrigationEvent.getValve());
                    //System.out.println(LocalTime.now() + " - valve " + irrigationEvent.getValve() + " on.");
                    for (int i = 0; i < irrigationEvent.getIrrigationTime(); i++) {
                        if (db.isNewDbGenerated() || db.stop())
                            i = irrigationEvent.getIrrigationTime();
                        else {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            previousNow = now;
        }
        //shutdown all pins
        shutdownGpio();
    }

    public void stop() {
        stop = true;
    }

    public void newProgram(DataBase db, Boolean newDB) {
        this.db = db;
        this.irrigationSchedule = db.getIrrigationSchedule();
        db.setNewDB(false);
    }

    private void shutdownGpio() {
        //stop = true;
        //shutdown all pins
        if (ioController == null) {
            ioController = new IoController();
        }
        ioController.shutdownAllPins();
    }
}
