package cs.dal.krush.tutorFragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cs.dal.krush.R;
import cs.dal.krush.models.DBHelper;

/**
 * This fragment displays the details of a session when a tutor clicks on a
 * completed session in the tutor Sessions History list view
 */
public class TutorHistoryDetailsFragment extends Fragment {

    static int USER_ID;
    static int SESSION_ID;
    DBHelper mydb;
    Cursor sessionCursor;
    TextView titleField, studentNameField, studentEmailField, locationField,
            startField, endField, studentLabel, sessionInfoLabel, schoolField;
    ImageView studentPicture;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutor_sessions_details, container, false);

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
        schoolField = (TextView)view.findViewById(R.id.tutor_history_details_school);

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
        startField.setText("Started At: " + startTime);
        endField.setText("Ended At: " + endTime);
        locationField.setText("Meeting Location: " + location);
        schoolField.setText("School: " + school);
        if(imgPath != null && !imgPath.isEmpty()) {
            Bitmap profilePic = BitmapFactory.decodeFile(imgPath);
            studentPicture.setImageBitmap(profilePic);
        }

        sessionCursor.close();
        mydb.close();

        return view;
    }
}
