package cs.dal.krush.seeders;

import cs.dal.krush.models.DBHelper;

/**
 * Seeder for the locations database table
 */
public class TutorSeeder {

    /**
     * Function that inserts dummy tutor accounts into the DB.
     * Insert format: locationId, schoolId, profilePic, firstName, lastName, email, password, rate.
     * @param db
     */
    public static void insert(DBHelper db){
        db.tutor.insert(1, 1, null, "Tim", "Njugush", "timnjugush@gmail.com", "password", 2, (float)4, 8);
        db.tutor.insert(1, 1, null, "Greg", "Miller", "gregpmillr@gmail.com", "password", 2, (float)4, 8);
        db.tutor.insert(3, 2, null, "Michael", "Njoroge", "mc@gmail.com", "password", 2, (float)4.5, 5);
        db.tutor.insert(2, 1, null, "gabriel", "kamau", "gkamau@gmail.com", "password", 5, (float)4.1, 5);
        db.tutor.insert(4, 5, null, "mr", "wandeto", "wandeto@gmail.com", "password", 1, (float)3.5, 5);
        db.tutor.insert(5, 3, null, "Connor", "Kamau", "cw@gmail.com", "password", 4, (float)4, 5);
        db.tutor.insert(2, 2, null, "Jack", "Njuguna", "jr@gmail.com", "password", 2, (float)4.25, 5);
        db.tutor.insert(5, 4, null, "Peter", "Lukip", "pr@gmail.com", "password", 2, (float)3, 5);
        db.tutor.insert(1, 3, null, "Bonnie", "Kalalwe", "br@gmail.com", "password", 2, (float)3.75, 5);
        db.tutor.insert(1, 3, null, "Eve", "Wamboi", "ez@gmail.com", "password", 2, (float)3.5, 5);
    }
}
