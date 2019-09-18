package cs.dal.krush.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Tutor Model class.
 */
public class Tutor extends Table{

    public Tutor(SQLiteDatabase dbWrite, SQLiteDatabase dbRead){
        super(dbWrite,dbRead);
    }

    /**
     * Insert row into tutors table
     * @param locationId
     * @param schoolId
     * @param profilePic
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param rate
     * @param rating
     * @param ratingCount
     * @return boolean
     */
    public boolean insert(int locationId, int schoolId, String profilePic, String firstName, String lastName, String
                          email, String password, int rate, float rating, int ratingCount){
        ContentValues contentValues = new ContentValues();
        if (locationId != 0) {
            contentValues.put("location_id", locationId);
        }
        contentValues.put("school_id", schoolId);
        contentValues.put("profile_pic", profilePic);
        contentValues.put("f_name", firstName);
        contentValues.put("l_name", lastName);
        contentValues.put("email", email);
        contentValues.put("password", password);
        if (rate != 0) {
            contentValues.put("rate", rate);
        }
        contentValues.put("rating", rating);
        contentValues.put("revenue", 0.00);
        contentValues.put("rating_count", ratingCount);
        dbWrite.insert("tutors", null, contentValues);
        return true;
    }

    /**
     * Updates the rating for a given tutor
     * @param tutorId
     * @param rating
     * @param ratingCount
     * @return
     */
    public boolean updateTutorRating(int tutorId, float rating, int ratingCount){

        ContentValues contentValues = new ContentValues();
        contentValues.put("rating", rating);
        contentValues.put("rating_count", ratingCount);
        dbWrite.update("tutors", contentValues, "id=?", new String[] { "" + tutorId});
        return true;
    }

    /**
     * Updates the location of given tutor
     * @param tutorId
     * @param locationId
     */
    public void updateTutorLocation(int tutorId, int locationId){
        ContentValues contentValues = new ContentValues();
        contentValues.put("location_id", locationId);
        dbWrite.update("tutors", contentValues, "id=?", new String[] { "" + tutorId});
    }

    @Override
    public Cursor getData(int id){
        return dbRead.rawQuery("SELECT * FROM tutors WHERE id="+id+"",null);
    }

    @Override
    public Cursor getAll() {
        return dbRead.rawQuery("SELECT * FROM tutors",null);
    }

    /**
     * Gets all tutors.
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * Source:
     * [7] Android column '_id' does not exist? (n.d.). Retrieved March 12, 2017,
     * from http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
     *
     * @return Cursor
     */
    public Cursor getAllForCursorAdapter() {
        return dbRead.rawQuery("SELECT id as _id, location_id, school_id, profile_pic, " +
                "f_name, l_name, email, password, rating, rate, revenue FROM tutors", null);
    }

    /**
     * Gets all unique tutors that the given student has previously booked sessions with.
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * Source:
     * [7] Android column '_id' does not exist? (n.d.). Retrieved March 12, 2017,
     * from http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
     *
     * @return Cursor
     */
    public Cursor getPreviouslyUsedTutorsForCursorAdapter(int studentId) {
        return dbRead.rawQuery(
                "SELECT DISTINCT t.id as _id, t.location_id, t.school_id, t.profile_pic, " +
                "t.f_name, t.l_name, t.email, t.password, t.rating, t.rate, t.revenue " +
                "FROM tutors t " +
                "INNER JOIN tutoring_sessions ts ON _id = ts.tutor_id " +
                "WHERE ts.student_id=" + studentId + ""
                , null
        );
    }

    /**
     * Gets all tutors filtered by the courses the student has signed up for.
     * This is a query specifically meant for Cursor Adapters (renaming the id column to _id).
     *
     * Source:
     * [7] Android column '_id' does not exist? (n.d.). Retrieved March 12, 2017,
     * from http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
     *
     * @return Cursor
     */
    public Cursor getTutorsFilteredBySchoolForCursorAdapter(int studentId) {
        return dbRead.rawQuery(
                "SELECT DISTINCT t.id as _id, t.location_id, t.school_id, t.profile_pic, " +
                        "t.f_name, t.l_name, t.email, t.password, t.rating, t.rate, t.revenue " +
                        "FROM tutors t " +
                        "INNER JOIN schools sc ON t.school_id = sc.id " +
                        "INNER JOIN students s ON t.school_id = s.school_id " +
                        "WHERE s.id = " + studentId + ""
                , null
        );
    }

