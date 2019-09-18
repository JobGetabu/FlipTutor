package cs.dal.krush.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBHelper class that makes it possible to perform queries on the DB.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mike_casey.db";
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    public Student student = new Student(this.getWritableDatabase(), this.getReadableDatabase());
    public Tutor tutor = new Tutor(this.getWritableDatabase(), this.getReadableDatabase());
    public School school = new School(this.getWritableDatabase(), this.getReadableDatabase());
    public Course course = new Course(this.getWritableDatabase(), this.getReadableDatabase());
    public AudioRecording audioRecording = new AudioRecording(this.getWritableDatabase(), this.getReadableDatabase());
    public TutoringSession tutoringSession = new TutoringSession(this.getWritableDatabase(), this.getReadableDatabase());
    public TutorRating tutorRating = new TutorRating(this.getWritableDatabase(), this.getReadableDatabase());
    public CoursesTutors coursesTutors = new CoursesTutors(this.getWritableDatabase(), this.getReadableDatabase());
    public Location location = new Location(this.getWritableDatabase(), this.getReadableDatabase());
    public AvailableTime availableTime = new AvailableTime(this.getWritableDatabase(), this.getReadableDatabase());

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE locations " +
                        "(id INTEGER PRIMARY KEY," +
                        "location VARCHAR(255))"
        );

        db.execSQL(
                "CREATE TABLE tutors " +
                        "(id INTEGER PRIMARY KEY," +
                        "location_id INTEGER," +
                        "school_id INTEGER," +
                        "profile_pic VARCHAR(255)," +
                        "f_name VARCHAR(255)," +
                        "l_name VARCHAR(255)," +
                        "email VARCHAR(255)," +
                        "password VARCHAR(255)," +
                        "rating INTEGER," +
                        "rating_count INTEGER," +
                        "rate INTEGER," +
                        "revenue INTEGER," +
                        "FOREIGN KEY(school_id) REFERENCES schools(id)," +
                        "FOREIGN KEY(location_id) REFERENCES locations(id))"
        );

        db.execSQL(
                "CREATE TABLE students " +
                        "(id INTEGER PRIMARY KEY," +
                        "school_id INTEGER," +
                        "audio_recording_id INTEGER," +
                        "profile_pic VARCHAR(255)," +
                        "f_name VARCHAR(255)," +
                        "l_name VARCHAR(255)," +
                        "email VARCHAR(255)," +
                        "password VARCHAR(255)," +
                        "credit_card_num VARCHAR(255)," +
                        "credit_card_exp_date DATE," +
                        "credit_card_cvv INTEGER," +
                        "FOREIGN KEY(school_id) REFERENCES schools(id)," +
                        "FOREIGN KEY(audio_recording_id) REFERENCES audio_recording(id))"
        );

        db.execSQL(
                "CREATE TABLE tutoring_sessions " +
                        "(id INTEGER PRIMARY KEY," +
                        "title VARCHAR(255)," +
                        "start_time DATETIME," +
                        "end_time DATETIME," +
                        "student_id INTEGER," +
                        "tutor_id INTEGER," +
                        "location_id INTEGER," +
                        "session_booked INTEGER," +
                        "profile_pic VARCHAR(255)," +
                        "FOREIGN KEY(location_id) REFERENCES locations(id)," +
                        "FOREIGN KEY(student_id) REFERENCES students(id) ON DELETE CASCADE," +
                        "FOREIGN KEY(tutor_id) REFERENCES tutors(id) ON DELETE CASCADE)"
        );

        db.execSQL(
                "CREATE TABLE tutor_ratings " +
                        "(id INTEGER PRIMARY KEY," +
                        "rating INTEGER," +
                        "student_id INTEGER," +
                        "tutor_id INTEGER," +
                        "FOREIGN KEY(student_id) REFERENCES students(id) ON DELETE CASCADE," +
                        "FOREIGN KEY(tutor_id) REFERENCES tutors(id) ON DELETE CASCADE)"
        );
        
        db.execSQL(
                "CREATE TABLE schools " +
                        "(id INTEGER PRIMARY KEY," +
                        "name VARCHAR(255)," +
                        "type VARCHAR(255)," +
                        "location_id INTEGER," +
                        "FOREIGN KEY(location_id) REFERENCES locations(id))"
        );

        db.execSQL(
                "CREATE TABLE courses " +
                        "(id INTEGER PRIMARY KEY," +
                        "title VARCHAR(255)," +
                        "course_code VARCHAR(255))"
        );

        db.execSQL(
                "CREATE TABLE audio_recording " +
                        "(id INTEGER PRIMARY KEY," +
                        "student_id INTEGER," +
                        "session_id INTEGER," +
                        "FOREIGN KEY(session_id) REFERENCES tutoring_sessions(id)," +
                        "FOREIGN KEY(student_id) REFERENCES students(id) ON DELETE CASCADE)"
        );

        db.execSQL(
                "CREATE TABLE course_tutors " +
                        "(id INTEGER PRIMARY KEY," +
                        "course_id INTEGER NOT NULL," +
                        "tutor_id INTEGER NOT NULL," +
                        "FOREIGN KEY(course_id) REFERENCES courses(id) ON DELETE CASCADE," +
                        "FOREIGN KEY(tutor_id) REFERENCES tutors(id) ON DELETE CASCADE)"
        );

        db.execSQL(
                "CREATE TABLE available_time " +
                        "(id INTEGER PRIMARY KEY," +
                        "tutor_id INTEGER NOT NULL," +
                        "start_time DATETIME NOT NULL," +
                        "end_time DATETIME NOT NULL," +
                        "booked INTEGER," +
                        "FOREIGN KEY(tutor_id) REFERENCES tutors(id) ON DELETE CASCADE)"
        );



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tutors");
        db.execSQL("DROP TABLE IF EXISTS students");
        db.execSQL("DROP TABLE IF EXISTS tutoring_sessions");
        db.execSQL("DROP TABLE IF EXISTS schools");
        db.execSQL("DROP TABLE IF EXISTS courses");
        db.execSQL("DROP TABLE IF EXISTS audio_recording");
        db.execSQL("DROP TABLE IF EXISTS course_tutors");
        db.execSQL("DROP TABLE IF EXISTS locations");
        db.execSQL("DROP TABLE IF EXISTS available_time");

        onCreate(db);
    }

    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
