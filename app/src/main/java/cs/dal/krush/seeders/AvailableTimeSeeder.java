package cs.dal.krush.seeders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import cs.dal.krush.models.DBHelper;

public class AvailableTimeSeeder {

    public static void insert(DBHelper db) {
        DateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        GregorianCalendar calendarStartTime = new GregorianCalendar();


        calendarStartTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 13);
        calendarStartTime.set(Calendar.MONTH, 9);
        calendarStartTime.set(Calendar.YEAR, 2019);
        calendarStartTime.set(Calendar.MINUTE, 0);
        calendarStartTime.set(Calendar.SECOND, 0);


        GregorianCalendar calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 14);
        calendarEndTime.set(Calendar.MONTH, 9);
        calendarEndTime.set(Calendar.YEAR, 2019);
        calendarEndTime.set(Calendar.MINUTE, 0);
        calendarStartTime.set(Calendar.SECOND, 0);

        String startTime = timeFormatter.format(calendarStartTime.getTime());
        String endTime = timeFormatter.format(calendarEndTime.getTime());

        db.availableTime.insert(startTime,endTime,1);

        calendarStartTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 13);
        calendarStartTime.set(Calendar.MONTH, 9);
        calendarStartTime.set(Calendar.YEAR, 2019);
        calendarStartTime.set(Calendar.MINUTE, 0);
        calendarStartTime.set(Calendar.SECOND, 0);

        calendarEndTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 14);
        calendarEndTime.set(Calendar.MONTH, 9);
        calendarEndTime.set(Calendar.YEAR, 2019);
        calendarEndTime.set(Calendar.MINUTE, 0);
        calendarStartTime.set(Calendar.SECOND, 0);

        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());

        db.availableTime.insert(startTime,endTime,1);



        calendarStartTime.set(Calendar.DAY_OF_MONTH, 27);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 14);
        calendarStartTime.set(Calendar.MONTH, 9);
        calendarStartTime.set(Calendar.YEAR, 2019);
        calendarStartTime.set(Calendar.MINUTE, 0);
        calendarStartTime.set(Calendar.SECOND, 0);

        calendarEndTime.set(Calendar.DAY_OF_MONTH, 27);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 16);
        calendarEndTime.set(Calendar.MONTH, 9);
        calendarEndTime.set(Calendar.YEAR, 2019);
        calendarEndTime.set(Calendar.MINUTE, 0);
        calendarStartTime.set(Calendar.SECOND, 0);

        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());

        db.availableTime.insert(startTime,endTime,1);



        calendarStartTime.set(Calendar.DAY_OF_MONTH, 28);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 14);
        calendarStartTime.set(Calendar.MONTH, 9);
        calendarStartTime.set(Calendar.YEAR, 2019);
        calendarStartTime.set(Calendar.MINUTE, 0);
        calendarStartTime.set(Calendar.SECOND, 0);

        calendarEndTime.set(Calendar.DAY_OF_MONTH, 28);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 16);
        calendarEndTime.set(Calendar.MONTH, 9);
        calendarEndTime.set(Calendar.YEAR, 2019);
        calendarEndTime.set(Calendar.MINUTE, 30);
        calendarStartTime.set(Calendar.SECOND, 0);

        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());

        db.availableTime.insert(startTime,endTime,1);
    }
}
