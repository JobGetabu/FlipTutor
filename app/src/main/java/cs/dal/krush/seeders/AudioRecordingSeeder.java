package cs.dal.krush.seeders;

import cs.dal.krush.models.DBHelper;

/**
 * Seeder for the audio_recording database table
 */
public class AudioRecordingSeeder {

    /**
     * Function that inserts dummy audio recordings for students' tutoring sessions
     * @param db
     */
    public static void insert(DBHelper db){
        db.audioRecording.insert(1,2);
        db.audioRecording.insert(2,1);
        db.audioRecording.insert(3,4);
        db.audioRecording.insert(4,1);
        db.audioRecording.insert(5,2);
        db.audioRecording.insert(2,5);
        db.audioRecording.insert(5,1);
        db.audioRecording.insert(2,3);
        db.audioRecording.insert(4,3);
    }
}
