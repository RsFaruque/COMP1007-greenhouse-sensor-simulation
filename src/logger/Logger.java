package logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Logger {
    private String logfile = "src/logs.txt";

    public void log(Object obj) {
        try (
            FileOutputStream file = new FileOutputStream("src/logs.txt");
            OutputStreamWriter streamWriter = new OutputStreamWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(streamWriter);
        ) {
            buffWriter.append(obj.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(obj.toString());
    }
}