package businesslogic;

import csv.SensorData;
import datatypes.SensorReading;

public class SensorReadingRange {
    public boolean inUnsafeRange(SensorReading sensor) throws IllegalArgumentException {
        return switch(sensor.getSensorType()) {
            case "temperature" -> sensor.getValue() > 30 || sensor.getValue() < 18; 
            case "soilMoisture" -> sensor.getValue() > 60 || sensor.getValue() < 30;
            case "humidity" -> sensor.getValue() > 70 || sensor.getValue() < 40;
            case "light" -> sensor.getValue() > 1200 || sensor.getValue() < 300;
            default -> throw new IllegalArgumentException("Invalid sensor type");
        };
    }

    public int unsafeReadingCount(SensorData data, String dataRange) throws Exception {
        int total = data.getTotalCount("all");
        if (total == 0) throw new Exception("There are no readings");

        int count = 0;
        for (int i = 0; i < total; i++) {
            SensorReading sensor = data.get(i);
            if (dataRange.equals("all") || sensor.getSensorType().equals(dataRange) || sensor.getZone().equals(dataRange)) {
                if (inUnsafeRange(sensor)) count++;
            }
        }
        return count;
    }

    public double unsafeReadingPercent(SensorData data, String dataRange) throws Exception {
        int count = 0, total = 0;
        for (int i = 0; i < data.getTotalCount("all"); i++) {
            SensorReading sensor = data.get(i);
            if (dataRange.equals("all") || sensor.getSensorType().equals(dataRange) || sensor.getZone().equals(dataRange)) {
                if (inUnsafeRange(data.get(i))) count++;
                total++;
            }
        }
        if (total == 0) throw new Exception("There are no readings.");
        return (double) count / total * 100;
    } 
}