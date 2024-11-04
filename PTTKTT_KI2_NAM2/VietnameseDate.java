
/******************************************************************************
 *  Compilation:  javac VietnameseDate.java
 *  Execution:    java VietnameseDate
 *  Dependencies: StdOut.java
 *
 *  An immutable data type for dates.
 *
 ******************************************************************************/

 


public class VietnameseDate implements Comparable<VietnameseDate> {
    private static final int[] DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private final int day;     // day   (between 1 and DAYS[month]
    private final int month;   // month (between 1 and 12)
    private final int year;    // year

   /**
     * Initializes a new date from the month, day, and year.
     * @param month the month (between 1 and 12)
     * @param day the day (between 1 and 28-31, depending on the month)
     * @param year the year
     * @throws IllegalArgumentException if this date is invalid
     */
    public VietnameseDate(int day, int month, int year) {
        if (!isValid(day, month, year)) throw new IllegalArgumentException("Invalid date");
        this.day   = day;
        this.month = month;
        this.year  = year;
    }

    /**
     * Initializes new date specified as a string in form MM/DD/YYYY.
     * @param date the string representation of this date
     * @throws IllegalArgumentException if this date is invalid
     */
    public VietnameseDate(String date) {
        String[] fields = date.split("/");
        if (fields.length != 3) {
            fields = date.split("-");
        }
        
        if (fields.length != 3) {
            String[] test = date.split("\\s+");
            if (test.length == 6) {
                fields = new String[3];
                fields[0] = test[1];
                fields[1] = test[3];
                fields[2] = test[5];
            }
        }
        
        if (fields.length != 3) {
            throw new IllegalArgumentException("Invalid date");
        }
        day = Integer.parseInt(fields[0]);
        month = Integer.parseInt(fields[1]);
        year  = Integer.parseInt(fields[2]);
        if (!isValid(day, month, year)) throw new IllegalArgumentException("Invalid date");
    }

    /**
     * Return the month.
     * @return the month (an integer between 1 and 12)
     */
    public int month() {
        return month;
    }

    /**
     * Returns the day.
     * @return the day (an integer between 1 and 31)
     */
    public int day() {
        return day;
    }

    /**
     * Returns the year.
     * @return the year
     */
    public int year() {
        return year;
    }


    // is the given date valid?
    private static boolean isValid(int d, int m, int y) {
        if (m < 1 || m > 12)      return false;
        if (d < 1 || d > DAYS[m]) return false;
        if (m == 2 && d == 29 && !isLeapYear(y)) return false;
        return true;
    }

    // is y a leap year?
    private static boolean isLeapYear(int y) {
        if (y % 400 == 0) return true;
        if (y % 100 == 0) return false;
        return y % 4 == 0;
    }

    /**
     * Returns the next date in the calendar.
     *
     * @return a date that represents the next day after this day
     */
    public VietnameseDate next() {
        if (isValid(day + 1, month, year))    return new VietnameseDate(day + 1, month, year);
        else if (isValid(1, month + 1, year)) return new VietnameseDate(1, month + 1, year);
        else                                  return new VietnameseDate(1, 1, year + 1);
    }

    /**
     * Compares two dates chronologically.
     *
     * @param  that the other date
     * @return {@code true} if this date is after that date; {@code false} otherwise
     */
    public boolean isAfter(VietnameseDate that) {
        return compareTo(that) > 0;
    }

    /**
     * Compares two dates chronologically.
     *
     * @param  that the other date
     * @return {@code true} if this date is before that date; {@code false} otherwise
     */
    public boolean isBefore(VietnameseDate that) {
        return compareTo(that) < 0;
    }

    /**
     * Compares two dates chronologically.
     *
     * @return the value {@code 0} if the argument date is equal to this date;
     *         a negative integer if this date is chronologically less than
     *         the argument date; and a positive ineger if this date is chronologically
     *         after the argument date
     */
    @Override
    public int compareTo(VietnameseDate that) {
        if (this.year  < that.year)  return -1;
        if (this.year  > that.year)  return +1;
        if (this.month < that.month) return -1;
        if (this.month > that.month) return +1;
        if (this.day   < that.day)   return -1;
        if (this.day   > that.day)   return +1;
        return 0;
    }

    /**
     * Returns a string representation of this date.
     *
     * @return the string representation in the format MM/DD/YYYY
     */
    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }

    /**
     * Compares this date to the specified date.
     *
     * @param  other the other date
     * @return {@code true} if this date equals {@code other}; {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        VietnameseDate that = (VietnameseDate) other;
        return (this.day == that.day) && (this.month == that.month) && (this.year == that.year);
    }

    /**
     * Returns an integer hash code for this date.
     *
     * @return an integer hash code for this date
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31*hash + day;
        hash = 31*hash + month;
        hash = 31*hash + year;
        return hash;
    }

    /**
     * Unit tests the {@code VietnameseDate} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        VietnameseDate test = new VietnameseDate("4/8/1955");
        StdOut.println(test);
        
        test = new VietnameseDate("4-8-1955");
        StdOut.println(test);

        test = new VietnameseDate("ngay 4 thang 8 nam 1955");
        StdOut.println(test);
    }

}
