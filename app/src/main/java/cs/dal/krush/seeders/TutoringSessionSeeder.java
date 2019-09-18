package cs.dal.krush.seeders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import cs.dal.krush.models.DBHelper;

/**
 * DB Seeders class for Tutoring Sessions.
 */
public class TutoringSessionSeeder {

    /**
     * Function that inserts dummy tutoring sessions into the DB.
     * Insert format: studentId, tutorId, locationId, title.
     * @param db
     */
    public static void insert(DBHelper db){


        DateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        GregorianCalendar calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 5);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 11);
        calendarStartTime.set(Calendar.MONTH, 2);
        calendarStartTime.set(Calendar.YEAR, 2017);
        calendarStartTime.set(Calendar.MINUTE, 25);

        GregorianCalendar calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 5);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 12);
        calendarEndTime.set(Calendar.MONTH, 2);
        calendarEndTime.set(Calendar.YEAR, 2017);
        calendarEndTime.set(Calendar.MINUTE, 25);

        String startTime = timeFormatter.format(calendarStartTime.getTime());
        String endTime = timeFormatter.format(calendarEndTime.getTime());

        db.tutoringSession.insert(1,1,1,1,"Meeting at Starbucks on Robie St", startTime, endTime);

        calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 1);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 11);
        calendarStartTime.set(Calendar.MONTH, 2);
        calendarStartTime.set(Calendar.YEAR, 2017);
        calendarStartTime.set(Calendar.MINUTE, 25);

        calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 1);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 12);
        calendarEndTime.set(Calendar.MONTH, 2);
        calendarEndTime.set(Calendar.YEAR, 2017);
        calendarEndTime.set(Calendar.MINUTE, 25);
        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());
        db.tutoringSession.insert(2,1,1,1,"Meeting at Dalhousie CS building", startTime, endTime);

        calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 7);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 11);
        calendarStartTime.set(Calendar.MONTH, 2);
        calendarStartTime.set(Calendar.YEAR, 2017);
        calendarStartTime.set(Calendar.MINUTE, 25);

        calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 7);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 12);
        calendarEndTime.set(Calendar.MONTH, 2);
        calendarEndTime.set(Calendar.YEAR, 2017);
        calendarEndTime.set(Calendar.MINUTE, 25);
        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());
        db.tutoringSession.insert(1,1,2,1,"Meeting at Sparkzone", startTime, endTime);
        calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 3);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 11);
        calendarStartTime.set(Calendar.MONTH, 2);
        calendarStartTime.set(Calendar.YEAR, 2017);
        calendarStartTime.set(Calendar.MINUTE, 25);

        calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 3);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 12);
        calendarEndTime.set(Calendar.MONTH, 2);
        calendarEndTime.set(Calendar.YEAR, 2017);
        calendarEndTime.set(Calendar.MINUTE, 25);
        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());
        db.tutoringSession.insert(3,1,1,1,"Meeting at Coburg Coffee", startTime, endTime);
        db.tutoringSession.insert(2,6,6,1,"Meeting at Acadia library", startTime, endTime);
        db.tutoringSession.insert(2,3,3,1,"Meeting at Truro Big Stop", startTime, endTime);
        db.tutoringSession.insert(4,2,1,1,"Meeting at Dal Math Learning Centre", startTime, endTime);
        db.tutoringSession.insert(1,2,1,1,"Meeting at Dal Killam Library", startTime, endTime);
        db.tutoringSession.insert(1,3,1,1,"Meeting at Public Library 4th floor", startTime, endTime);
        db.tutoringSession.insert(1,5,1,1,"Meeting at Dal CS Learning Centre", startTime, endTime);
        db.tutoringSession.insert(1,6,1,1,"Meeting at Dal Student Union Building", startTime, endTime);

        calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 20);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 11);
        calendarStartTime.set(Calendar.MONTH, 5);
        calendarStartTime.set(Calendar.YEAR, 2017);
        calendarStartTime.set(Calendar.MINUTE, 25);

        calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 20);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 12);
        calendarEndTime.set(Calendar.MONTH, 5);
        calendarEndTime.set(Calendar.YEAR, 2017);
        calendarEndTime.set(Calendar.MINUTE, 25);
        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());
        db.tutoringSession.insert(1,1,2,1,"Meeting at Kimathi Mess", startTime, endTime);
        db.tutoringSession.insert(1,6,2,1,"Meeting at Kimathi Library", startTime, endTime);
        db.tutoringSession.insert(1,3,6,1,"Meeting at RC 13", startTime, endTime);
        db.tutoringSession.insert(1,2,6,1,"Meeting at Freedom Hall", startTime, endTime);
        db.tutoringSession.insert(1,3,4,1,"Meeting at Nyeri Library", startTime, endTime);
        db.tutoringSession.insert(1,5,4,1,"Meeting at Nyandarua Hostel", startTime, endTime);
        db.tutoringSession.insert(1,6,3,1,"Meeting at Tamaal Tv Room", startTime, endTime);

        calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 29);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 4);
        calendarStartTime.set(Calendar.MONTH, 2);
        calendarStartTime.set(Calendar.YEAR, 2017);
        calendarStartTime.set(Calendar.MINUTE, 00);

        calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 29);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 4);
        calendarEndTime.set(Calendar.MONTH, 2);
        calendarEndTime.set(Calendar.YEAR, 2017);
        calendarEndTime.set(Calendar.MINUTE, 30);
        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());

        db.tutoringSession.insert(1,1,2,1,"Meeting at Starbucks Coffee", startTime, endTime);
    }
}
