package hu.sasukeskapa;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class DataBase {
    private boolean newDB = false;
    private boolean stop = false;
    private int mode = 0;      //0-automatic, 1-manual
    private ManualProgram manualProgram;
    private ArrayList<AutoProgram> autoPrograms;
    private PriorityQueue<IrrigationEvent> irrigationSchedule;

    public final static int secondsPerDay = 86400;

    public DataBase() {
        manualProgram = null;
        autoPrograms = new ArrayList<>();
        irrigationSchedule = new PriorityQueue<>();
    }

    public void clearAutoPrograms() {
        autoPrograms.clear();
    }

    public boolean isNewDbGenerated() {
        return newDB;
    }

    public void setNewDB(boolean newDB) {
        this.newDB = newDB;
    }

    public boolean stop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    //0-automatic, 1-manual
    public int getMode() {
        return mode;
    }

    //0-automatic, 1-manual
    public void setMode(int mode) {
        this.mode = mode;
    }

    public ManualProgram getManualProgram() {
        return manualProgram;
    }

    public void setManualProgram(ManualProgram manualProgram) {
        this.manualProgram = manualProgram;
    }

    public ArrayList<AutoProgram> getAutoPrograms() {
        return autoPrograms;
    }

    public void setAutoPrograms(ArrayList<AutoProgram> autoPrograms) {
        this.autoPrograms = autoPrograms;
    }

    public void addToAutoPrograms(AutoProgram autoProgram) {
        autoPrograms.add(autoProgram);
    }

    public PriorityQueue<IrrigationEvent> getIrrigationSchedule() {
        return irrigationSchedule;
    }

    public void setIrrigationSchedule(PriorityQueue<IrrigationEvent> irrigationSchedule) {
        this.irrigationSchedule = irrigationSchedule;
    }

    public void addToIrrigationSchedule(IrrigationEvent irrigationEvent) {
        irrigationSchedule.add(irrigationEvent);
    }

    public void generateIrrigationSchedule() {
        irrigationSchedule.clear();
        //generate the queue the irrigation scheduler will use
        if (!stop && mode == 0 && !autoPrograms.isEmpty()) {    //auto mode (0-automatic, 1-manual)
            //stepping through
            for (AutoProgram currentAutoProgram : autoPrograms) {
                int now = LocalTime.now().getHour() * 3600 +
                        LocalTime.now().getMinute() * 60 +
                        LocalTime.now().getSecond() +
                        5;  //to have enough time for the scheduler to start with the first
                //current AutoProgram temp variables
                int startTime = currentAutoProgram.getStartTime();
                int endTime = currentAutoProgram.getEndTime();
                int irrigationTime = currentAutoProgram.getIrrigationTime();
                int irrigationDelta = currentAutoProgram.getIrrigationDelta();
                ArrayList<Integer> valveList = new ArrayList<>();
                valveList.addAll(currentAutoProgram.getValveList());
                //adding the current program to the IrrigationSchedule
                int startTimeForValveBatch = startTime;
                int endTimeForValveBatch = startTimeForValveBatch + (valveList.size() * irrigationTime);
                //testing code: (next line)
                //now = 0;
                while (endTimeForValveBatch < endTime && endTimeForValveBatch < secondsPerDay) {
                    if (now <= startTimeForValveBatch) {
                        for (int i = 0; i < valveList.size(); i++) {
                            int ieStartTime = startTimeForValveBatch + (i * irrigationTime);
                            int ieValve = valveList.get(i);
                            addToIrrigationSchedule(new IrrigationEvent(ieStartTime, irrigationTime, ieValve));
                        }
                    }
                    //incrementing startTimeForValveBatch with delta time and updating endTimeForValveBatch
                    startTimeForValveBatch += irrigationDelta;
                    endTimeForValveBatch = startTimeForValveBatch + (valveList.size() * irrigationTime);
                }
            }
        } else if (!stop && mode == 1 && manualProgram != null) {    //manual mode (0-automatic, 1-manual)
            //temp variables
            int irrigationCount = manualProgram.getIrrigationCount();
            int irrigationTime = manualProgram.getIrrigationTime();
            int irrigationDelta = manualProgram.getIrrigationDelta();
            ArrayList<Integer> valveList = new ArrayList<>();
            valveList.addAll(manualProgram.getValveList());
            //adding the current program to the IrrigationSchedule
            int startTimeForValveBatch = LocalTime.now().getHour() * 3600 +
                    LocalTime.now().getMinute() * 60 +
                    LocalTime.now().getSecond() +
                    1;  //to have enough time for the scheduler to start with the first
            int endTimeForValveBatch = startTimeForValveBatch + (valveList.size() * irrigationTime);
            int cycle = 0;
            while (cycle < irrigationCount && endTimeForValveBatch < secondsPerDay) {
                for (int j = 0; j < valveList.size(); j++) {
                    int ieStartTime = startTimeForValveBatch + (j * irrigationTime);
                    int ieValve = valveList.get(j);
                    addToIrrigationSchedule(new IrrigationEvent(ieStartTime, irrigationTime, ieValve));
                }
                //incrementing startTimeForValveBatch with delta time and updating endTimeForValveBatch
                startTimeForValveBatch += irrigationDelta;
                endTimeForValveBatch = startTimeForValveBatch + (valveList.size() * irrigationTime);
                cycle++;
            }
        }
    }

    @Override
    public String toString() {
        return "DataBase{" + "\n" +
                "  shutdownGpio=" + stop + "\n" +
                "  mode=" + mode + "\n" +
                "  manualProgram=" + manualProgram + "\n" +
                "  autoPrograms=" + autoPrograms + "\n" +
                "  irrigationSchedule=" + irrigationSchedule + "\n" +
                '}';
    }
}
