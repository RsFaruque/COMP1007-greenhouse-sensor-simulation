package csv;

import dataTypes.SensorReading;
import dataTypes.Timestamp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import logger.Logger;


public class SensorData {
    SensorReading[] sensorReadings = new SensorReading[0];
    String filePath = "";

    public SensorData(String filePath) {
        this.filePath = filePath;
        try (FileInputStream fileStream = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fileStream);
            BufferedReader buffer = new BufferedReader(isr);
        ){     
            buffer.readLine();                 // remove column names
            String line = buffer.readLine();   // data starts from here
            while (line != null) {
                append(SensorReading.stringToSensorReading(line));
                line = buffer.readLine();
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

    public SensorReading get(int index) {
        return sensorReadings[index];
    }    


    public int getTotalCount(String dataRange) {
        int count = 0;
        if (dataRange.equals("all")) return sensorReadings.length;
        for (int i = 0; i < sensorReadings.length; i++) {
            if (evaluateFilter(sensorReadings[i], dataRange)) {
                count++;
            }
        }
        return count;
    }

    public double getMinValue(String dataRange) throws Exception{
        double minVal = 0;
        boolean first = true;
        for (int i = 0; i < sensorReadings.length; i++) {
            if (evaluateFilter(sensorReadings[i], dataRange)) {
                if (first) {
                    minVal = sensorReadings[i].getValue();
                    first = false;
                } else if (sensorReadings[i].getValue() < minVal)
                    minVal = sensorReadings[i].getValue();
            }
        }
        if (first) throw new Exception("No readings for sensors of " + dataRange); // Catch this in menu to show user data doesn't exist. Cleaner than using neg since sensor data can be negative
        return minVal;
    }

    public double getMaxValue(String dataRange) throws Exception{
        double maxVal = 0;
        boolean first = true;
        for (int i = 0; i < sensorReadings.length; i++) {
            if (evaluateFilter(sensorReadings[i], dataRange)) {
                if (first) {
                    maxVal = sensorReadings[i].getValue();
                    first = false;
                } else if (sensorReadings[i].getValue() > maxVal)
                    maxVal = sensorReadings[i].getValue();
            } 
        }
        if (first) throw new Exception("No readings for sensor of " + dataRange); // Catch this in menu to show data doesn't exit.
        return maxVal;
    }

    public double getMeanValue(String dataRange) {
        double total = 0;
        int count = 0;
        for (int i = 0; i < sensorReadings.length; i++) {
            if (evaluateFilter(sensorReadings[i], dataRange)) {
                total += sensorReadings[i].getValue();
                count++;
            }
        }
        return total / count;
    }

    private boolean evaluateFilter(SensorReading sensor, String dataRange) {
        if (dataRange.equals("all")){
            return true;
        }
        return sensor.getSensorType().equals(dataRange) 
            || sensor.getZone().equals(dataRange);
    }

    public void addData(String sensorID, String sensorType, String zone, double value, Timestamp time) throws IllegalArgumentException {
        Logger logger = new Logger();
        SensorReading newReading = new SensorReading(
            sensorID,
            sensorType,
            zone,
            value,
            time
        );
        try (
            FileOutputStream file = new FileOutputStream(filePath, true);
            OutputStreamWriter streamWriter = new OutputStreamWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(streamWriter);
        ){
            buffWriter.write(
                "" + newReading.getTimestamp().getDay()
                + ',' + newReading.getTimestamp().getMonth()
                + ',' + newReading.getTimestamp().getYear()
                + ',' + newReading.getTimestamp().getHour()
                + ',' + newReading.getTimestamp().getMinute()
                + ',' + newReading.getSensorID()
                + ',' + newReading.getSensorType()
                + ',' + newReading.getZone()
                + ',' + newReading.getValue()
                + '\n'
            );
            append(newReading);
        } catch (IOException e) {
            logger.log(e.getMessage());
        }
    }

}