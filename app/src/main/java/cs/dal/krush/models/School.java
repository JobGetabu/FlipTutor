package cs.dal.krush.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * School Model class.
 */
public class School extends Table{

    public School(SQLiteDatabase dbWrite, SQLiteDatabase dbRead){
        super(dbWrite,dbRead);
    }

    /**
     * Insert row into schools table
     * @param name
     * @param locationId
     * @param type
     * @return boolean
     */
    public boolean insert(String name, int locationId, String type){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("location_id", locationId);
        contentValues.put("type", type);
        dbWrite.insert("schools", null, contentValues);
        return true;
    }

    @Override
    public Cursor getData(int id){
        return dbRead.rawQuery("SELECT * FROM schools WHERE id="+id+"",null);
    }

    @Override
    public Cursor getAll() {
        return dbRead.rawQuery("SELECT * FROM schools",null);
    }

    /**
     * Get a school by the name field
     * @param name
     * @return Cursor
     */
    public Cursor getDataByName(String name){
        return dbRead.rawQuery("SELECT * FROM schools WHERE name="+name+"",null);
    }

    /**
     * Get a school by the type field
     * @param type
     * @return Cursor
     */
    public Cursor getDataByType(String type){
        return dbRead.rawQuery("SELECT * FROM schools WHERE type="+type+"",null);
    }

    /**
     * Get a school by the location_id field
     * @param location_id
     * @return Cursor
     */
    public Cursor getSchoolsByLocation(int location_id){
        return dbRead.rawQuery("SELECT * FROM schools WHERE location_id="+location_id+"",null);
    }

    /**
     * Delete a school by id
     * @param id
     * @return int
     */
    public int deleteSchool(int id){
        return dbWrite.delete("schools","id = ?",new String[] { Integer.toString(id) });
    }

}
