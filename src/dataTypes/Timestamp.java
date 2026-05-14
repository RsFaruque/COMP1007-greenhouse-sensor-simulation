package dataTypes;

public class Timestamp {
    int dayOfMonth;
    int monthOfYear;
    int year;
    int hour;
    int minute;

    public Timestamp (int dayOfMonth, int monthOfYear, int year, int hour, int minute) {
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear; 
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public static Timestamp stringToTimestamp(String line) {
        String[] dataList = line.split(",");

        return new Timestamp(
            Integer.parseInt(dataList[0]),
            Integer.parseInt(dataList[1]),
            Integer.parseInt(dataList[2]),
            Integer.parseInt(dataList[3]),
            Integer.parseInt(dataList[4])
        );
    }
    
}