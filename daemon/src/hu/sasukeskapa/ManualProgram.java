package hu.sasukeskapa;

import java.util.ArrayList;

public class ManualProgram {
    private ArrayList<Integer> valveList;   //valvelist
    private int irrigationTime = 0;         //irrigationtime
    private int irrigationDelta = 0;        //irrigationdelta
    private int irrigationCount = 0;        //irrigationcount

    public ManualProgram() {
        valveList = new ArrayList<>();
    }

    public ArrayList<Integer> getValveList() {
        return valveList;
    }

    public void setValveList(ArrayList<Integer> valveList) {
        this.valveList = valveList;
    }

    public void addToValveList(int valve) {
        valveList.add(valve);
    }

    public int getIrrigationTime() {
        return irrigationTime;
    }

    public void setIrrigationTime(int irrigationTime) {
        this.irrigationTime = irrigationTime;
    }

    public int getIrrigationDelta() {
        return irrigationDelta;
    }

    public void setIrrigationDelta(int irrigationDelta) {
        this.irrigationDelta = irrigationDelta;
    }

    public int getIrrigationCount() {
        return irrigationCount;
    }

    public void setIrrigationCount(int irrigationCount) {
        this.irrigationCount = irrigationCount;
    }

    @Override
    public String toString() {
        return "\nManualProgram{" +
                "valveList=" + valveList +
                ", irrigationTime=" + irrigationTime +
                ", irrigationDelta=" + irrigationDelta +
                ", irrigationCount=" + irrigationCount +
                '}';
    }
}
