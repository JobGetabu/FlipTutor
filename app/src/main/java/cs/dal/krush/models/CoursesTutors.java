package cs.dal.krush.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Courses-Tutors pivot table Model class.
 */
public class CoursesTutors {
    SQLiteDatabase dbWrite;
    SQLiteDatabase dbRead;

    public CoursesTutors(SQLiteDatabase dbWrite, SQLiteDatabase dbRead){
        this.dbWrite = dbWrite;
        this.dbRead = dbRead;
    }

    /**
     * insert into this table using the tutor_id and course_id fields
     * @param tutor_id
     * @param course_id
     * @return boolean
     */
    public boolean insertCoursesTutors(int tutor_id, int course_id){
        ContentValues contentValues = new ContentValues();
        contentValues.put("tutor_id", tutor_id);
        contentValues.put("course_id", course_id);
        dbWrite.insert("course_tutors", null, contentValues);
        return true;
    }

    /**
     * Get courses from a specific tutor
     * @param tutor_id
     * @return Cursor
     */
    public Cursor getCoursesFromTutor(int tutor_id){
        return dbRead.rawQuery(
                "SELECT c.course_code, c.id " +
                        "FROM course_tutors ct " +
                        "INNER JOIN courses c ON c.id = ct.course_id " +
                        "WHERE ct.tutor_id="+tutor_id+""
                ,null
        );
    }

    /**
     * Give a tutor id, returns an ArrayList of strings of the tutors course names
     * @param tutor_id
     * @return ArrayList<String>
     */
    public ArrayList<String> getCourseNamesFromTutor(int tutor_id){
        ArrayList<String> courseNames = null;
        Cursor res = dbRead.rawQuery(
                "SELECT c.title, c.course_code " +
                        "FROM courses c " +
                        "INNER JOIN course_tutors ct on ct.course_id = c.id " +
                        "INNER JOIN tutors t on t.id = ct.tutor_id " +
                        "WHERE t.id="+tutor_id, null);
        if(res.moveToFirst()) {
            courseNames = new ArrayList<>();
            do {
                courseNames.add(res.getString(res.getColumnIndex("course_code")) + " - "
                        + res.getString(res.getColumnIndex("title")));
            } while(res.moveToNext());
        }
        res.close();
        return courseNames;
    }

    /**
     * Get tutors from a specific course
     * @param course_id
     * @return Cursor
     */
    public Cursor getTutorsFromCourse(int course_id){
        return dbRead.rawQuery(
                "SELECT t.f_name, t.l_name, t.id " +
                        "FROM course_tutors ct " +
                        "INNER JOIN tutors t ON t.id = ct.tutor_id " +
                        "WHERE ct.course_id="+course_id+""
                ,null
        );
    }
}
