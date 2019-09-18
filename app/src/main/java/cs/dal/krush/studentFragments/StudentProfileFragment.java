package cs.dal.krush.studentFragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cs.dal.krush.R;
import cs.dal.krush.models.DBHelper;
import static cs.dal.krush.R.id.student_profile_name;

/**
 * Sets up the Student Profile fragment. This fragment belongs to the StudentMainActivity class
 * and is accessed through the student's bottom navigation bar.
 *
 * The student can view and edit their user profile using this fragment.
 */
public class StudentProfileFragment extends Fragment implements View.OnClickListener {

    private ImageView edit_btn, profile_picture_view;
    private TextView profile_name_view, email_view, school_view, email_label, school_label;
    private DBHelper mydb;
    private Cursor cursor;
    static int USER_ID;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.student_profile, container, false);

        //Get user_id of logged in user
        USER_ID = getArguments().getInt("USER_ID");

        //initialize database connection
        mydb = new DBHelper(getContext());
        cursor = mydb.student.getData(USER_ID);
        cursor.moveToFirst();

        // Get Views
        profile_name_view = (TextView) myView.findViewById(student_profile_name);
        profile_picture_view = (ImageView) myView.findViewById(R.id.student_profile_picture);
        email_view = (TextView) myView.findViewById(R.id.student_profile_email);
        school_view = (TextView) myView.findViewById(R.id.student_profile_school);
        email_label = (TextView) myView.findViewById(R.id.student_profile_email_label);
        school_label = (TextView) myView.findViewById(R.id.student_profile_school_label);


        //fetch custom app font
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //Set custom app font
        profile_name_view.setTypeface(typeFace);
        email_view.setTypeface(typeFace);
        school_view.setTypeface(typeFace);
        email_label.setTypeface(typeFace);
        school_label.setTypeface(typeFace);

        //Get values from database
        String name = cursor.getString(cursor.getColumnIndex("f_name")) + " " + cursor.getString(cursor.getColumnIndex("l_name"));
        String email = cursor.getString(cursor.getColumnIndex(("email")));
        String school = mydb.student.getSchoolAndType(USER_ID);

        //Profile Picture
        String imagePath = cursor.getString(cursor.getColumnIndex("profile_pic"));
        if(imagePath != null && !imagePath.isEmpty()) {
            Bitmap profile_pic = BitmapFactory.decodeFile(imagePath);
            profile_picture_view.setImageBitmap(profile_pic);
        }

        // Set values to views
        profile_name_view.setText(name);
        email_view.setText(email);
        school_view.setText(school);

        //Edit profile button listener
        edit_btn = (ImageView) myView.findViewById(R.id.edit_profile_button);
        edit_btn.setOnClickListener(this);
        return myView;
    }

    // Edit profile button listener, starts StudentProfileEditFragment
    @Override
    public void onClick(View v) {
        try {
            //Create bundle to send USER_ID to edit fragment
            Bundle bundle = new Bundle();
            bundle.putInt("USER_ID", USER_ID);

            StudentProfileEditFragment edit = new StudentProfileEditFragment();
            edit.setArguments(bundle);

            // Set user_id for edit fragment
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.student_fragment_container, edit);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
