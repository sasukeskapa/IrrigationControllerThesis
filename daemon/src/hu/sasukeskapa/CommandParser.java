package hu.sasukeskapa;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.regex.Pattern;

public class CommandParser {
    //gets JSON and generates program
    private final static String timeRegex = "^([01][0-9]|2[0-3]):[0-9][0-9]:[0-9][0-9]$";
    private final static String numberRegex = "^\\d+$";
    private final static String valveListRegex = "^(\\d+,)*\\d+$";


    DataBase generateDbFromJson(InputDataBase inputDataBase, DataBase db) {
        //DataBase db = new DataBase();
        JsonParser jsonParser = new JsonParser();

        //setting shutdownGpio value
        db.setStop(!"0".equals(inputDataBase.getStop()));       //set shutdownGpio to false if the value is 0

        //setting mode value
        db.setMode(Integer.parseInt(inputDataBase.getMode()));  //0-automatic, 1-manual

        //setting manual program
        ManualProgram manualProgram = null;
        JsonObject jsonObject = null;
        try {
            jsonObject = (JsonObject) jsonParser.parse(inputDataBase.getManualProgram());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            String valvelist = jsonObject.get("valvelist").getAsString();
            String irrigationtime = jsonObject.get("irrigationtime").getAsString();
            String irrigationdelta = jsonObject.get("irrigationdelta").getAsString();
            String irrigationcount = jsonObject.get("irrigationcount").getAsString();
            //validate input
            if (Pattern.matches(numberRegex, irrigationcount) &&
                    Pattern.matches(timeRegex, irrigationtime) &&
                    Pattern.matches(timeRegex, irrigationdelta) &&
                    Pattern.matches(valveListRegex, valvelist)) {
                manualProgram = new ManualProgram();
                manualProgram.setIrrigationCount(Integer.parseInt(jsonObject.get("irrigationcount").getAsString()));
                manualProgram.setIrrigationTime(parseTime(irrigationtime));
                manualProgram.setIrrigationDelta(parseTime(irrigationdelta));
                for (String valve : valvelist.split(",")) {
                    int v = Integer.parseInt(valve);
                    if (v > 0)
                        manualProgram.addToValveList(v);
                }
            }
        } else {
            System.out.println("jsonObject is null in manual program generation");
        }
        db.setManualProgram(manualProgram);

        //setting automatic program
        AutoProgram autoProgram = null;
        db.clearAutoPrograms();
        for (String inputAutoProgram : inputDataBase.getAutoPrograms()) {
            autoProgram = null;
            jsonObject = null;
            try {
                jsonObject = (JsonObject) jsonParser.parse(inputAutoProgram);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonObject != null) {
                String starttime = jsonObject.get("starttime").getAsString();
                String endtime = jsonObject.get("endtime").getAsString();
                String valvelist = jsonObject.get("valvelist").getAsString();
                String irrigationtime = jsonObject.get("irrigationtime").getAsString();
                String irrigationdelta = jsonObject.get("irrigationdelta").getAsString();

                //validate input
                if (Pattern.matches(timeRegex, starttime) &&
                        Pattern.matches(timeRegex, endtime) &&
                        Pattern.matches(valveListRegex, valvelist) &&
                        Pattern.matches(timeRegex, irrigationtime) &&
                        Pattern.matches(timeRegex, irrigationdelta)) {
                    autoProgram = new AutoProgram();
                    autoProgram.setStartTime(parseTime(starttime));
                    autoProgram.setEndTime(parseTime(endtime));
                    for (String valve : valvelist.split(",")) {
                        int v = Integer.parseInt(valve);
                        if (v > 0)
                            autoProgram.addToValveList(v);
                    }
                    autoProgram.setIrrigationTime(parseTime(irrigationtime));
                    autoProgram.setIrrigationDelta(parseTime(irrigationdelta));
                    if (!(autoProgram.getStartTime() < autoProgram.getEndTime()))
                        autoProgram = null;
                }
            }
            if (autoProgram != null)
                db.addToAutoPrograms(autoProgram);
        }

        //generate the IrrigationSchedule
        db.generateIrrigationSchedule();

        //set the new DB flag
        db.setNewDB(true);

        //return the generated DB
        return db;
    }

    private int parseTime(String timeString) {
        int time = 0;
        time += Integer.parseInt(timeString.substring(6, 8));
        time += (Integer.parseInt(timeString.substring(3, 5)) * 60);
        time += (Integer.parseInt(timeString.substring(0, 2)) * 3600);
        if (time > 86399)
            time = 86399;
        return time;
    }
}
