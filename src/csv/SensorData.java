package csv;

import datatypes.SensorReading;
import datatypes.Timestamp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import logger.Logger;


public class SensorData {
    private SensorReading[] sensorReadings = new SensorReading[0];
    private String filePath = "";
    Logger logger = new Logger("src/logs.txt");

    // ------------------CONSTRUCTORS--------------------
    public SensorData(String filePath) throws IOException {
        this.filePath = filePath;
        readCsv(filePath);
    }

    public SensorData (SensorData data) {
        sensorReadings = new SensorReading[data.getSensorReadings().length];
        for (int i = 0; i < sensorReadings.length; i++) {
            sensorReadings[i] = new SensorReading(data.get(i));
        }        
        filePath = data.getFilePath();
    }

    public SensorData(){}

//-------------------SETTERS---------------------------------------
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void setSensorReadings(SensorReading[] readings) {
        sensorReadings = readings;
    } 

//-------------------GETTERS---------------------------------------
    public SensorReading[] getSensorReadings() {
        return sensorReadings.clone();
    }
    public String getFilePath() {
        return filePath;
    }
    public SensorReading get(int index) {
        return sensorReadings[index];
    }    


//---------HELPERS---------------------

    public final void append(SensorReading sensorReading) {
        SensorReading[] newArr = new SensorReading[sensorReadings.length + 1];

        for (int i = 0; i < sensorReadings.length; i++) {
            newArr[i] = sensorReadings[i];
        }
        newArr[sensorReadings.length] = sensorReading;
        sensorReadings = newArr;
    }

    public final void readCsv(String filePath) throws IOException {
        try (FileInputStream fileStream = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fileStream);
            BufferedReader buffer = new BufferedReader(isr);
        ){     
            buffer.readLine();                 // remove column names
            String line = buffer.readLine();   // data starts from here
            while (line != null) {
                try {
                    append(SensorReading.stringToSensorReading(line));
                } catch (IllegalArgumentException e) {
                    logger.logAndDisplay("Record was not read -> " + e.getMessage() + '\n');
                }
                line = buffer.readLine();
            }
        } catch (IOException e) {
            logger.logAndDisplay("\n" + e.getMessage() + "\n");
            throw new IOException(e.getMessage());
        }
    }

// ---------------------STATISTICS------------------------------
    public int getTotalCount(String dataRange) {
        int count = 0;
        if (dataRange.equals("all")) return sensorReadings.length;
        for (SensorReading sensor : sensorReadings) {
            if (evaluateFilter(sensor, dataRange)) {
                count++;
            }
        }
        return count;
    }

    public double getMinValue(String dataRange) throws Exception{
        if (sensorReadings.length == 0) throw new Exception("There are no readings");

        double minVal = 0;
        boolean first = true;
        for (SensorReading sensor : sensorReadings) {
            if (evaluateFilter(sensor, dataRange)) {
                if (first) {
                    minVal = sensor.getValue();
                    first = false;
                } else if (sensor.getValue() < minVal)
                    minVal = sensor.getValue();
            }
        }
        if (first) throw new Exception("No readings for sensors of " + dataRange); // Catch this in menu to show user data doesn't exist. Cleaner than using -ve since sensor data can be negative
        return minVal;
    }

    public double getMaxValue(String dataRange) throws Exception{
        if (sensorReadings.length == 0) throw new Exception("There are no readings");

        double maxVal = 0;
        boolean first = true;
        for (SensorReading sensor : sensorReadings) {
            if (evaluateFilter(sensor, dataRange)) {
                if (first) {
                    maxVal = sensor.getValue();
                    first = false;
                } else if (sensor.getValue() > maxVal)
                    maxVal = sensor.getValue();
            } 
        }
        if (first) throw new Exception("No readings for sensor of " + dataRange); // Catch this in menu to show data doesn't exist for this filter.
        return maxVal;
    }

    public double getMeanValue(String dataRange) throws Exception{
        if (sensorReadings.length == 0) throw new Exception ("There are no readings.");

        double sum = 0;
        int count = 0;
        for (SensorReading sensor : sensorReadings) {
            if (evaluateFilter(sensor, dataRange)) {
                sum += sensor.getValue();
                count++;
            }
        }
        if (count == 0) throw new Exception ("There are no readings for " + dataRange);
        return sum / count;
    }

    public void addData(String sensorID, String sensorType, String zone, double value, Timestamp time) throws IllegalArgumentException {

        SensorReading newReading = new SensorReading(
            sensorID,
            sensorType,
            zone,
            value,
            time
        );
        try (
            FileOutputStream file = new FileOutputStream(filePath);
            OutputStreamWriter streamWriter = new OutputStreamWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(streamWriter);
        ){
            String data = "day,month,year,hour,minute,sensorID,sensorType,zone,value\n";
            for (SensorReading sensor : sensorReadings) {
                data += sensor.getTimestamp().getDay()
                + "," + sensor.getTimestamp().getMonth()
                + "," + sensor.getTimestamp().getYear()
                + "," + sensor.getTimestamp().getHour()
                + "," + sensor.getTimestamp().getMinute()
                + "," + sensor.getSensorID()
                + "," + sensor.getSensorType()
                + "," + sensor.getZone()
                + "," + sensor.getValue()
                + '\n';
            }
            data += newReading.getTimestamp().getDay()
            + "," + newReading.getTimestamp().getMonth()
            + "," + newReading.getTimestamp().getYear()
            + "," + newReading.getTimestamp().getHour()
            + "," + newReading.getTimestamp().getMinute()
            + "," + newReading.getSensorID()
            + "," + newReading.getSensorType()
            + "," + newReading.getZone()
            + "," + newReading.getValue()
            + '\n';
            buffWriter.write(data);
            append(newReading);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteData(String sensorID) throws Exception {
        // Delete from file and shift array
        boolean deleteSuccessful = false;
        for (int i = 0; i < sensorReadings.length; i++) {
            if (sensorReadings[i].getSensorID().equals(sensorID)) {
                sensorReadings[i] = null;
                deleteSuccessful = true;
            }
        }
        if (!deleteSuccessful) throw new Exception("Deletion unsuccessful. So such sensor as " + sensorID);

        SensorReading[] newSensorReadings = new SensorReading[sensorReadings.length-1];
        int x = 0;
        for (SensorReading sensor : sensorReadings) {
            if (sensor != null) 
                newSensorReadings[x++] = sensor;
        }
        sensorReadings = newSensorReadings;
        
        // Write the changes to csv file
        try (
            FileOutputStream file = new FileOutputStream(filePath);
            OutputStreamWriter streamWriter = new OutputStreamWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(streamWriter);
        ){
            String data = "day,month,year,hour,minute,sensorID,sensorType,zone,value\n";
            for (SensorReading sensor : sensorReadings) {
                data += sensor.getTimestamp().getDay()
                + "," + sensor.getTimestamp().getMonth()
                + "," + sensor.getTimestamp().getYear()
                + "," + sensor.getTimestamp().getHour()
                + "," + sensor.getTimestamp().getMinute()
                + "," + sensor.getSensorID()
                + "," + sensor.getSensorType()
                + "," + sensor.getZone()
                + "," + sensor.getValue()
                + '\n';
            }
            buffWriter.write(data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


// ----------------PRIVATE----------------
    private boolean evaluateFilter(SensorReading sensor, String dataRange) {
        if (dataRange.equals("all")){
            return true;
        }
        return sensor.getSensorType().equals(dataRange) 
            || sensor.getZone().equals(dataRange);
    }

}