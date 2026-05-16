package datatypes;

public class Timestamp {
    private int dayOfMonth;
    private int monthOfYear;
    private int year;
    private int hour;
    private int minute;

    public Timestamp (int dayOfMonth, int monthOfYear, int year, int hour, int minute) {
        if (dayOfMonth < 1 || dayOfMonth > 31) throw new IllegalArgumentException("Day must be within 1 to 31: " + dayOfMonth);
        if (monthOfYear < 1 || monthOfYear > 12) throw new IllegalArgumentException("Month must be within 1 to 12: " + monthOfYear);
        if (year < 0 || year > 2026) throw new IllegalArgumentException("Year must be within 0 to 2026: " + year);
        if (hour < 0 || hour > 23) throw new IllegalArgumentException("Year must be within 0 to 23: " + hour);
        if (minute < 0 || minute > 59) throw new IllegalArgumentException("Minute must be within 0 to 59: " + minute);
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
        if (day < 1 || day > 31) throw new IllegalArgumentException("Day must be within 1 to 31: " + day);
        dayOfMonth = day;
    }
    public void setMonth(int month) {
        if (month < 1 || month > 12) throw new IllegalArgumentException("Month must be within 1 to 12: " + month);
        monthOfYear = month;
    }
    public void setYear(int year) {
        if (year < 0 || year > 2026) throw new IllegalArgumentException("Year must be within 0 to 2026: " + year);
        this.year = year;
    }
    public void setHour(int hour) {
        if (hour < 0 || hour > 23) throw new IllegalArgumentException("Year must be within 0 to 23: " + hour);
        this.hour = hour;
    }
    public void setMinute(int minute) {
        if (minute < 0 || minute > 59) throw new IllegalArgumentException("Minute must be within 0 to 59: " + minute);
        this.minute = minute;
    }

}