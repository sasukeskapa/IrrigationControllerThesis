package hu.sasukeskapa;

import java.util.ArrayList;

public class InputDataBase {
    private String stop = "";
    private String mode = "";       //0-automatic, 1-manual
    private String manualProgram = "";
    private ArrayList<String> autoPrograms;

    public InputDataBase() {
        autoPrograms = new ArrayList<>();
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    //0-automatic, 1-manual
    public String getMode() {
        return mode;
    }

    //0-automatic, 1-manual
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getManualProgram() {
        return manualProgram;
    }

    public void setManualProgram(String manualProgram) {
        this.manualProgram = manualProgram;
    }

    public ArrayList<String> getAutoPrograms() {
        return autoPrograms;
    }

    public void setAutoPrograms(ArrayList<String> autoPrograms) {
        this.autoPrograms = autoPrograms;
    }

    public void addToAutoPrograms(String autoProgram) {
        autoPrograms.add(autoProgram);
    }

    @Override
    public String toString() {
        return "InputDataBase{" + "\n" +
                "  shutdownGpio='" + stop + '\'' + "\n" +
                "  mode='" + mode + '\'' + "\n" +
                "  manualProgram='" + manualProgram + '\'' + "\n" +
                "  autoPrograms=" + autoPrograms + "\n" +
                '}';
    }
}
