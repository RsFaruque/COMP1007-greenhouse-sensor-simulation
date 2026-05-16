package logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Logger {
    private String logfile = "";

    public Logger(String logfile) {
        this.logfile = logfile;
    }
    public Logger(Logger logger) {
        logfile = logger.getLogFilePath();
    }
    public Logger(){}


    public void setLogFilePath(String path) {
        this.logfile = path;
    }
    public String getLogFilePath() {
        return logfile;
    }

    public void log(String obj) {
        try (
            FileOutputStream file = new FileOutputStream(logfile, true);
            OutputStreamWriter streamWriter = new OutputStreamWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(streamWriter);
        ) {
            buffWriter.write(obj);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void logAndDisplay(String obj) {
        log(obj);
        System.out.print(obj);
    }
}