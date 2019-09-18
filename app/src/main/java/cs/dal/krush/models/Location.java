package cs.dal.krush.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Location Model class.
 */
public class Location extends Table{

    public Location(SQLiteDatabase dbWrite, SQLiteDatabase dbRead){
        super(dbWrite,dbRead);
    }

    /**
     * Insert row into locations table
     * @param location
     * @return boolean
     */
    public boolean insert(String location) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("location", location);
        dbWrite.insert("locations", null, contentValues);
        return true;
    }

    @Override
    public Cursor getData(int id) {
        return dbRead.rawQuery("SELECT * FROM locations WHERE id="+id+"",null);
    }

    @Override
    public Cursor getAll() {
        return dbRead.rawQuery("SELECT * FROM locations",null);
    }

    /**
     * Get a location by the location's name
     * @param location
     * @return Cursor
     */
    public Cursor getDataByLocation(String location){
        return dbRead.rawQuery("SELECT * FROM locations WHERE location='"+location+"'",null);
    }

    /**
     * Get a location by the school_id column
     * @param school_id
     * @return Cursor
     */
    public Cursor getLocationBySchool(int school_id){
        //get the school's location_id:
        Cursor res = dbRead.rawQuery("SELECT * FROM schools WHERE id="+school_id+"",null);
        res.moveToFirst();
        int location_id = res.getInt(res.getColumnIndex("location_id"));

        //get the location from the location_id:
        res = dbRead.rawQuery("SELECT * FROM locations WHERE id="+location_id+"",null);

        return res;
    }

    /**
     * Delete a location by id
     * @param id
     * @return int
     */
    public int deleteLocation(int id){
        return dbWrite.delete("locations","id = ?",new String[] { Integer.toString(id) });
    }
}
