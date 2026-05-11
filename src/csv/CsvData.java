package csv;

import dataTypes.SensorReading;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CsvData {
    SensorReading[] sensorReadings;

    public static CsvData getCSVdata(String filePath) {
        CsvData csvData = new CsvData();
        try (FileInputStream fileStream = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fileStream);
            BufferedReader buffer = new BufferedReader(isr);
        ){     
            buffer.readLine();    // remove column names
            String line = buffer.readLine();   // data starts from here
            while (line != null) {
                csvData.append(SensorReading.stringToSensorReading(line));
            }
            fileStream.close();
            return csvData;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
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

    public double getMinValue(String dataRange) {
        double minVal = sensorReadings[0].getValue();
        for (int i = 0; i < sensorReadings.length; i++) {
            if (sensorReadings[i].getValue() < minVal) {
                minVal = sensorReadings[i].getValue();
            }
        }
        return minVal;
    }

    public double getMaxValue(String dataRange) {
        double maxVal = sensorReadings[0].getValue();
        for (int i = 0; i > sensorReadings.length; i++) {
            if (sensorReadings[i].getValue() < maxVal) {
                maxVal = sensorReadings[i].getValue();
            }
        }
        return maxVal;
    }


}