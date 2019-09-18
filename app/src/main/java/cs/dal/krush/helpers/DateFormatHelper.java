package cs.dal.krush.helpers;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * This class contains functions for converting dates into more readable formats
 */
public class DateFormatHelper
{
    /**
     * Get the name of a month from integer
     * @param month to convert to String
     * @param locale default locale
     * @return formatted month name
     */
    public static String formatMonth(int month, Locale locale) {
        DateFormat formatter = new SimpleDateFormat("MMMM", locale);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, month);
        return formatter.format(calendar.getTime());
    }

    /**
     * Get the name of a day from integer
     * @param day to convert to String
     * @param locale default locale
     * @return formatted day name
     */
    public static String getFullDayName(int day, Locale locale) {

        DateFormat formatter = new SimpleDateFormat("EEEE", locale);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return formatter.format(calendar.getTime());
    }

    /**
     * Takes a full dateTime string yyyy-mm-dd HH:MM:SS and converts it
     * to a more readable format, such as March 20, Monday: 2:00 PM
     * Year and seconds are disregarded
     * @param fullDate
     * @return
     * @throws ParseException
     */
    public static String getStartDate(String fullDate) throws ParseException
    {
        String result;

        // Split the dateTime string into date and time
        String[] fullDateSplit = fullDate.split("\\s");
        String date = fullDateSplit[0];
        String time = fullDateSplit[1];

        // Further split date into year-month-day
        String[] dateSplit = date.split("-");
        String month = dateSplit[1];
        String day = dateSplit[2];

        // Get actual names for month and day
        String monthName = formatMonth(Integer.parseInt(month), Locale.getDefault());
        String dayName = getFullDayName(Integer.parseInt(day), Locale.getDefault());

        // Drop seconds from the time
        time = time.substring(0, time.lastIndexOf(":"));

        // Convert to 12 hour
        time = convertTo12Hour(time);

        result = monthName + " " + day + ", " + dayName + ": " + time;
        return result;
    }

    /**
     * Takes a full date and just returns the hours and minutes since
     * a tutor session can be assumed to end on the same date and it starts on
     * @param fullDate
     * @return
     * @throws ParseException
     */
    public static String getTimeFromDate(String fullDate) throws ParseException
    {

        // Get time from dateTime string
        String time = fullDate.split("\\s")[1];

        // Drop seconds from the time
        time = time.substring(0, time.lastIndexOf(":"));

        // Convert to 12 hour
        time = convertTo12Hour(time);

        return time;

    }

    /**
     * Takes a 24 hour format of "H:mm" i.e. "14:45" and converts to 12 hour with AM/PM
     * @param time
     * @return
     * @throws ParseException
     */
    public static String convertTo12Hour(String time) throws ParseException
    {

        SimpleDateFormat inputFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
        Date inputDate = inputFormat.parse(time);

        SimpleDateFormat outputFormat = new SimpleDateFormat("h:mm aa", Locale.getDefault());
        return outputFormat.format(inputDate);

    }

    /**
     * Formatter to match Java date with sqlite, this makes
     * it much easier to query dates in sqlite.
     * @param date selected date
     * @param time specific time of day
     * @return
     */
    public String formatForSqlite(String date, String time){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        // split and parse
        String[] splitDate = date.split("[-]");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);

        // get the hour and minute of day
        String[] splitTime = time.split("\\s");
        String[] splitStartTime = splitTime[0].split("[:]");
        int hourOfDay = Integer.parseInt(splitStartTime[0]);
        int minute = Integer.parseInt(splitStartTime[1]);

        // convert to calendar, then format
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        return formatter.format(calendar.getTime());
    }

}
