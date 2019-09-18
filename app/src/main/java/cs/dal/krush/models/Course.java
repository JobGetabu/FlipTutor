package cs.dal.krush.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Course Model class.
 */
public class Course extends Table{

    public Course(SQLiteDatabase dbWrite, SQLiteDatabase dbRead){
        super(dbWrite,dbRead);
    }

    /**
     * Insert row into courses
     * @param title
     * @param course_code
     * @return boolean
     */
    public boolean insert(String title, String course_code){
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("course_code", course_code);
        dbWrite.insert("courses", null, contentValues);
        return true;
    }

    @Override
    public Cursor getData(int id){
        return dbRead.rawQuery("SELECT * FROM courses WHERE id="+id+"",null);
    }

    @Override
    public Cursor getAll() {
        return dbRead.rawQuery("SELECT * FROM courses",null);
    }

    /**
     * Get a course by the title name
     * @param title
     * @return Cursor
     */
    public Cursor getDataByTitle(String title){
        return dbRead.rawQuery("SELECT * FROM courses WHERE title="+title+"",null);
    }

    /**
     * Get a course by the course code
     * @param courseCode
     * @return Cursor
     */
    public Cursor getDataByCourseCode(String courseCode){
        return dbRead.rawQuery("SELECT * FROM courses WHERE course_code="+courseCode+"",null);
    }

    /**
     * Delete a course from an id
     * @param id
     * @return int
     */
    public int deleteCourse(int id){
        return dbWrite.delete("courses","id = ?",new String[] { Integer.toString(id) });
    }
}
