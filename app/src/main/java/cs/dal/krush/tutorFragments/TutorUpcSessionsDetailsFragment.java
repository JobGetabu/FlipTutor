package cs.dal.krush.tutorFragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cs.dal.krush.R;
import cs.dal.krush.models.DBHelper;

/**
 * This fragment displays the details of a session when a tutor clicks on an
 * upcoming session in the tutor home upcoming sessions list view
 */
public class TutorUpcSessionsDetailsFragment extends Fragment {

    static int USER_ID;
    static int SESSION_ID;
    DBHelper mydb;
    Cursor sessionCursor;
    TextView titleField, studentNameField, studentEmailField, locationField,
            startField, endField, studentLabel, sessionInfoLabel, schoolField;
    ImageView studentPicture;
    Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutor_home_upc_session_details, container, false);

        // Get USER_ID and SESSION_ID
        USER_ID = getArguments().getInt("USER_ID");
        SESSION_ID = getArguments().getInt("SESSION_ID");

        // Initialize db connection
        mydb = new DBHelper(getContext());

        // Get session
        sessionCursor = mydb.tutoringSession.getSessionHistoryDetailsBySessionIdForCursorAdapter(SESSION_ID);
        sessionCursor.moveToFirst();

        // Get Views
        studentPicture = (ImageView) view.findViewById(R.id.tutor_history_details_student_picture);
        titleField = (TextView) view.findViewById(R.id.tutor_history_details_title);
        studentNameField = (TextView) view.findViewById(R.id.tutor_history_details_student_name);
        studentEmailField = (TextView) view.findViewById(R.id.tutor_history_details_student_email);
        locationField = (TextView) view.findViewById(R.id.tutor_history_details_location);
        startField = (TextView) view.findViewById(R.id.tutor_history_details_start);
        endField = (TextView) view.findViewById(R.id.tutor_history_details_end);
        studentLabel = (TextView) view.findViewById(R.id.tutor_history_details_student_label);
        sessionInfoLabel = (TextView) view.findViewById(R.id.tutor_history_details_session_label);
        schoolField = (TextView) view.findViewById(R.id.tutor_history_details_school);
        cancelButton = (Button) view.findViewById(R.id.cancel_session_button);

        //fetch custom app font
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/FredokaOne-Regular.ttf");

        //Set custom app font
        titleField.setTypeface(typeFace);
        studentNameField.setTypeface(typeFace);
        studentEmailField.setTypeface(typeFace);
        locationField.setTypeface(typeFace);
        startField.setTypeface(typeFace);
        endField.setTypeface(typeFace);
        studentLabel.setTypeface(typeFace);
        sessionInfoLabel.setTypeface(typeFace);
        schoolField.setTypeface(typeFace);
        cancelButton.setTypeface(typeFace);

        // Get values from database
        String title = sessionCursor.getString(sessionCursor.getColumnIndex("title"));
        String studentName = sessionCursor.getString(sessionCursor.getColumnIndex("f_name")) + " " +
                sessionCursor.getString(sessionCursor.getColumnIndex("l_name"));
        String studentEmail = sessionCursor.getString(sessionCursor.getColumnIndex("email"));
        String startTime = sessionCursor.getString(sessionCursor.getColumnIndex("start_time"));
        String endTime = sessionCursor.getString(sessionCursor.getColumnIndex("end_time"));
        String location = sessionCursor.getString(sessionCursor.getColumnIndex("location"));
        String imgPath = sessionCursor.getString(sessionCursor.getColumnIndex("profile_pic"));
        String school = sessionCursor.getString(sessionCursor.getColumnIndex("name")) + " " +
                sessionCursor.getString(sessionCursor.getColumnIndex("type"));

        // Set values to view
        titleField.setText(title);
        studentNameField.setText(studentName);
        studentEmailField.setText(studentEmail);
        startField.setText("Starts At: " + startTime);
        endField.setText("Ends At: " + endTime);
        locationField.setText("Meeting Location: " + location);
        schoolField.setText("School: " + school);
        if (imgPath != null && !imgPath.isEmpty()) {
            Bitmap profilePic = BitmapFactory.decodeFile(imgPath);
            studentPicture.setImageBitmap(profilePic);
        }


        //Build the "Are you sure?" dialog window:
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to cancel this session?").setTitle("Cancel Session");
        builder.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked on "Yes" - destroy the tutoring session
                mydb.tutoringSession.deleteTutoringSession(SESSION_ID);
                mydb.close();

                // return to home fragment
                Bundle bundle = new Bundle();
                bundle.putInt("USER_ID", USER_ID);
                TutorHomeFragment home = new TutorHomeFragment();
                home.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.tutor_fragment_container, home);
                transaction.commit();
            }
        });
        builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //user clicked on "No" - return to the view

            }
        });
        final AlertDialog dialog = builder.create();

        // Set on click listener for cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display "are you sure" dialog window:
                dialog.show();
            }
        });

        // close DB connections
        sessionCursor.close();

        return view;
    }

}
