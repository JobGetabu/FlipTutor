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
        db.student.insert(3, null, "John", "Weru", "jw@gmail.com", "password");
        db.student.insert(2, null, "John", "Smith", "js@gmail.com", "password");
        db.student.insert(4, null, "Samuel", "Jackson", "sj@gmail.com", "password");
        db.student.insert(5, null, "Jack", "Daniels", "jd@gmail.com", "password");
        db.student.insert(6, null, "Tim", "Thompson", "tt@gmail.com", "password");
        db.student.insert(7, null, "Mike", "Jackson", "mj@gmail.com", "password");
        db.student.insert(8, null, "Lucy", "Patricia", "lp@gmail.com", "password");
        db.student.insert(7, null, "Carline", "Njeri", "mj@gmail.com", "password");
        db.student.insert(3, null, "McKenzie", "Rekker", "mk@gmail.com", "password");
        db.student.insert(4, null, "Darlene", "Tims", "dt@gmail.com", "password");
    }
}
