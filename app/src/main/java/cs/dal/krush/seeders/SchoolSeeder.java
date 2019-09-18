package cs.dal.krush.seeders;

import cs.dal.krush.models.DBHelper;

/**
 * Seeder for the schools database table
 */
public class SchoolSeeder {

    /**
     * Function that inserts dummy schools into the DB
     * insert format: school_name, locationID, school_type
     * @param db
     */
    public static void insert(DBHelper db){
        db.school.insert("Certified Public Accountant",1,"Community College");
        db.school.insert("Certified Investment And Financial Analysts (CIFA)",1,"Community College");
        db.school.insert("Certified Secretaries",4,"Community College");
        db.school.insert("Certified Information Communication Technologists (CICT)",1,"Community College");
        db.school.insert("ACCOUNTING TECHNICIAN DIPLOMA (ATD)",1,"University");
        db.school.insert("Certificate In Accounting And Management Skills (CAMS)",1,"Community College");
        db.school.insert("Certified Credit Professionals (CPP)",5,"Community College");
        db.school.insert("Diploma in Information Communication Technology(DICT)",6,"University");
        db.school.insert("Diploma in Credit Management (DCM)",1,"University");
    }
}
