package hu.sasukeskapa;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class ConfigFileMonitor {
    private final static String defaultLockFilePath = "/var/www/html/sources/lock.txt";
    private final static String defaultModifiedFilePath = "/var/www/html/sources/modified.txt";
    private final static String defaultModeSelectFilePath = "/var/www/html/sources/mode.txt";
    private final static String defaultStopAllFilePath = "/var/www/html/sources/manual_stop_all.txt";
    private final static String defaultManualProgramFilePath = "/var/www/html/sources/manual_program.txt";
    private final static String defaultConfigFilesPath = "/var/www/html/sources/";
    private boolean firstPass = true;
    //checks for new commands and passes them to the CommandParser

    public ConfigFileMonitor() {
        firstPass = true;
    }

    InputDataBase getConfigFilesIfPossible() {
        try {
            //check if the files are locked
            String lock = new String(Files.readAllBytes(Paths.get(defaultLockFilePath)));
            lock = lock.trim();
            if ("1".equals(lock)) {
                //if the files are locked we return and wait
                return null;
            }
            //check if the files are not modified and it's not the first run and just return
            String modified = new String(Files.readAllBytes(Paths.get(defaultModifiedFilePath)));
            modified = modified.trim();
            if ("0".equals(modified) && !firstPass) {
                //if the files are not modified and it's not the first run, we return as there's nothing to do
                return null;
            }
            //locking the files to deal with concurrency
            PrintWriter printWriter = new PrintWriter(new File(defaultLockFilePath));
            printWriter.print("1");
            printWriter.flush();
            printWriter.close();

            InputDataBase inputDataBase = getConfigFiles();

            //uncheck the modified "bit" (file)
            printWriter = new PrintWriter(new File(defaultModifiedFilePath));
            printWriter.print("0");
            printWriter.flush();
            printWriter.close();
            //unlock the config files
            printWriter = new PrintWriter(new File(defaultLockFilePath));
            printWriter.print("0");
            printWriter.flush();
            printWriter.close();

            firstPass = false;

            return inputDataBase;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputDataBase getConfigFiles() {
        try {
            InputDataBase inputDataBase = new InputDataBase();
            inputDataBase.setMode(new String(Files.readAllBytes(Paths.get(defaultModeSelectFilePath))).trim());
            inputDataBase.setStop(new String(Files.readAllBytes(Paths.get(defaultStopAllFilePath))).trim());
            inputDataBase.setManualProgram(new String(Files.readAllBytes(Paths.get(defaultManualProgramFilePath))).trim());
            File folder = new File(defaultConfigFilesPath);
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile() && Pattern.matches("^auto_program\\d+.txt$", file.getName())) {
                        inputDataBase.addToAutoPrograms(new String(Files.readAllBytes(Paths.get(file.getAbsolutePath()))).trim());
                    }
                }
            }
            return inputDataBase;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
