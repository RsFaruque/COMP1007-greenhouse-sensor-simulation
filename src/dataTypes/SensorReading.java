package dataTypes;

public class SensorReading {
    private String sensorID;
    private String sensorType;
    private String zone;
    private double value;
    private Timestamp timestamp;

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

    public double getValue() {
        return value;
    }
    public String getSensorType() {
        return sensorType;
    }
    public String getSensorID() {
        return sensorID;
    }
    public String getZone() {
        return zone;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
}