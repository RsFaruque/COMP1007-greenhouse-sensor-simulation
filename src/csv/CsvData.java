package csv;

import dataTypes.SensorReading;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CsvData {
    SensorReading[] sensorReadings;

    public static CsvData getCSVdata(String filePath) {
        FileInputStream fileStream; 
        InputStreamReader isr;
        BufferedReader buffer;
        CsvData csvData = new CsvData();
        try {
            fileStream = new FileInputStream(filePath);
            isr = new InputStreamReader(fileStream);
            buffer = new BufferedReader(isr);

            buffer.readLine();    // remove column names
            String line = buffer.readLine();   // data starts from here
            while (line != null) {
                csvData.append(SensorReading.stringToSensorReading(line));
            }
            fileStream.close();
            return csvData;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            try {
                fileStream.close();
                return null;

            } catch (IOException closingException) {
                System.out.println(closingException.getMessage());
                return null;
            }
        }
    }

    public void append(SensorReading sensorReading) {
        SensorReading[] newArr = new SensorReading[sensorReadings.length + 1];

        for (int i = 0; i < sensorReadings.length; i++) {
            newArr[i] = sensorReadings[i];
        }
        newArr[sensorReadings.length] = sensorReading;
        sensorReadings = newArr;
    }
}