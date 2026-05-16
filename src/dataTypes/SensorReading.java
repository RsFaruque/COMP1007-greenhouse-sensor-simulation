package dataTypes;

import java.security.InvalidParameterException;

public class SensorReading {
    private String sensorID;
    private String sensorType;
    private String zone;
    private double value;
    private Timestamp timestamp;
    public final String[] sensorTypes = {"temperature", "soilMoiture", "humidity", "light"};
    public final String[] sensorPrefix = {"TMP", "SOIL", "HMD", "LGT"};


    public SensorReading (String sensorID, String sensorType, String zone, double value, Timestamp timestamp) throws InvalidParameterException {
        this.sensorID = validateSensorID(sensorID);
        this.sensorType = validateSensorType(sensorType);
        this.zone = validateZone(zone);
        this.value = value;
        this.timestamp = timestamp;
    }

    public static SensorReading stringToSensorReading(String line) throws InvalidParameterException {
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
    public void setValue(double val) {
        value = val;
    }

    public String getSensorType() {
        return sensorType;
    }
    public void setSensorType(String type) {
        sensorType = validateSensorType(type);
    }

    public String getSensorID() {
        return sensorID;
    }
    public void setSensorID(String id) {
        sensorID = validateSensorID(id);
    }

    public String getZone() {
        return zone;
    }
    public void setZone(String zone) {
        this.zone = zone;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp time) {
        timestamp = time;
    }


    public static String validateZone(String zone) throws InvalidParameterException{
        if (!(zone.equals("ZoneA")
        || zone.equals("ZoneB")
        || zone.equals("ZoneC"))) {
            throw new InvalidParameterException("Zone is not one of ZoneA, ZoneB, ZoneC | Zone entered: " + zone);
        }
        return zone;
    }

    public static String validateSensorType(String sensor) throws InvalidParameterException {
        if (!(sensor.equals("temperature")
        || sensor.equals("humidity")
        || sensor.equals("soilMoisture")
        || sensor.equals("light"))) {
            throw new InvalidParameterException("Invalid sensor type");
        }
        return sensor;
    }

    public static String validateSensorID(String sensorID) throws InvalidParameterException {
        if (!(sensorID.substring(0,3).equals("TMP")    // checks the 3 character sensor ID
        || sensorID.substring(0,3).equals("HMD")
        || sensorID.substring(0,3).equals("LGT")
        || sensorID.substring(0,4).equals("SOIL"))) {
            throw new InvalidParameterException("Invalid sensor ID");
        } 
        return sensorID;
    }
}