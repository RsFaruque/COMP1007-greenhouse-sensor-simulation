package datatypes;


public class SensorReading {
    private String sensorID;
    private String sensorType;
    private String zone;
    private double value;
    private Timestamp timestamp;

    
    public static final String[] sensorTypeList = {"temperature", "soilMoisture", "humidity", "light"};
    public static final String[] sensorPrefixes = {"TMP", "SOIL", "HMD", "LGT"};
    public static final String[] zoneList = {"ZoneA", "ZoneB", "ZoneC"};

    public SensorReading (String sensorID, String sensorType, String zone, double value, Timestamp timestamp) throws IllegalArgumentException {
        this.sensorID = validateSensorID(sensorID);
        this.sensorType = validateSensorType(sensorType);
        this.zone = validateZone(zone);
        this.value = value;
        this.timestamp = timestamp;
    }

    public static SensorReading stringToSensorReading(String line) throws IllegalArgumentException {
        String[] data = line.split(",");
        return new SensorReading(
            data[5],  // sensorID
            data[6],  // sensorType
            data[7],  // zone
            Double.parseDouble(data[8]),
            Timestamp.stringToTimestamp(line)
        );
    }

    // -------------------GETTERS AND SETTERS-----------------------
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

   
    // -------------------VALIDATION-----------------------
    public static String validateZone(String zone) throws IllegalArgumentException{
        compare(zoneList, zone, "Invalid zone: " + zone);
        return zone;
    }

    public static String validateSensorType(String sensor) throws IllegalArgumentException {
        compare(sensorTypeList, sensor, "Invalid sensor type: " + sensor);
        return sensor;
    }

    public static String validateSensorID(String sensorID) throws IllegalArgumentException {
        String prefix = sensorID.substring(0,3);
        if (Character.isLetter(sensorID.charAt(3))) prefix += sensorID.charAt(3);
        compare(sensorPrefixes, prefix, "Invalid sensor ID: " + sensorID);
        return sensorID;
    }

    private static void compare(String[] arr, String txt, String errMsg) throws IllegalArgumentException {
        boolean isValid = false;
        for (String x : arr) {
            if (x.equals(txt)) {
                isValid = true;
            }
        }
        if (!isValid) throw new IllegalArgumentException(errMsg);
    }
}