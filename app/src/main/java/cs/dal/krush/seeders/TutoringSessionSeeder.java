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
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 13);
        calendarStartTime.set(Calendar.MONTH, 9);
        calendarStartTime.set(Calendar.YEAR, 2019);
        calendarStartTime.set(Calendar.MINUTE, 25);

        GregorianCalendar calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 14);
        calendarEndTime.set(Calendar.MONTH, 9);
        calendarEndTime.set(Calendar.YEAR, 2019);
        calendarEndTime.set(Calendar.MINUTE, 25);

        String startTime = timeFormatter.format(calendarStartTime.getTime());
        String endTime = timeFormatter.format(calendarEndTime.getTime());

        db.tutoringSession.insert(1,1,1,1,"Meeting at Kimathi mess", startTime, endTime);

        GregorianCalendar calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 13);
        calendarStartTime.set(Calendar.MONTH, 9);
        calendarStartTime.set(Calendar.YEAR, 2019);
        calendarStartTime.set(Calendar.MINUTE, 25);

        GregorianCalendar calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 14);
        calendarEndTime.set(Calendar.MONTH, 9);
        calendarEndTime.set(Calendar.YEAR, 2019);
        calendarEndTime.set(Calendar.MINUTE, 25);
        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());
        db.tutoringSession.insert(2,1,1,1,"Meeting at sunrise hostels", startTime, endTime);

        GregorianCalendar calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 13);
        calendarStartTime.set(Calendar.MONTH, 9);
        calendarStartTime.set(Calendar.YEAR, 2019);
        calendarStartTime.set(Calendar.MINUTE, 25);

        GregorianCalendar calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 14);
        calendarEndTime.set(Calendar.MONTH, 9);
        calendarEndTime.set(Calendar.YEAR, 2019);
        calendarEndTime.set(Calendar.MINUTE, 25);
        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());
        db.tutoringSession.insert(1,1,2,1,"Meeting in RC13 resource", startTime, endTime);
        calendarStartTime = new GregorianCalendar();
        GregorianCalendar calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 13);
        calendarStartTime.set(Calendar.MONTH, 9);
        calendarStartTime.set(Calendar.YEAR, 2019);
        calendarStartTime.set(Calendar.MINUTE, 25);

        GregorianCalendar calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 14);
        calendarEndTime.set(Calendar.MONTH, 9);
        calendarEndTime.set(Calendar.YEAR, 2019);
        calendarEndTime.set(Calendar.MINUTE, 25);
        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());
        db.tutoringSession.insert(3,1,1,1,"Meeting at kimathi mess", startTime, endTime);
        db.tutoringSession.insert(2,6,6,1,"Meeting at national library", startTime, endTime);
        db.tutoringSession.insert(2,3,3,1,"Meeting at school of bs", startTime, endTime);
        db.tutoringSession.insert(4,2,1,1,"Meeting at school library", startTime, endTime);
        db.tutoringSession.insert(1,2,1,1,"Meeting at resource centre", startTime, endTime);
        db.tutoringSession.insert(1,3,1,1,"Meeting at sunrise hostels", startTime, endTime);
        db.tutoringSession.insert(1,5,1,1,"Meeting at students centre", startTime, endTime);
        db.tutoringSession.insert(1,6,1,1,"Meeting at Tamaal hostels", startTime, endTime);

        calendarStartTime = new GregorianCalendar();
        GregorianCalendar calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 13);
        calendarStartTime.set(Calendar.MONTH, 9);
        calendarStartTime.set(Calendar.YEAR, 2019);
        calendarStartTime.set(Calendar.MINUTE, 25);

        GregorianCalendar calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 14);
        calendarEndTime.set(Calendar.MONTH, 9);
        calendarEndTime.set(Calendar.YEAR, 2019);
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

        GregorianCalendar calendarStartTime = new GregorianCalendar();
        calendarStartTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarStartTime.set(Calendar.HOUR_OF_DAY, 13);
        calendarStartTime.set(Calendar.MONTH, 9);
        calendarStartTime.set(Calendar.YEAR, 2019);
        calendarStartTime.set(Calendar.MINUTE, 25);

        GregorianCalendar calendarEndTime = new GregorianCalendar();
        calendarEndTime.set(Calendar.DAY_OF_MONTH, 25);
        calendarEndTime.set(Calendar.HOUR_OF_DAY, 14);
        calendarEndTime.set(Calendar.MONTH, 9);
        calendarEndTime.set(Calendar.YEAR, 2019);
        calendarEndTime.set(Calendar.MINUTE, 25);
        startTime = timeFormatter.format(calendarStartTime.getTime());
        endTime = timeFormatter.format(calendarEndTime.getTime());

        db.tutoringSession.insert(1,1,2,1,"Meeting at resource centre", startTime, endTime);
    }
}
