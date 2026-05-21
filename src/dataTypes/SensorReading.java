package datatypes;


public class SensorReading {
    private String sensorID = "UNK";
    private String sensorType = "unknown";
    private String zone = "unknown";
    private double value = 0;
    private Timestamp timestamp = new Timestamp();

    
    public static final String[] sensorTypeList = {"temperature", "soilMoisture", "humidity", "light"};
    public static final String[] sensorPrefixes = {"TMP", "SOIL", "HMD", "LGT"};
    public static final String[] zoneList = {"ZoneA", "ZoneB", "ZoneC"};

    public SensorReading (String sensorID, String sensorType, String zone, double value, Timestamp timestamp) throws IllegalArgumentException {
        // check if sensor type is a valid option
        if (
            !(sensorType.equals("temperature")
            || sensorType.equals("humidity")
            || sensorType.equals("soilMoisture")
            || sensorType.equals("light")
        )) {
            throw new IllegalArgumentException("Invalid sensor type: "+sensorType);
        }

        // Check if sensor type and sensor ID match
        this.sensorID = validateSensorID(sensorID);
        if (!IDtoType(sensorID).equals(sensorType))
            throw new IllegalArgumentException("Sensor ID and type do not match. ID=" + sensorID + ", type="+sensorType + ", expected="+IDtoType(sensorID));
        
        this.sensorType = sensorType;
        this.zone = validateZone(zone);
        this.value = value;
        this.timestamp = timestamp;
    }

    public SensorReading(SensorReading sensor) {
        sensorID = sensor.getSensorID();
        sensorType = sensor.getSensorType();
        zone = sensor.getZone();
        value = sensor.getValue();
        timestamp = new Timestamp(sensor.getTimestamp());
    }

    public SensorReading() {}


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

    public String getSensorID() {
        return sensorID;
    }
    public void setSensorID(String id) {
        sensorID = validateSensorID(id);
        sensorType = IDtoType(id);
    }

    public String getZone() {
        return zone;
    }
    public void setZone(String zone) {
        this.zone = validateZone(zone);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp time) {
        timestamp = time;
    }

   
    // -------------------VALIDATION-----------------------
    public static String validateZone(String zone) throws IllegalArgumentException{
        if (
            !(zone.equals("ZoneA")
            || zone.equals("ZoneB")
            || zone.equals("ZoneC")
        )) {
            throw new IllegalArgumentException("Invalid zone: "+zone);
        }
        return zone;
    }

    public static String validateSensorID(String sensorID) throws IllegalArgumentException {
        if (sensorID.length() != 6) {
            throw new IllegalArgumentException("Invalid sensor ID: ID must be 6 characters long. Passed: " + sensorID);
        }

        // Test prefix
        if (IDtoType(sensorID).equals("unknown")) throw new IllegalArgumentException("Invalid ID prefix:"+sensorID); 

        // Test number
        String prefix = sensorID.substring(0,3);
        if (Character.isLetter(sensorID.charAt(3))) prefix += sensorID.charAt(3);
        String suffix = sensorID.substring(prefix.length());

        for (int i = 0; i < suffix.length(); i++) {
            if (!Character.isDigit(suffix.charAt(i)))
                throw new IllegalArgumentException("Invalid sensor ID: ID digits cannot contain letters. Passed: " + sensorID);
        }

        return sensorID;
    }

    public static String IDtoType(String id) {
        String prefix = id.substring(0,3);
        if (Character.isLetter(id.charAt(3))) prefix += id.charAt(3);
        
        return switch(prefix){
            case "TMP" -> "temperature";
            case "HMD" -> "humidity";
            case "LGT" -> "light";
            case "SOIL" -> "soilMoisture";
            default -> "unknown";
        };
    }
}