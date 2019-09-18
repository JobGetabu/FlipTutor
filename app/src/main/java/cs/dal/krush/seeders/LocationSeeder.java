package cs.dal.krush.seeders;

import cs.dal.krush.models.DBHelper;

/**
 * Seeder for the locations database table
 */
public class LocationSeeder {

    /**
     * Function that inserts dummy locations into the DB
     * @param db
     */
    public static void insert(DBHelper db){
        db.location.insert("6050 University Avenue B3H");
        db.location.insert("5991 Spring Garden Road, B3H 1Y6");
        db.location.insert("5440 Spring Garden Road, B3J 1E9");
        db.location.insert("6225 University Avenue, B3H 4H8");
        db.location.insert("6136 University Avenue, B3H 4J2");
        db.location.insert("1451 South Park Street, B3J OB6");
    }
}
