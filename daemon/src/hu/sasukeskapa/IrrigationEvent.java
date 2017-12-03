package hu.sasukeskapa;

public class IrrigationEvent implements Comparable<IrrigationEvent> {
    private int startTime = 0;
    private int irrigationTime = 0;
    private int valve = 0;

    public IrrigationEvent(int startTime, int irrigationTime, int valve) {
        this.startTime = startTime;
        this.irrigationTime = irrigationTime;
        this.valve = valve;
    }

    public int getStartTime() {
        return startTime;
    }

    //returns the index of the valve (0,1,2,3,....)
    public int getValve() {
        //the valve is an index
        //but for the users' ease of use it starts with 1
        //so convert it to the correct value here
        return valve - 1;
    }

    public int getIrrigationTime() {
        return irrigationTime;
    }

    private void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    private void setIrrigationTime(int irrigationTime) {
        this.irrigationTime = irrigationTime;
    }

    private void setValve(int valve) {
        this.valve = valve;
    }

    @Override
    public int compareTo(IrrigationEvent irrigationEvent) {
        if (this.startTime == irrigationEvent.getStartTime()) {
            return 0;
        } else if (this.startTime < irrigationEvent.getStartTime()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "\nIrrigationEvent{" +
                "startTime=" + startTime +
                ", irrigationTime=" + irrigationTime +
                ", valve=" + valve +
                '}';
    }
}
