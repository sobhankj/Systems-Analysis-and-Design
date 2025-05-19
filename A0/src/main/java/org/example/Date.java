package org.example;
import java.util.Comparator;
import java.util.Objects;

public class Date implements Comparable<Date> {
    private final int day;
    private final int month;
    private final int year;

    public Date(int day, int month, int year) {
        if (year < 0 || month < 1 || month > 12 || day < 1 || day > daysOfMonth(month, year))
            throw new IllegalArgumentException("Invalid date");

        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    private static int daysOfMonth(int month, int year) {
        if (month < 1 || month > 12)
            throw new IllegalArgumentException("Invalid value for month");
        if (month < 7)
            return 31;
        else if (month < 12)
            return 30;
        else
            return isLeapYear(year) ? 30 : 29;
    }

    private static boolean isLeapYear(int year) {
        int r = year % 33;
        return r == 1 || r == 5 || r == 9 || r == 13 || r == 17 || r == 22 || r == 26 || r == 30;
    }

    public Date nextDay() {
        int d = day;
        int m = month;
        int y = year;
        
        d++;
        if (d > daysOfMonth(m, y)) {
            d = 1;
            m++;
            if (m > 12) {
                m = 1;
                y++;
            }
        }
        return new Date(d, m, y);
    }

    @Override
    public int compareTo(Date other) {
        return Comparator.comparingInt((Date d) -> d.year)
               .thenComparingInt(d -> d.month)
               .thenComparingInt(d -> d.day)
               .compare(this, other);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Date)) return false;
        Date date = (Date) o;
        return Objects.equals(day, date.day) &&
               Objects.equals(month, date.month) &&
               Objects.equals(year, date.year);
    }


    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        return year + "/" + month + "/" + day;
    }
}