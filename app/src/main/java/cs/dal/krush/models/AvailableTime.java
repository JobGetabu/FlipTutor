package cs.dal.krush.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Available Time Model class.
 */
public class AvailableTime extends Table{

    private String day;
    private String month;
    private String year;
    private String startTime;
    private String endTime;

    public AvailableTime(SQLiteDatabase dbWrite, SQLiteDatabase dbRead){
        super(dbWrite,dbRead);
    }

    /**
     * Insert a record into the available_time table
     * @param startTime
     * @param endTime
     * @return
     */
    public boolean insert(String startTime, String endTime, int tutor_id){
        ContentValues contentValues = new ContentValues();
        contentValues.put("tutor_id", tutor_id);
        contentValues.put("start_time", startTime);
        contentValues.put("end_time", endTime);
        contentValues.put("booked", 0);
        dbWrite.insert("available_time", null, contentValues);
        return true;
    }

    /**
     * Gets all available times for given tutor
     * @param tutorId
     * @return
     */
    public Cursor getDataByTutorId(int tutorId) {
        return dbRead.rawQuery("SELECT * FROM available_time WHERE tutor_id="+tutorId, null);
    }

    /**
     * Returns upcoming available times that are not already booked
     * @param tutorId
     * @return
     */
    public Cursor getUpcomingDataByTutorId(int tutorId){
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = "\"" + simple.format(new Date()) + "\"";
        return dbRead.rawQuery("SELECT * FROM available_time " +
                "WHERE tutor_id="+tutorId
                + " AND start_time > "+now
                + " AND booked=0", null);
    }


    @Override
    public Cursor getData(int id) {
        res = dbRead.rawQuery("SELECT * FROM available_time WHERE id="+id+"",null);
        return res;
    }

    @Override
    public Cursor getAll() {
        res = dbRead.rawQuery("SELECT * FROM available_time",null);
        return res;
    }

    /**
     * Get the available times for a tutor from the given dates
     * @param year as String
     * @param month of the year
     * @param day of the month
     * @return Cursor of start time
     */
    public Cursor getByDay(String year, String month, String day){
        //get all records with the same day, month and year to display the times.
        res = dbRead.rawQuery("SELECT strftime('%H:%M',start_time) as start, " +
                "strftime('%H:%M',end_time) as end, " +
                "strftime('%Y', start_time) as year, " +
                "strftime('%m', start_time) as month, " +
                "strftime('%d', start_time) as day " +
                "FROM available_time " +
                "WHERE year='"+year+"' " +
                "AND month='"+month+"' " +
                "AND day='"+day+"'",null);
        return res;
    }

    /**
     * Get all available times from the specified tutor, ordered by day
     * @param tutor_id of times to retrieve
     * @return Cursor of ordered available dates
     */
    public Cursor getAllOrderedByDay(int tutor_id){
        res = dbRead.rawQuery("SELECT id,tutor_id,start_time, end_time FROM available_time " +
                "WHERE start_time > datetime('now','-2 day') " +
                "AND tutor_id="+tutor_id+" " +
                "ORDER BY start_time",null);
        return res;
    }

    /**
     * Delete a record from the database. This method first finds the id
     * of the record, then deletes the id.
     * @param date of record to delete
     * @param tutor_id of associated tutor to retrieve id
     */
    public void deleteRecord(String date, int tutor_id){
        res = dbRead.rawQuery("SELECT id, start_time FROM available_time " +
                "WHERE start_time='"+date+"' " +
                "AND tutor_id="+tutor_id+"",null);

        Integer id = null;
        res.moveToFirst();
        id = res.getInt(res.getColumnIndex("id"));

        dbWrite.execSQL("DELETE FROM available_time " +
                "WHERE id="+id+"");
    }

}