    /**
     * Get a tutor by the f_name field
     * @param firstName
     * @return Cursor
     */
    public Cursor getDataByFirstName(String firstName){
        return dbRead.rawQuery("SELECT * FROM tutors WHERE f_name="+firstName+"",null);
    }

    /**
     * Get a tutor by the l_name field
     * @param lastName
     * @return Cursor
     */
    public Cursor getDataByLastName(String lastName){
        return dbRead.rawQuery("SELECT * FROM tutors WHERE l_name="+lastName+"",null);
    }

    /**
     * Get a tutor by the email field
     * @param email
     * @return Cursor
     */
    public Cursor getDataEmail(String email, String password){
        return dbRead.rawQuery("SELECT * FROM tutors WHERE email=? AND password=?", new String[]{email, password});
    }

    /**
     * Get a tutor by the rating field
     * @param rating
     * @return Cursor
     */
    public Cursor getDataByRating(int rating){
        return dbRead.rawQuery("SELECT * FROM tutors WHERE rating="+rating+"",null);
    }

    /**
     * Increment a tutors total revenue
     * @param tutorID
     * @param revenue
     * @return Cursor
     */
    public void incrementTutorRevenue(int tutorID, float revenue){
        dbWrite.execSQL("UPDATE tutors SET revenue = revenue+"+revenue+" WHERE id="+tutorID+"");
    }

    /**
     * Given a tutorID, returns the location_id of tutor
     * @param tutorId
     * @return
     */
    public int getLocationId(int tutorId) {
        int location_id;
        Cursor res = dbRead.rawQuery("SELECT location_id " +
                "FROM tutors " +
                "WHERE id="+tutorId, null);
        if(res != null) {
            res.moveToFirst();
            location_id = res.getInt(res.getColumnIndex("location_id"));
            res.close();
            return location_id;
        }
        else{
            return -1;
        }
    }

    /**
     * Gets the rating count for a given tutor
     * @param tutorID
     * @return
     */
    public int getTutorRatingCount(int tutorID) {
        Cursor res = dbRead.rawQuery("SELECT rating_count FROM tutors WHERE id=" + tutorID, null);
        res.moveToFirst();
        int ratingCount = Integer.parseInt(res.getString(res.getColumnIndex("rating_count")));
        return ratingCount;
    }

    /**
     * Gets the set location of a tutor by their tutorID
     * @param tutorID
     * @return
     */
    public String getLocationName(int tutorID) {
        String location = null;
        Cursor res = dbRead.rawQuery("SELECT l.location " +
                "FROM locations l " +
                "INNER JOIN tutors t on t.location_id = l.id " +
                "WHERE t.id="+tutorID, null);
        if(res.moveToFirst()) {
            location = res.getString(res.getColumnIndex("location"));
        }
        res.close();
        return location;
    }

    /**
     * Given a tutorID, returns a string of the school name associated
     * with this tutors location_id
     * @param tutorId
     * @return
     */
    public String getSchoolName(int tutorId) {
        String school;
        Cursor res = dbRead.rawQuery("SELECT s.name " +
                "FROM schools s " +
                "INNER JOIN tutors t on t.school_id = s.id " +
                "WHERE t.id="+tutorId, null);
        if(res != null) {
            res.moveToFirst();
            school = res.getString(res.getColumnIndex("name"));
            res.close();
            return school;
        }
        else {
            return "School Not Found";
        }

    }

    /**
     * Given a tutorID, returns a string of the school name associated
     * with this tutors location_id
     * @param tutorId
     * @return
     */
    public String getSchoolNameAndType(int tutorId) {
        String school = null;
        Cursor res = dbRead.rawQuery("SELECT s.name, s.type " +
                "FROM schools s " +
                "INNER JOIN tutors t on t.school_id = s.id " +
                "WHERE t.id="+tutorId, null);
        if(res.moveToFirst()) {
            school = res.getString(res.getColumnIndex("name")) + " "
                    + res.getString(res.getColumnIndex("type"));
        }
        res.close();
        return school;


    }

    /**
     * Delete a tutor by id
     * @param id
     * @return int
     */
    public int deleteTutor(int id){
        return dbWrite.delete("tutors","id = ?",new String[] { Integer.toString(id) });
    }
}
