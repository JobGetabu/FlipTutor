package cs.dal.krush.seeders;

import cs.dal.krush.models.DBHelper;

/**
 * Seeder for the students database table
 */
public class StudentSeeder {

    /**
     * Function that inserts dummy student accounts into the DB.
     * Insert format: schoolId, profilePic, firstName, lastName, email, password.
     * @param db
     */
    public static void insert(DBHelper db){
        db.student.insert(1, null, "Greg", "Miller", "gregpmillr@gmail.com", "password");
        db.student.insert(3, null, "John", "Wick", "jw@gmail.com", "password");
        db.student.insert(2, null, "John", "Smith", "js@gmail.com", "password");
        db.student.insert(4, null, "Samuel", "Jackson", "sj@dal.ca", "password");
        db.student.insert(5, null, "Jack", "Daniels", "jd@outlook.com", "password");
        db.student.insert(6, null, "Tim", "Thompson", "tt@hotmail.ca", "password");
        db.student.insert(7, null, "Mike", "Jackson", "mj@hotmail.ca", "password");
        db.student.insert(8, null, "Lucy", "Patterson", "lp@hotmail.ca", "password");
        db.student.insert(7, null, "Carline", "Jenkins", "mj@hotmail.ca", "password");
        db.student.insert(3, null, "McKenzie", "Rekker", "mk@dal.ca", "password");
        db.student.insert(4, null, "Darlene", "Tims", "dt@gmail.ca", "password");
    }
}
