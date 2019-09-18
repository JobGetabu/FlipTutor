package cs.dal.krush.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Tutor rating model
 */

public class TutorRating extends Table {

    public TutorRating(SQLiteDatabase dbWrite, SQLiteDatabase dbRead) { super(dbWrite,dbRead); }

    /**
     * Adds a new rating of a tutor to the tutor_ratings table
     * @param rating
     * @param studentId
     * @param tutorId
     * @return
     */
    public boolean insert(float rating, int studentId, int tutorId){
        ContentValues contentValues = new ContentValues();
        contentValues.put("rating", rating);
        contentValues.put("student_id", studentId);
        contentValues.put("tutor_id", tutorId);
        dbWrite.insert("tutor_ratings", null, contentValues);
        return true;
    }

    /**
     * Updates the rating a student has previously given
     * @param rating
     * @param studentId
     * @param tutorId
     * @return
     */
    public boolean updateTutorRating(float rating, int studentId, int tutorId){

        ContentValues contentValues = new ContentValues();
        contentValues.put("rating", rating);
        dbWrite.update("tutor_ratings", contentValues, "tutor_id=? AND student_id=?", new String[] { "" + tutorId, "" + studentId});
        return true;
    }
    @Override
    public Cursor getData(int id) {
        return dbRead.rawQuery("SELECT * FROM tutor_ratings WHERE id="+id+"",null);
    }

    @Override
    public Cursor getAll() { return dbRead.rawQuery("SELECT * FROM tutor_ratings",null); }

    /**
     * Gets all ratings records for given tutor
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * @return Cursor
     */
    public Cursor getTutorRatingByTutorId(int tutorId){
        return dbRead.rawQuery(
                "SELECT id AS _id, tutor_id, student_id, rating " +
                "FROM tutor_ratings " +
                "WHERE tutor_id=" + tutorId
                ,null
        );
    }

    /**
     * Gets the rating of a tutor given by the student
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * Source:
     * [7] Android column '_id' does not exist? (n.d.). Retrieved March 12, 2017,
     * from http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
     *
     * @param tutorId
     * @param studentId
     * @return Cursor
     */
    public Cursor getTutorRatingByTutorAndStudentId(int tutorId, int studentId){
        return dbRead.rawQuery(
                "SELECT id AS _id, tutor_id, student_id, rating " +
                "FROM tutor_ratings " +
                "WHERE tutor_id=" + tutorId +
                " AND student_id=" + studentId
                ,null
        );
    }




}
