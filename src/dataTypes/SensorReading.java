package dataTypes;

public class SensorReading {
    String sensorID;
    String sensorType;
    String zone;
    double value;
    Timestamp timestamp;

    public SensorReading (String sensorID, String sensorType, String zone, double value, Timestamp timestamp) {
        this.sensorID = sensorID;
        this.sensorType = sensorType;
        this.zone = zone;
        this.value = value;
        this.timestamp = timestamp;
    }

    public static SensorReading stringToSensorReading(String line) {
        String[] data = line.split(",");

        return new SensorReading(
            data[5],
            data[6],
            data[7],
            Double.parseDouble(data[8]),
            Timestamp.stringToTimestamp(line)
        );
    }

}