package dataTypes;

public class Timestamp {
    private int dayOfMonth;
    private int monthOfYear;
    private int year;
    private int hour;
    private int minute;

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

    // GETTERS
    public int getDay(){
        return dayOfMonth;
    }
    public int getMonth(){
        return monthOfYear;
    }
    public int getYear(){
        return year;
    }
    public int getHour(){
        return hour;
    }
    public int getMinute(){
        return minute;
    }
    
    // SETTERS
    public void setDay(int day) {
        dayOfMonth = day;
    }
    public void setMonth(int month) {
        monthOfYear = month;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }

}