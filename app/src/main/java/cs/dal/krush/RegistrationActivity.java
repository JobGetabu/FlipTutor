package cs.dal.krush;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import cs.dal.krush.helpers.ValidationHelper;
import cs.dal.krush.models.DBHelper;

/**
 * RegistrationActivity is used to register a new user to
 * the Krush application. It performs basic form validation
 * (i.e. required fields) and creates a new user in the DB
 * upon succesfully filling out the registration form.
 *
 */
public class RegistrationActivity extends AppCompatActivity {

    private int profileSelected;
    private int schoolID;
    private boolean isValid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirmation;
    private DBHelper mydb;
    private ArrayList schoolList;
    private Cursor rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //initialize database connection
        mydb = new DBHelper(getApplicationContext());

        //fetch UI elements:
        final TextView page_title_textView = (TextView) findViewById(R.id.text_view_page_header);
        final TextView radio_label_textView = (TextView) findViewById(R.id.radio_label);
        final TextView school_label_textView = (TextView) findViewById(R.id.school_label);
        final EditText first_name_input = (EditText) findViewById(R.id.input_first_name);
        final EditText last_name_input = (EditText) findViewById(R.id.input_last_name);
        final EditText  email_input = (EditText) findViewById(R.id.input_email);
        final EditText  input_password = (EditText) findViewById(R.id.input_password);
        final EditText  input_password_confirm = (EditText) findViewById(R.id.input_password_confirm);
        final Button register_button = (Button) findViewById(R.id.submit_registration);
        final RadioButton radio_student = (RadioButton) findViewById(R.id.radio_student);
        final RadioButton radio_tutor = (RadioButton) findViewById(R.id.radio_tutor);
        final Spinner school_selecter = (Spinner) findViewById(R.id.school_selecter);

        //fetch custom app font
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/FredokaOne-Regular.ttf");

        //set logo font style
        page_title_textView.setTypeface(typeFace);
        radio_label_textView.setTypeface(typeFace);
        school_label_textView.setTypeface(typeFace);

        //radio button click listeners
        profileSelected = 0;

        radio_tutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                profileSelected = 1;
            }
        });
        radio_student.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                profileSelected = 2;
            }
        });

        //get all schools from db
        rs = mydb.school.getAll();
        schoolList = new ArrayList();

        //populate schoolList with schools
        if(rs.getCount() != 0){
            rs.moveToFirst();
            do{
                schoolList.add(rs.getString(1) + " "
                + rs.getString(2));
            }while (rs.moveToNext());
        }

        //populate the school_selecter spinner with values from schoolList
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, schoolList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        school_selecter.setAdapter(adapter);

        //setup OnClickListeners:
        register_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                isValid = true;
                firstName = first_name_input.getText().toString();
                lastName = last_name_input.getText().toString();
                email = email_input.getText().toString();
                password = input_password.getText().toString();
                passwordConfirmation = input_password_confirm.getText().toString();

                //validate input fields are not empty
                if(firstName.length() == 0) {
                    first_name_input.setError("First name is required!");
                    isValid = false;
                }
                if(lastName.length() == 0) {
                    last_name_input.setError("Last name is required!");
                    isValid = false;
                }
                if(email.length() == 0) {
                    email_input.setError("Email is required.");
                    isValid = false;
                }
                if(password.length() == 0) {
                    input_password.setError("Password is required!");
                    isValid = false;
                }
                if(passwordConfirmation.length() == 0) {
                    input_password_confirm.setError("Password is required!");
                    isValid = false;
                }

                //validate password must match
                if(!passwordConfirmation.equals(password)) {
                    input_password_confirm.setError("Passwords must match");
                    isValid = false;
                }

                //validate email format
                if(!ValidationHelper.Email_Validate(email)) {
                    email_input.setError("Invalid email format!");
                    isValid = false;
                }

                //validate if profile is selected
                if(profileSelected == 0) {
                    Toast.makeText(getApplicationContext(), "Please select a Role " +
                            "(Student or Tutor)", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                //validate if email is already used
                if (!mydb.student.validateEmail(email)) {
                    email_input.setError("This email is already used!");
                    isValid = false;
                }

                if(isValid) {
                    //get index value of the school
                    schoolID = schoolList.indexOf(school_selecter.getSelectedItem().toString()) + 1;

                    //create user based on their type (student/tutor)
                    if(profileSelected == 1){
                        mydb.tutor.insert(0, schoolID, null, firstName, lastName, email, password, 0, 0, 0);
                        Intent i = new Intent(RegistrationActivity.this, TutorMainActivity.class);
                        //get the new user we just inserted to the DB and pass the ID to the next activity:
                        Cursor newUser = mydb.tutor.getDataEmail(email, password);
                        newUser.moveToFirst();
                        i.putExtra("USER_ID", newUser.getString(newUser.getColumnIndex("id")));
                        newUser.close();
                        startActivity(i);
                    } else {
                        mydb.student.insert(schoolID, null, firstName, lastName, email, password);
                        Intent i = new Intent(RegistrationActivity.this, StudentMainActivity.class);
                        //get the new user we just inserted to the DB and pass the ID to the next activity:
                        Cursor newUser = mydb.student.getDataEmail(email, password);
                        newUser.moveToFirst();
                        i.putExtra("USER_ID", newUser.getString(newUser.getColumnIndex("id")));
                        newUser.close();
                        startActivity(i);
                    }
                }
            }
        });
    }
}
