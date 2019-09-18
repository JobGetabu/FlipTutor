package cs.dal.krush.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Audio Recording Model class.
 */
public class AudioRecording extends Table {

    public AudioRecording(SQLiteDatabase dbWrite, SQLiteDatabase dbRead){
        super(dbWrite,dbRead);
    }

    /**
     * Insert row into audio_recordings table
     * @param studentId
     * @param sessionId
     * @return boolean
     */
    public boolean insert(int studentId, int sessionId){
        ContentValues contentValues = new ContentValues();
        contentValues.put("student_id", studentId);
        contentValues.put("session_id", sessionId);
        dbWrite.insert("audio_recording", null, contentValues);
        return true;
    }
    /**
     * Update row in audio_recordings table
     * @param studentId
     * @param sessionId
     * @return boolean
     */
    public boolean update(int studentId, int sessionId){
        ContentValues contentValues = new ContentValues();
        contentValues.put("student_id", studentId);
        contentValues.put("session_id", sessionId);
        dbWrite.update("audio_recording", contentValues, "student_id=? AND session_id=?", new String[] { "" + studentId, "" + sessionId});
        return true;
    }
    @Override
    public Cursor getData(int id){
        return dbRead.rawQuery("SELECT * FROM audio_recording WHERE id="+id+"",null);
    }

    @Override
    public Cursor getAll() {
        return dbRead.rawQuery("SELECT * FROM audio_recording",null);
    }

    /**
     * Get an audio recording by the location_id field
     * @param sessionId
     * @return res
     */
    public Cursor getDataBySessionId(int sessionId){
        return dbRead.rawQuery("SELECT * FROM audio_recording WHERE session_id="+sessionId+"",null);
    }

    /**
     * Get an audio recording by the student_id field
     * @param studentId
     * @return res
     */
    public Cursor getDataByStudentId(int studentId){
        return dbRead.rawQuery("SELECT * FROM audio_recording WHERE student_id="+studentId+"",null);
    }

    /**
     * Get an audio recording by the student_id and session_id fields
     * @param studentId
     * @param sessionId
     * @return res
     */
    public Cursor getDataByStudentAndSessionId(int studentId, int sessionId){
        return dbRead.rawQuery("SELECT * FROM audio_recording WHERE student_id="+studentId+" AND session_id="+sessionId+"",null);
    }

    /**
     * Delete an audio recording from an id
     * @param id
     * @return int
     */
    public int deleteAudioRecording(int id){
        return dbWrite.delete("audio_recording","id = ?",new String[] { Integer.toString(id) });
    }
}
