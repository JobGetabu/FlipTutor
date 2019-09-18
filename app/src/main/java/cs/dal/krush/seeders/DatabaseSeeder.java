package cs.dal.krush.seeders;

import android.content.Context;
import android.database.Cursor;

import cs.dal.krush.models.AvailableTime;
import cs.dal.krush.models.DBHelper;
import cs.dal.krush.models.TutorRating;

/**
 * Database Seeder class built to initiate seeding
 */
public class DatabaseSeeder {
    /**
     * Declare variables
     */
    DBHelper mydb;
    Object[] args;
    Context context;

    /**
     * Empty constructor
     * @param context
     */
    public DatabaseSeeder(Context context){
        this.context = context;
    }

    /**
     * Seed data into database
     */
    public void insertData() {
        //instantiate DBHelper
        mydb = new DBHelper(context);

        /**
         * Insert data
         */
        LocationSeeder.insert(mydb);
        SchoolSeeder.insert(mydb);
        TutorSeeder.insert(mydb);
        StudentSeeder.insert(mydb);
        TutoringSessionSeeder.insert(mydb);
        TutorRatingSeeder.insert(mydb);
        CourseSeeder.insert(mydb);
        CoursesTutorsSeeder.insert(mydb);
        AvailableTimeSeeder.insert(mydb);
    }

    /**
     * Display data inserted by seeders
     */
    public void displayData(){
        //instantiate DBHelper
        mydb = new DBHelper(context);

        /**
         * Get and display data
         */
        Cursor rs;
        String s;

        rs = mydb.location.getLocationBySchool(1);
        rs.moveToFirst();
        s = rs.getString(rs.getColumnIndex("location"));
        System.out.println("LOCATION:" + s);
        if (!rs.isClosed()) {
            rs.close();
        }

        rs = mydb.course.getData(1);
        rs.moveToFirst();
        s = rs.getString(rs.getColumnIndex("course_code"));
        System.out.println(s);
        if (!rs.isClosed()) {
            rs.close();
        }

        rs = mydb.school.getData(1);
        rs.moveToFirst();
        s = rs.getString(rs.getColumnIndex("name"));
        System.out.println(s);
        if (!rs.isClosed()) {
            rs.close();
        }

        rs = mydb.tutor.getData(1);
        rs.moveToFirst();
        s = rs.getString(rs.getColumnIndex("f_name"));
        System.out.println(s);
        if (!rs.isClosed()) {
            rs.close();
        }
    }
}
