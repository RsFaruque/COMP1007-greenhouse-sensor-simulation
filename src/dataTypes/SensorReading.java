package dataTypes;

import java.security.InvalidParameterException;

public class SensorReading {
    private String sensorID;
    private String sensorType;
    private String zone;
    private double value;
    private Timestamp timestamp;

    public SensorReading (String sensorID, String sensorType, String zone, double value, Timestamp timestamp) throws InvalidParameterException {
        this.sensorID = validateSensorID(sensorID);
        this.sensorType = validateSensorType(sensorType);
        this.zone = validateZone(zone);
        this.value = value;
        this.timestamp = timestamp;
    }

    public static SensorReading stringToSensorReading(String line) {
        String[] data = line.split(",");

        return new SensorReading(
            data[5],  // sensorID
            data[6],  // sensorType
            data[7],  // zone
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

    private String validateZone(String zone) throws InvalidParameterException{
        if (!zone.substring(0, 4).equals("Zone")) {
            throw new InvalidParameterException();
        }
        for (int i = 4; i < zone.length(); i++) {
           if (zone.charAt(i) == ' ') {
            throw new InvalidParameterException();
           }
        }
        return zone;
    }

    private String validateSensorType(String sensor) throws InvalidParameterException {
        if (!(sensor.equals("temperature")
        || sensor.equals("humidity")
        || sensor.equals("soilMoisture")
        || sensor.equals("light"))) {
            throw new InvalidParameterException();
        }
        return sensor;
    }

    private String validateSensorID(String sensorID) throws InvalidParameterException {
        if (!(sensorID.substring(0,3).equals("TMP")    // checks the 3 character sensor ID
        || sensorID.substring(0,3).equals("HMD")
        || sensorID.substring(0,3).equals("LGT"))) {
            throw new InvalidParameterException();
        } else if (!sensorID.substring(0,4).equals("SOIL")) { // checks the 4 character sensor ID, i.e. SOIL
            throw new InvalidParameterException();
        }
        return sensorID;
    }
}