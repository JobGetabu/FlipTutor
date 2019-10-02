package cs.dal.krush.tutorFragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cs.dal.krush.R;
import cs.dal.krush.models.DBHelper;

/**
 * Sets up the Tutor Profile fragment. This fragment belongs to the TutorMainActivity class
 * and is accessed through the tutor's bottom navigation bar.
 *
 * The tutor can view and edit their user profile using this fragment.
 */
public class TutorProfileFragment extends Fragment implements View.OnClickListener {

    private DBHelper mydb;
    private Cursor cursor;
    static int USER_ID;
    ImageView edit_btn, profile_picture_view;
    TextView profile_name_view, email_view, school_view, rate_view, email_label, school_label, rate_label;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.tutor_profile, container, false);

        //Get user_id of logged in user
        USER_ID = getArguments().getInt("USER_ID");

        //initialize database connection
        mydb = new DBHelper(getContext());
        cursor = mydb.tutor.getData(USER_ID);
        cursor.moveToFirst();

        // Get TextViews
        profile_name_view = (TextView) myView.findViewById(R.id.tutor_profile_name);
        profile_picture_view = (ImageView) myView.findViewById(R.id.tutor_profile_picture);
        email_view = (TextView) myView.findViewById(R.id.tutor_email);
        school_view = (TextView) myView.findViewById(R.id.tutor_school);
        rate_view = (TextView) myView.findViewById(R.id.tutor_rate);
        email_label = (TextView) myView.findViewById(R.id.tutor_profile_email_label);
        school_label = (TextView) myView.findViewById(R.id.tutor_profile_school_label);
        rate_label = (TextView) myView.findViewById(R.id.tutor_profile_rate_label);

        //Get values from database
        String name = cursor.getString(cursor.getColumnIndex("f_name")) + " " + cursor.getString(cursor.getColumnIndex("l_name"));
        String email = cursor.getString(cursor.getColumnIndex(("email")));
        String rate = TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(("rate")))) ? "Please set your rate"
                : cursor.getString(cursor.getColumnIndex(("rate")));
        String school = mydb.tutor.getSchoolNameAndType(USER_ID);

        //Profile Picture
        String imagePath = cursor.getString(cursor.getColumnIndex("profile_pic"));
        if(imagePath != null && !imagePath.isEmpty()) {
            Bitmap profile_pic = BitmapFactory.decodeFile(imagePath);
            profile_picture_view.setImageBitmap(profile_pic);
        }

        profile_name_view.setText(name);
        email_view.setText(email);
        school_view.setText(school);
        rate_view.setText(rate);

        //fetch custom app font
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //Set custom app font
        profile_name_view.setTypeface(typeFace);
        email_view.setTypeface(typeFace);
        school_view.setTypeface(typeFace);
        rate_view.setTypeface(typeFace);
        email_label.setTypeface(typeFace);
        school_label.setTypeface(typeFace);
        rate_label.setTypeface(typeFace);

        //Edit profile button listener
        edit_btn = (ImageView) myView.findViewById(R.id.edit_profile_button);
        edit_btn.setOnClickListener(this);
        return myView;
    }

    /**
     * Edit profile button listener, starts TutorProfileEditFragment
     * @param v
     */
    @Override
    public void onClick(View v) {
        try {
            //Create bundle to send USER_ID to edit fragment
            Bundle bundle = new Bundle();
            bundle.putInt("USER_ID", USER_ID);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            TutorProfileEditFragment edit = new TutorProfileEditFragment();
            edit.setArguments(bundle);
            transaction.replace(R.id.tutor_fragment_container, edit);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
