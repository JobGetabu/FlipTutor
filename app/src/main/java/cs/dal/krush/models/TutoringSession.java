package cs.dal.krush.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Tutoring Sessions model class.
 */
public class TutoringSession extends Table{

    public TutoringSession(SQLiteDatabase dbWrite, SQLiteDatabase dbRead){
        super(dbWrite,dbRead);
    }

    /**
     * Insert row into tutoring_sessions
     * @param studentId
     * @param tutorId
     * @param locationId
     * @param title
     * @param sessionBooked
     * @return boolean
     */

    public boolean insert(int studentId, int tutorId, int locationId, int sessionBooked, String title, String startTime, String endTime){
        ContentValues contentValues = new ContentValues();
        contentValues.put("student_id", studentId);
        contentValues.put("tutor_id", tutorId);
        contentValues.put("location_id", locationId);
        contentValues.put("title", title);
        contentValues.put("session_booked", sessionBooked);
        contentValues.put("start_time", startTime);
        contentValues.put("end_time", endTime);
        dbWrite.insert("tutoring_sessions", null, contentValues);
        return true;
    }

    @Override
    public Cursor getData(int id){
        return dbRead.rawQuery("SELECT * FROM tutoring_sessions WHERE id="+id+"", null);
    }

    @Override
    public Cursor getAll() {
        return dbRead.rawQuery("SELECT * FROM tutoring_sessions", null);
    }

    /**
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     * Gets all tutoring sessions.
     * @return Cursor
     */
    public Cursor getAllForCursorAdapter(){
        return dbRead.rawQuery("SELECT id as _id, student_id, tutor_id, location_id, title FROM tutoring_sessions", null);
    }

    /**
     * Get a tutoring session by the title
     * @param title
     * @return Cursor
     */
    public Cursor getDataByTitle(String title){
        return dbRead.rawQuery("SELECT * FROM tutoring_sessions WHERE title="+title+"",null);
    }

    /**
     * Get a tutoring session by the location_id field
     * @param locationId
     * @return Cursor
     */
    public Cursor getDataByLocationId(int locationId){
        return dbRead.rawQuery("SELECT * FROM tutoring_sessions WHERE location_id="+locationId+"",null);
    }

    /**
     * Get a tutoring session by the student_id field
     * @param studentId
     * @return Cursor
     */
    public Cursor getDataByStudentId(int studentId){
        return dbRead.rawQuery("SELECT * FROM tutoring_sessions WHERE student_id="+studentId+"",null);
    }

    /**
     * Gets all upcoming tutoring sessions by the given student.
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * Source:
     * [7] Android column '_id' does not exist? (n.d.). Retrieved March 12, 2017,
     * from http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
     *
     * @param studentId
     * @return Cursor
     */
    public Cursor getDataByStudentIdForCursorAdapter(int studentId){
        return dbRead.rawQuery(
                "SELECT t.id AS _id, t.location_id, t.school_id, t.profile_pic, t.f_name, t.l_name, " +
                "t.email, t.password, t.rating, t.rate, t.revenue, " +
                "ts.student_id, ts.title, ts.id, ts.start_time, ts.end_time, ts.location_id, " +
                "l.location " +
                "FROM tutors t " +
                "INNER JOIN tutoring_sessions ts ON _id = ts.tutor_id " +
                "INNER JOIN locations l ON ts.location_id = l.id " +
                "WHERE ts.student_id=" + studentId +
                " AND ts.end_time>=date('now')"
                ,null
        );
    }

    /**
     * Gets all upcoming tutoring sessions by the given tutor.
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * Source:
     * [7] Android column '_id' does not exist? (n.d.). Retrieved March 12, 2017,
     * from http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
     *
     * @param tutorId
     * @return Cursor
     */
    public Cursor getDataByTutorIdForCursorAdapter(int tutorId){
        return dbRead.rawQuery(
                "SELECT s.id AS _id, s.school_id, s.profile_pic, s.f_name, s.l_name, s.email, " +
                        "ts.title, ts.id, ts.start_time, ts.end_time, ts.location_id, " +
                        "l.location, " +
                        "sl.name " +
                        "FROM students s " +
                        "INNER JOIN tutoring_sessions ts ON _id = ts.student_id " +
                        "INNER JOIN locations l ON ts.location_id = l.id " +
                        "INNER JOIN schools sl ON s.school_id = sl.id " +
                        "WHERE ts.tutor_id=" + tutorId +
                        " AND ts.session_booked=1" +
                        " AND ts.end_time>=date('now')"
                ,null
        );
    }
    /**
     * Gets all past tutoring sessions by the given student.
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * Source:
     * [7] Android column '_id' does not exist? (n.d.). Retrieved March 12, 2017,
     * from http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
     *
     * @param studentId
     * @return Cursor
     */
    public Cursor getSessionHistoryByStudentIdForCursorAdapter(int studentId){
        return dbRead.rawQuery(
                "SELECT t.id AS _id, t.school_id, t.profile_pic, " +
                "t.rating, t.rate, t.f_name, t.l_name, t.email, " +
                "ts.title, ts.id, ts.start_time, ts.end_time, ts.location_id, " +
                "l.location, " +
                "sl.name " +
                "FROM tutors t " +
                "INNER JOIN tutoring_sessions ts ON _id = ts.tutor_id " +
                "INNER JOIN locations l ON ts.location_id = l.id " +
                "INNER JOIN schools sl ON t.school_id = sl.id " +
                "WHERE ts.student_id=" + studentId +
                " AND ts.session_booked=1" +
                " AND ts.end_time<date('now')"
                ,null
        );
    }

