package datatypes;

public class Timestamp {
    private int dayOfMonth = 1;
    private int monthOfYear = 1;
    private int year = 0;
    private int hour = 0;
    private int minute = 0;

    public Timestamp (int dayOfMonth, int monthOfYear, int year, int hour, int minute) {
        if (dayOfMonth < 1 || dayOfMonth > 31) throw new IllegalArgumentException("Day must be within 1 to 31: " + dayOfMonth);
        if (monthOfYear < 1 || monthOfYear > 12) throw new IllegalArgumentException("Month must be within 1 to 12: " + monthOfYear);
        if (dayOfMonth > getDaysInMonth(monthOfYear)) throw new IllegalArgumentException(intToStrMonth(monthOfYear)+ " can only have " + getDaysInMonth(monthOfYear) + " days");

        if (year < 0 || year > 2026) throw new IllegalArgumentException("Year must be within 0 to 2026: " + year);
        if (hour < 0 || hour > 23) throw new IllegalArgumentException("Hour must be within 0 to 23: " + hour);
        if (minute < 0 || minute > 59) throw new IllegalArgumentException("Minute must be within 0 to 59: " + minute);
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear; 
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public Timestamp(Timestamp timestamp) {
        dayOfMonth = timestamp.getDay();
        monthOfYear = timestamp.getMonth();
        year = timestamp.getYear();
        hour = timestamp.getHour();
        minute = timestamp.getMinute();
    }

    public Timestamp() {}

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
        if (day > getDaysInMonth(monthOfYear)) throw new IllegalArgumentException(intToStrMonth(monthOfYear)+ " can only have " + getDaysInMonth(monthOfYear) + " days");
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
        if (hour < 0 || hour > 23) throw new IllegalArgumentException("Hour must be within 0 to 23: " + hour);
        this.hour = hour;
    }
    public void setMinute(int minute) {
        if (minute < 0 || minute > 59) throw new IllegalArgumentException("Minute must be within 0 to 59: " + minute);
        this.minute = minute;
    }

    private int getDaysInMonth(int month) {
        return switch(month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> 28;
            default -> 0;
        };
    }

    // ------------- validation helpers -------------------
    private String intToStrMonth(int month) throws IllegalArgumentException {
        return switch(month) {
            case 1 -> "january";
            case 2 -> "february";
            case 3 -> "march";
            case 4 -> "april";
            case 5 -> "may";
            case 6 -> "june";
            case 7 -> "july";
            case 8 -> "august";
            case 9 -> "september";
            case 10 -> "october";
            case 11 -> "november";
            case 12 -> "december";
            default -> throw new IllegalArgumentException("This month doesn't exist");
        };
    }

}