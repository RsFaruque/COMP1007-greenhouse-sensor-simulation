package logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Logger {
    private String logfile = "src/logs.txt";

    public void log(Object obj) {
        try (
            FileOutputStream file = new FileOutputStream("src/logs.txt", true);
            OutputStreamWriter streamWriter = new OutputStreamWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(streamWriter);
        ) {
            buffWriter.write(obj.toString());
            buffWriter.write("\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print(obj.toString());
    }
}