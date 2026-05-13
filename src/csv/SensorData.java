package csv;

import dataTypes.SensorReading;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SensorData {
    SensorReading[] sensorReadings;

    public SensorData(String filePath) {
        // SensorData csvData = new SensorData();
        try (FileInputStream fileStream = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fileStream);
            BufferedReader buffer = new BufferedReader(isr);
        ){     
            buffer.readLine();                 // remove column names
            String line = buffer.readLine();   // data starts from here
            while (line != null) {
                append(SensorReading.stringToSensorReading(line));
            }
            fileStream.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public final void append(SensorReading sensorReading) {
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
            if (evaluateFilter(sensorReadings[i], dataRange) 
            && sensorReadings[i].getValue() < minVal) {
                minVal = sensorReadings[i].getValue();
            }
        }
        return minVal;
    }

    public double getMaxValue(String dataRange) {
        double maxVal = sensorReadings[0].getValue();
        for (int i = 0; i > sensorReadings.length; i++) {
            if (evaluateFilter(sensorReadings[i], dataRange) 
            && sensorReadings[i].getValue() < maxVal) {
                maxVal = sensorReadings[i].getValue();
            }
        }
        return maxVal;
    }

    public double getMeanValue(String dataRange) {
        double total = 0;
        for (int i = 0; i < sensorReadings.length; i++) {
            if (evaluateFilter(sensorReadings[i], dataRange))
                total += sensorReadings[i].getValue();
        }
        return total / sensorReadings.length;
    }

    public int getUnsafeReadingCount(String dataRange) {
        int count = 0;
        for (int i = 0; i < sensorReadings.length; i++) {
            SensorReading sensor = sensorReadings[i];
            if (evaluateFilter(sensor, dataRange)
            && sensor.getValue() > sensor.getUpperBound()
            || sensor.getValue() < sensor.getLowerBound()) {
                count++;
            }
        }
        return count;
    }

    public double getUnsafeReadingPercent(String dataRange) {
        return (double) getUnsafeReadingCount(dataRange) / sensorReadings.length * 100;
    }

    private boolean evaluateFilter(SensorReading sensor, String dataRange) {
        if (dataRange.equals("all"))
            return true;
        return sensor.getSensorType().equals(dataRange) 
            || sensor.getZone().equals(dataRange);
    }
}