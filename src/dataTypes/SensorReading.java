package dataTypes;

import java.security.InvalidParameterException;

public class SensorReading {
    private String sensorID;
    private String sensorType;
    private String zone;
    private double value;
    private Timestamp timestamp;
    private double upper;
    private double lower;


    public SensorReading (String sensorID, String sensorType, String zone, double value, Timestamp timestamp) throws InvalidParameterException {
        this.sensorID = validateSensorID(sensorID);
        this.sensorType = validateSensorType(sensorType);
        this.zone = validateZone(zone);
        this.value = value;
        this.timestamp = timestamp;

        switch (sensorType) {
            case "temperature" -> {
                upper = 30.0; 
                lower = 18.0;
            }
            case "humidity" -> {
                upper = 40.0;
                lower = 70.0;
            }
            case "soilMoisture" -> {
                upper = 30.0;
                lower = 60.0;
            }
            case "light" -> {
                upper = 300.0;
                lower = 1200.0;
            }
        }        
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

    public double getUpperBound() {
        return upper;
    }
    public double getLowerBound() {
        return lower;
    }

    private String validateZone(String zone) throws InvalidParameterException{
        if (!zone.substring(0, 4).equals("Zone")) {
            throw new InvalidParameterException();
        }
        for (int i = 4; i < zone.length(); i++) {
           if (zone.charAt(i) == ' ') {
            throw new InvalidParameterException("Invalid Zone format.");
           }
        }
        return zone;
    }

    private String validateSensorType(String sensor) throws InvalidParameterException {
        if (!(sensor.equals("temperature")
        || sensor.equals("humidity")
        || sensor.equals("soilMoisture")
        || sensor.equals("light"))) {
            throw new InvalidParameterException("Invalid sensor type");
        }
        return sensor;
    }

    private String validateSensorID(String sensorID) throws InvalidParameterException {
        if (!(sensorID.substring(0,3).equals("TMP")    // checks the 3 character sensor ID
        || sensorID.substring(0,3).equals("HMD")
        || sensorID.substring(0,3).equals("LGT")
        || sensorID.substring(0,4).equals("SOIL"))) {
            throw new InvalidParameterException("Invalid sensor ID");
        } 
        return sensorID;
    }
}