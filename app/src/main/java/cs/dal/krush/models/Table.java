package cs.dal.krush.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Table abstract class used to get tables in the DB. All Model classes extends this class.
 */
public abstract class Table {

    protected SQLiteDatabase dbWrite;
    protected SQLiteDatabase dbRead;
    protected Cursor res;

    public Table(){}

    public Table(SQLiteDatabase dbWrite, SQLiteDatabase dbRead){
        this.dbWrite = dbWrite;
        this.dbRead = dbRead;
    }

    /**
     * Get field by id
     * @param id
     * @return Cursor
     */
    public abstract Cursor getData(int id);

    /**
     * Get all rows in table
     * @return Cursor
     */
    public abstract Cursor getAll();
}