    /**
     * Gets all past tutoring sessions by the given tutor.
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * Source:
     * [7] Android column '_id' does not exist? (n.d.). Retrieved March 12, 2017,
     * from http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
     *
     * @param tutorId
     * @return Cursor
     */
    public Cursor getSessionHistoryByTutorIdForCursorAdapter(int tutorId){
        return dbRead.rawQuery(
                "SELECT s.id AS _id, s.school_id, s.profile_pic, s.f_name, s.l_name, s.email, " +
                "ts.title, ts.id, ts.start_time, ts.end_time, ts.location_id, " +
                "l.location, " +
                "sl.name " +
                "FROM students s " +
                "INNER JOIN tutoring_sessions ts ON _id = ts.student_id " +
                "INNER JOIN locations l ON ts.location_id = l.id " +
                "INNER JOIN schools sl ON s.school_id = sl.id " +
                "WHERE ts.tutor_id=" + tutorId +
                " AND ts.session_booked=1" +
                " AND ts.end_time<date('now')"
                ,null
        );
    }

    /**
     * Gets the details of a specific (past) session.
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * Source:
     * [7] Android column '_id' does not exist? (n.d.). Retrieved March 12, 2017,
     * from http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
     *
     * @param sessionId
     * @return Cursor
     */
    public Cursor getSessionHistoryDetailsBySessionIdForCursorAdapter(int sessionId){
        return dbRead.rawQuery(
                "SELECT s.id AS _id, s.school_id, s.profile_pic, s.f_name, s.l_name, s.email, " +
                        "ts.title, ts.id, ts.start_time, ts.end_time, ts.location_id, " +
                        "l.location, " +
                        "sl.name, sl.type " +
                        "FROM students s " +
                        "INNER JOIN tutoring_sessions ts ON _id = ts.student_id " +
                        "INNER JOIN locations l ON ts.location_id = l.id " +
                        "INNER JOIN schools sl ON s.school_id = sl.id " +
                        "WHERE ts.id=" + sessionId + ""
                ,null
        );
    }

    /**
     * Gets the details of a specific (past) session for tutors.
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * Source:
     * [7] Android column '_id' does not exist? (n.d.). Retrieved March 12, 2017,
     * from http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
     *
     * @param sessionId
     * @return Cursor
     */
    public Cursor getSessionHistoryDetailsBySessionIdForTutorCursorAdapter(int sessionId){
        return dbRead.rawQuery(
                "SELECT t.id AS _id, t.school_id, t.profile_pic, t.f_name, t.l_name, t.email, " +
                        "ts.title, ts.id, ts.start_time, ts.end_time, ts.location_id, " +
                        "l.location, " +
                        "sl.name, sl.type " +
                        "FROM tutors t " +
                        "INNER JOIN tutoring_sessions ts ON _id = ts.tutor_id " +
                        "INNER JOIN locations l ON ts.location_id = l.id " +
                        "INNER JOIN schools sl ON t.school_id = sl.id " +
                        "WHERE ts.id=" + sessionId + ""
                ,null
        );
    }

    /**
     * Get a tutoring session by the tutor_id field
     * @param tutorId
     * @return Cursor
     */
    public Cursor getDataByTutorId(int tutorId){
        return dbRead.rawQuery("SELECT * FROM tutoring_sessions WHERE tutor_id="+tutorId+"",null);
    }

    /**
     * Get tutoring sessions for the current month
     * @param tutorId id for specified tutor
     * @return
     */
    public Cursor getDataBySchedule(int tutorId){
        return dbRead.rawQuery("SELECT * FROM tutoring_sessions " +
                "WHERE strftime('%m',start_time) = '03' " +
                "AND strftime('%m',end_time) = '03' " +
                "AND tutor_id = "+tutorId+"",null);
    }

    /**
     * Given a userId, it returns the last session in the db
     * Used after a student has just booked a session and
     * need the session id to pass to the session details fragment
     * @param userId
     * @return
     */
    public int getLastBookedSessionIdByUserId(int userId) {
        int session_id;
        Cursor res = dbRead.rawQuery("SELECT id " +
                "FROM tutoring_sessions " +
                "WHERE student_id="+userId,null);
        if (res != null){
            res.moveToLast();
            session_id = res.getInt(res.getColumnIndex("id"));
            res.close();
            return session_id;
        }
        else {
            return -1;
        }
    }

    /**
     * Delete tutoring session by id
     * @param id
     */
    public void deleteTutoringSession(int id) {
        dbWrite.delete("tutoring_sessions","id = ?",new String[] { String.valueOf(id) });
    }
}
