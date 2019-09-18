package cs.dal.krush.seeders;

import cs.dal.krush.models.DBHelper;

/**
 * Seeder for the courses database table
 */
public class CourseSeeder {

    /**
     * Function that inserts dummy courses into the DB
     * @param db
     */
    public static void insert(DBHelper db){
        db.course.insert("Computer Science 3", "CSCI2110");
        db.course.insert("Server Side Scripting","INFX2670");
        db.course.insert("Machine Learning", "CSCI4155");
        db.course.insert("Designing User Interfaces","CSCI3160");
        db.course.insert("Mobile Computing","CSCI4176");
        db.course.insert("Social Computing","CSCI1107");
        db.course.insert("Experimental Robotics","CSCI1106");
        db.course.insert("Computer Science 1","CSCI1100");
        db.course.insert("Communication Skills","CSCI2100");
        db.course.insert("Discrete Structures 1","MATH2112");
        db.course.insert("Discrete Structures 2","MATH2113");
        db.course.insert("Intro to Computer Organization and Assembly Language","CSCI2121");
        db.course.insert("Software Development","CSCI2121");
        db.course.insert("Intro to Database Systems","CSCI2141");
        db.course.insert("Ethical Issues in Computer Science","CSCI3101");
        db.course.insert("Design and Analysis of Algorithms","CSCI3110");
        db.course.insert("Operating Systems","CSCI3120");
        db.course.insert("Software Engineering","CSCI3130");
        db.course.insert("Object Oriented and Generic Programming","CSCI3132");
        db.course.insert("Principles of Programming Languages","CSCI3136");
        db.course.insert("Network Computing","CSCI3171");
        db.course.insert("Cryptography","MATH4116");
        db.course.insert("Natural Language Processing","CSCI4152");
        db.course.insert("Differential and Integral Calculus 1","MATH1000");
        db.course.insert("Differential and Integral Calculus 2","MATH1010");
        db.course.insert("Intermediate Calculus 1","MATH2001");
        db.course.insert("Intermediate Calculus 2","MATH2002");
        db.course.insert("Matrix Theory and Linear Algebra 1","MATH2030");
        db.course.insert("Matrix Theory and Linear Algebra 2","MATH2040");
        db.course.insert("Intro to Probability and Statistics","STAT2060");
        db.course.insert("Statistical Methods for Data Analysis and Inference","STAT2080");
        db.course.insert("Methods for Ordinary Differential Equations","MATH2120");
        db.course.insert("Theory of Numbers","MATH3070");
        db.course.insert("Introduction to Movement Physics","PHYC1190");
        db.course.insert("Intro to Electrical Physics","PHYC1290");
        db.course.insert("Physics In & Around You","PHYC1310");
        db.course.insert("Concepts in Chemistry 1","CHEM1011");
        db.course.insert("Concepts in Chemistry 2","CHEM1012");
    }
}