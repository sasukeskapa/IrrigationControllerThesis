package hu.sasukeskapa;

import java.util.ArrayList;

public class AutoProgram {
    private int startTime = 0;                  //starttime
    private int endTime = 0;                    //endtime
    private ArrayList<Integer> valveList;       //valvelist
    private int irrigationTime = 0;             //irrigationtime
    private int irrigationDelta = 0;            //irrigationdelta

    public AutoProgram() {
        valveList = new ArrayList<>();
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
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

    @Override
    public String toString() {
        return "\nAutoProgram{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", valveList=" + valveList +
                ", irrigationTime=" + irrigationTime +
                ", irrigationDelta=" + irrigationDelta +
                '}';
    }
}
